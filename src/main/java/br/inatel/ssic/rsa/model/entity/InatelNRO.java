package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "inatel_nro")
public class InatelNRO extends AbstractEntity<Long>{
	
	@Column(name = "colaborador_id")
	private Long colaborador_id;
	
	@Column(name = "nome_nro", length = 50)
	private String nomeNro;
	
	public Long getColaborador_id() {
		return colaborador_id;
	}
	public void setColaborador_id(Long colaborador_id) {
		this.colaborador_id = colaborador_id;
	}
	public String getNomeNro() {
		return nomeNro;
	}
	public void setNomeNro(String nomeNro) {
		this.nomeNro = nomeNro;
	}
}
