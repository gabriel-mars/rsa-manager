package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "colaborador")
@PrimaryKeyJoinColumn(name = "pessoa_id")
@DynamicUpdate
public class Colaborador extends Pessoa{
	@Column(name = "id_ericsson", nullable = false, length = 7)
	private String idEricsson;
	
	@Column(name = "data_inicio", nullable = false, length = 10)
	private String dataInicioRSA;
	
	@Column(name = "data_fim", length = 10)
	private String dataFimRSA;
	
	@Column(name = "organizacao", nullable = false, length = 50)
	private String organizacao;
	
	@Transient
	private Pessoa pessoa;

	@Column(name = "estagiario")
	private Boolean estagiario;

	public String getIdEricsson() {
		return idEricsson;
	}

	public void setIdEricsson(String idEricsson) {
		this.idEricsson = idEricsson;
	}

	public String getDataInicioRSA() {
		return dataInicioRSA;
	}

	public void setDataInicioRSA(String dataInicioRSA) {
		this.dataInicioRSA = dataInicioRSA;
	}

	public String getDataFimRSA() {
		return dataFimRSA;
	}

	public void setDataFimRSA(String dataFimRSA) {
		this.dataFimRSA = dataFimRSA;
	}

	public String getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(String organizacao) {
		this.organizacao = organizacao;
	}

	public Boolean getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Boolean estagiario) {
		this.estagiario = estagiario;
	}
}
