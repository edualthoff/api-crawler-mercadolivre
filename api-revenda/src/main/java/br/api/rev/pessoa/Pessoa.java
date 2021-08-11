package br.api.rev.pessoa;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.api.rev.modulo.ModuloAplicacao;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_pessoa")
@EntityListeners(AuditingEntityListener.class)
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pessoa_id")
	private Long id;
	
	@Column(name = "nome")
	@NotBlank()
	@Setter(value = AccessLevel.NONE)
	private String nome;

	@Column(name = "sobrenome")
	@Setter(value = AccessLevel.NONE)
	private String sobrenome;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "cpf")
	private String cpf;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "data_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date data_created;
	
	@Column(name = "data_update", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date data_modified;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "email_produto")
	private String emailProduto;
	
	@Column(name = "usuario_id")
	private String usuarioId;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "tb_pessoa_tb_modulo",
    joinColumns = {
            @JoinColumn(name = "pessoa_id_tb_pessoa", referencedColumnName = "pessoa_id", updatable = false)},
    inverseJoinColumns = {
            @JoinColumn(name = "modulo_id_tb_modulo", referencedColumnName = "modulo_id", updatable = false)})
	private List<ModuloAplicacao> moduloAplicacao;
	
	public void setNome(String nome) {
		this.nome = nome.toLowerCase();
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome.toLowerCase();
	}
}
