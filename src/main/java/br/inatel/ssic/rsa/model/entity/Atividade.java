package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "atividade")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
public class Atividade extends AbstractEntity<Long>{
	@Column(name = "site", nullable = false, length = 25)
	private String site;
	
	@Column(name = "os", nullable = false, length = 6)
	private Integer os;
	
	@Column(name = "empresa", nullable = false, length = 6)
	private String empresa;

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
}
