package br.api.rev.session;


public class SessionRevendaServiceImp implements SessionService{

	private String tenanteId;
	private Long idUser;
	private String session;
	
	
	public SessionRevendaServiceImp(String tenanteId, Long idUser) {
		super();
		this.tenanteId = tenanteId;
		this.idUser = idUser;
	}
	
	public SessionRevendaServiceImp(String session) {
		super();
		this.session = session;
	}

	@Override
	public String gerarSession() {
		this.session = this.tenanteId +"/"+ Long.toHexString(this.idUser);
		return this.session;
	}
}

