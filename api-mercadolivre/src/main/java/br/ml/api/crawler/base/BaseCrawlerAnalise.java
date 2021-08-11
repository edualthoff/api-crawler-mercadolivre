package br.ml.api.crawler.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import br.ml.api.config.tenant.IndexTenantDynamic;
import br.ml.api.crawler.BuscaCrawler;
import br.ml.api.crawler.busca.CrawlerBuscaUrl;
import br.ml.api.crawler.item.CrawlerBuscaItem;
import br.ml.api.crawler.item.CrawlerBuscaItemService;
import br.ml.api.crawler.item.CrawlerItem;
import br.ml.api.crawler.mercadolivre.BuscarItemMercadoLivre;
import br.ml.api.mail.MailService;
import br.ml.api.tenant.settings.TenantSettingsService;

@Component
@Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BaseCrawlerAnalise implements Serializable{
	private static final long serialVersionUID = 3865572919650540836L;
	private static final Logger log = LoggerFactory.getLogger(BaseCrawlerAnalise.class);

	@Autowired
	private CrawlerBuscaItemService crawlerBuscaItemService;
	//@Autowired
	//private CrawlerBuscaUrlService buscasMLService;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private MailService mailService;
	@Autowired
	private TenantSettingsService tenantSettingsService;
	//@Autowired
	//private ItemProdutoService itemProdutoService;

	/*
	public BaseCrawlerAnalise(CrawlerBuscaItemService crawlerBuscaItemService,
			CrawlerBuscaUrlService buscasMLService, ApplicationContext context, MailService mailService,
			ItemProdutoService itemProdutoService) {
		super();
		this.crawlerBuscaItemService = crawlerBuscaItemService;
		//this.buscasMLService = buscasMLService;
		this.context = context;
		this.mailService = mailService;
		//this.itemProdutoService = itemProdutoService;
	}*/

	public BaseCrawlerAnalise() {
	}

	public void build(CrawlerBuscaUrl buscasMercadoLivre, String urlLink) {
		log.debug("Thread - baseCrawlerAnaliseThread - metodod: baseCrawlerAnaliseThread START -- id: {}, link: {}", buscasMercadoLivre.getId(), urlLink);
		this.context.getBean(IndexTenantDynamic.class).setTenantId(buscasMercadoLivre.getTenantId());

		BuscaCrawler<CrawlerItem> buscaCrawler = new BuscarItemMercadoLivre();
		List<CrawlerItem> itensList = new ArrayList<>();
		itensList.addAll(buscaCrawler.analisarUrl(urlLink));

		List<CrawlerBuscaItem> listaProduto = crawlerBuscaItemService.buscarPorIdUriBuscasAndStatus(buscasMercadoLivre, true);
		log.debug("Thread - baseCrawlerAnaliseThread - entrou Try {}, {}, {}, {}", buscasMercadoLivre.getId(), urlLink,
				listaProduto.size(), itensList.size());
		if (listaProduto.isEmpty()) {
			List<CrawlerBuscaItem> itensNews = new ArrayList<>();
			log.debug("Thread - baseCrawlerAnaliseThread - entrou Try lista null -- ");
			for (CrawlerItem result : itensList) {
				// System.out.println("result: "+result.getId());
				CrawlerBuscaItem item = new CrawlerBuscaItem();
				item.setCrawlerBuscaItemPK(new CrawlerBuscaItem.CrawlerBuscaItemPK());
				item.setCrawlerBuscaUrl(buscasMercadoLivre);
				item.setCrawlerItem(result);
				item.getCrawlerBuscaItemPK().setTenantId(buscasMercadoLivre.getTenantId());
				item.setAtivo(true);
				//item.setNew(true);				
				itensNews.add(item);
			}
			crawlerBuscaItemService.adicionarTodos(itensNews);
		} else {
			if (!(itensList.isEmpty())) {
				List<CrawlerItem> newList = new ArrayList<>();
				log.debug("Thread - baseCrawlerAnaliseThread - ItensList -- retornou itens do crawler - idBusca: ");
				newList.addAll(this.verificarItemsVendidosAndEnviados(buscasMercadoLivre, listaProduto, itensList));
				// System.out.println("Lista baseCrawlerAnaliseThread - "+itensList.size()+"
				// "+newList.size());
				itensList.clear();
				itensList.addAll(newList);
			} else {
				/**
				 * Lista do Crawler - Mercadolivre - Vazia - Não existe produto, ou deu erro de conexao, entre outro motivos
				 */
				log.debug("Thread - baseCrawlerAnaliseThread - ItensList vazia (null) - não retornou nenhuma itens do crawler MercadoLivre");
				//buscasMercadoLivre.setErrorLink(true);
				//buscasMLService.atualizar(buscasMercadoLivre.getId(), buscasMercadoLivre);
				Thread.currentThread().interrupt();
			}
		}
		this.mountMailEnviar(buscasMercadoLivre, urlLink, itensList);
	}

	
	private List<CrawlerItem> verificarItemsVendidosAndEnviados(CrawlerBuscaUrl buscasMercadoLivre,
			List<CrawlerBuscaItem> listaProdutoDB, List<CrawlerItem> listAtual) {
		log.debug("Thread - baseCrawlerAnaliseThread - Metodo - verificarItemsVendidosAndEnviados START, IDBUSCA: {}", listaProdutoDB.size());
		List<CrawlerBuscaItem> itensListBD = new ArrayList<>();
		//itensMercadoLivreService.atualizarTodos(listaProduto);
		/*
		 * if (listaProduto.isEmpty()) { log.
		 * debug("verificarItemsVendidosAndEnviados - nenhum resultado salvo no BancoDados - Lista Nova"
		 * ); return listAtual; } else {
		 */
		log.debug("verificarItemsVendidosAndEnviados - resultado salvo no BancoDados - entrou else");
		for (Iterator<CrawlerBuscaItem> itemDB = listaProdutoDB.iterator(); itemDB.hasNext();) {
			CrawlerBuscaItem item = itemDB.next();
			// log.debug("verificarItemsVendidosAndEnviados - Entrou no FOR - Validar
			// itens");
			// System.out.println("entrei for lista DB id: "+ item.getId());
			//item.setAtivo(false);
			for (Iterator<CrawlerItem> atual = listAtual.iterator(); atual.hasNext();) {
				CrawlerItem itemAtual = atual.next();
				// System.out.println("entrei for lista DB id: "+ itemAtual.getId());
				if (item.getCrawlerItem().getCrawlerItemPK().getId().equals(itemAtual.getCrawlerItemPK().getId())) {
					// System.out.println("entrei for lista Atual Valido if id igual
					// "+item.getId()+" "+itemAtual.getId());
					if (item.getCrawlerItem().getValor() == itemAtual.getValor()) {
						// System.out.println("entrei for lista valor true "+item.getValor()+" "+itemAtual.getValor());
						atual.remove();
					}
					itemDB.remove();
				}
			}
		}
		
		log.debug("verificarItemsVendidosAndEnviados - FOR Concluido Sucesso - listas db e atua size: {} e {}", listaProdutoDB.size(), itensListBD.size());
		if (listaProdutoDB.isEmpty() && listAtual.isEmpty()) {
			log.debug("---- Lista Empety -----");
			return listAtual;
		}
		for (Iterator<CrawlerBuscaItem> itemDB = listaProdutoDB.iterator(); itemDB.hasNext();) {
			CrawlerBuscaItem item = itemDB.next();
			item.setAtivo(false);
			item.getCrawlerItem().setAtivo(false);
			//item.setNew(false);
			//System.out.println("ativo: "+item.isAtivo());
			itensListBD.add(item);
			//log.debug("lista antiga: {}", item.toString());
		}
		for (Iterator<CrawlerItem> atual = listAtual.iterator(); atual.hasNext();) {
			CrawlerItem itemAtual = atual.next();
			CrawlerBuscaItem crawlerBuscaItem = new CrawlerBuscaItem();
			crawlerBuscaItem.setCrawlerBuscaUrl(buscasMercadoLivre);
			crawlerBuscaItem.setCrawlerItem(itemAtual);

			crawlerBuscaItem.setCrawlerBuscaItemPK(new CrawlerBuscaItem.CrawlerBuscaItemPK());
			crawlerBuscaItem.getCrawlerBuscaItemPK().setCrawlerBuscaUrlPk(buscasMercadoLivre.getId());
			crawlerBuscaItem.getCrawlerBuscaItemPK().setCrawlerItemPk(itemAtual.getCrawlerItemPK());
			crawlerBuscaItem.getCrawlerBuscaItemPK().setTenantId(buscasMercadoLivre.getTenantId());
			crawlerBuscaItem.setAtivo(true);
			//log.debug("lista nova: {}",crawlerBuscaItem.toString());
			//crawlerBuscaItem.setNew(true);
			//itemAtual.setDateCreated(Instant.now());
			//itemAtual.getIdUriBuscas().containsAll(itemAtual.getIdUriBuscas());
			/** Old Logica Aparetemente desnecessario
			if(Optional.ofNullable(itemAtual.getIdUriBuscas()).isEmpty() || !itemAtual.getIdUriBuscas().contains(buscasMercadoLivre.getId())) {
				System.out.println("optional isempty id uri");
				itemAtual.setIdUriBuscas(Arrays.asList(buscasMercadoLivre.getId()));
				if(Optional.ofNullable(itemAtual.getTenantID()).isEmpty() || !itemAtual.getTenantID().contains(buscasMercadoLivre.getTenantID())) {
					itemAtual.setTenantID(Arrays.asList(buscasMercadoLivre.getTenantID()));
					itemAtual.setDate_created(Instant.now());
				}
			}*/
			
			itensListBD.add(crawlerBuscaItem);
		}
		log.debug("verificarItemsVendidosAndEnviados - Atualizar Lista de Itens no BD");
		crawlerBuscaItemService.atualizarTodos(itensListBD);
		return listAtual;
		// }
	}
	/**
	 * Pega a lista e monta o email a ser encaminhado
	 * @param buscasMercadoLivre
	 * @param urlLink
	 * @param itensList
	 */
	private void mountMailEnviar(CrawlerBuscaUrl buscasMercadoLivre, String urlLink, List<CrawlerItem> itensList) {
		log.debug("Metodo mountMailEnviar - Start - list index size {}" + itensList.size());
		/*ItemProduto itemProduto = this.itemProdutoService.buscarItensPorId(buscasMercadoLivre.getIdProduto());
		StringBuilder strBuilder = new StringBuilder();
		StringBuilder strAssuntoBuilder = new StringBuilder(
				itemProduto.getModelo() + " - " + buscasMercadoLivre.getCondicao().getCondicao());*/
		StringBuilder strBuilder = new StringBuilder();
		StringBuilder strAssuntoBuilder = new StringBuilder(
				buscasMercadoLivre.getIdProduto().getModelo() + " - " + buscasMercadoLivre.getCondicao().getCondicao());
		// String assunto = itemProduto.getModelo() + " - " +
		// buscasMercadoLivre.getCondicao().getCondicao();
		boolean enviarEmail = tenantSettingsService.enviarEmailVazio(buscasMercadoLivre.getTenantId());
		String mensagem;
		if (itensList.isEmpty() && enviarEmail == true) {
			strAssuntoBuilder.append(" - Vazio");
			strBuilder.append("Não tem nenhum item novo ou a lista esta com error");
			mensagem = buscasMercadoLivre.getDescricao() + "\n \n" + urlLink + "\n \n" + strBuilder.toString();
			this.enviarEmailCrawler(strAssuntoBuilder.toString(), mensagem);
		} else if(!itensList.isEmpty()) {
			for (CrawlerItem item : itensList) {
				strBuilder.append(item.getLinkUrl() +  "\n Titulo: " +
						item.getTitulo() + "\n Condicao: " + 
						item.getCondicao() + "\n Tipo: " + 
						item.getTipoAnuncio() + "\n Valor: " +
						item.getValor() + "\n \n");
			}
			mensagem = buscasMercadoLivre.getDescricao() + "\n \n" + urlLink + "\n \n" + strBuilder.toString();
			this.enviarEmailCrawler(strAssuntoBuilder.toString(), mensagem);
		}

		// assunto = itemProduto.getModelo() + " - " +
		// buscasMercadoLivre.getCondicao().getCondicao();
		//mensagem = buscasMercadoLivre.getDescricao() + "\n \n" + urlLink + "\n \n" + strBuilder.toString();
		// log.debug("Metodo mountMailEnviar - mensagem {}", mensagem);
		//this.mailService.sendMail("revendascerta@gmail.com", strAssuntoBuilder.toString(), mensagem);
	}
	
	private void enviarEmailCrawler(String assunto, String mensagem) {
		this.mailService.sendMail("revendascerta@gmail.com", assunto, mensagem);		
	}
}
