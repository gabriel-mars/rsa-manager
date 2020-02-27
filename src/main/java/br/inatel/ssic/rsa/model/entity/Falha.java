package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "falha")
@DynamicUpdate
public class Falha extends AbstractEntity<Long> {
	
	@Column(name = "site", nullable = false, length = 25)
	private String site;
	
	@Column(name = "colaborador", nullable = false, length = 30)
	private String colaborador;
	
	@Column(name = "data_falha", nullable = false, length = 10)
	private String data;
	
	@Column(name = "falha_primaria", nullable = false, length = 80)
	private String falhaPrimaria;
	
	@Column(name = "falha_secundaria", nullable = false, length = 110)
	private String falhaSecundaria;
	
	@Column(name = "criticidade", length = 15)
	private String criticidade;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "centro_rsa", nullable = false, length = 8)
	private String centroRsa;
	
	@Column(name = "reportado", nullable = false, length = 14)
	private String reportado;
	
	@Column(name = "regional", nullable = false, length = 3)
	private String regional;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFalhaPrimaria() {
		return falhaPrimaria;
	}

	public void setFalhaPrimaria(String falhaPrimaria) {
		this.falhaPrimaria = falhaPrimaria;
	}

	public String getFalhaSecundaria() {
		return falhaSecundaria;
	}

	public void setFalhaSecundaria(String falhaSecundaria) {
		this.falhaSecundaria = falhaSecundaria;
	}

	public String getCriticidade() {
		return criticidade;
	}

	public void setCriticidade(String criticidade) {
		this.criticidade = criticidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCentroRsa() {
		return centroRsa;
	}

	public void setCentroRsa(String centroRsa) {
		this.centroRsa = centroRsa;
	}

	public String getReportado() {
		return reportado;
	}

	public void setReportado(String reportado) {
		this.reportado = reportado;
	}

	public String getRegional() {
		return regional;
	}

	public void setRegional(String regional) {
		this.regional = regional;
	}
}
