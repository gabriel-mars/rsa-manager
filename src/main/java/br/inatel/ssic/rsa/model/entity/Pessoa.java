package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
public class Pessoa extends AbstractEntity<Long>{
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "email", nullable = false, length = 50)
	private String email;
	
	@Column(name = "cpf", nullable = false, length = 15)
	private String cpf;
	
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@Column(name = "senha", length = 50)
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
