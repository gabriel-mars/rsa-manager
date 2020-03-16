package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "item")
@DynamicUpdate
public class Item extends AbstractEntity<Long>{
	
	@Column(name = "ordem", nullable = false, length = 7)
	private String ordem;
	
	@Column(name = "item", nullable = false, length = 100)
	private String item;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "status", nullable = false, length = 21)
	private String status;
	
	@Column(name = "ofensor", nullable = false, length = 20)
	private String ofensor;
	
	@Column(name = "data_envio", nullable = false, length = 10)
	private String dataEnvio;
	
	@Column(name = "hora_envio", nullable = false, length = 8)
	private String horaEnvio;
	
	@Column(name = "centro_rsa", nullable = false, length = 8)
	private String centroRsa;
	
	@Column(name = "data_analise", nullable = false, length = 10)
	private String dataAnalise;
	
	@Column(name = "hora_analise", nullable = false, length = 8)
	private String horaAnalise;
	
	@Column(name = "inspetor", nullable = false, length = 30)
	private String inspetor;
	
	@Column(name = "site", nullable = false, length = 25)
	private String site;

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOfensor() {
		return ofensor;
	}

	public void setOfensor(String ofensor) {
		this.ofensor = ofensor;
	}

	public String getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getHoraEnvio() {
		return horaEnvio;
	}

	public void setHoraEnvio(String horaEnvio) {
		this.horaEnvio = horaEnvio;
	}

	public String getCentroRsa() {
		return centroRsa;
	}

	public void setCentroRsa(String centroRsa) {
		this.centroRsa = centroRsa;
	}

	public String getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(String dataAnalise) {
		this.dataAnalise = dataAnalise;
	}

	public String getHoraAnalise() {
		return horaAnalise;
	}

	public void setHoraAnalise(String horaAnalise) {
		this.horaAnalise = horaAnalise;
	}

	public String getInspetor() {
		return inspetor;
	}

	public void setInspetor(String inspetor) {
		this.inspetor = inspetor;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}