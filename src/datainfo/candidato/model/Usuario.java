package datainfo.candidato.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

/**
 * Modelo que representa a tabel de Usuario_externo do banco
 * @author Rubens
 *
 */
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	
	private String telefone;
	
	private String email;
		

	private Boolean ativo;
	
		
	private String cpf;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@ForeignKey(name="funcaos_fk")
	private FuncaoUsuarioExterno funcaos = new FuncaoUsuarioExterno();

	@ManyToOne(fetch=FetchType.EAGER)
	@ForeignKey(name="perfils_fk")
	private Perfil perfils = new Perfil();
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
		
	public void setAtivo(Boolean ativo) {
		if (ativo == null) 
			this.ativo = false;
		
		this.ativo = ativo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public FuncaoUsuarioExterno getFuncaos() {
		return funcaos;
	}

	public void setFuncaos(FuncaoUsuarioExterno funcaos) {
		this.funcaos = funcaos;
	}

	public Perfil getPerfils() {
		return perfils;
	}

	public void setPerfils(Perfil perfils) {
		this.perfils = perfils;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
