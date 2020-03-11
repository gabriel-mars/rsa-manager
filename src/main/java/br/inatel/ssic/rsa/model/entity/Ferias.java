package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "ferias")
@DynamicUpdate
public class Ferias extends AbstractEntity<Long> {
	@Column(name = "inicio_ferias", nullable = false, length = 12)
	private String inicioFerias;
	
	@Column(name = "fim_ferias", nullable = false, length = 12)
	private String fimFerias;
	
	@Column(name = "colaborador_id")
	private Long colaboradorId;

	public String getInicioFerias() {
		return inicioFerias;
	}

	public void setInicioFerias(String inicioFerias) {
		this.inicioFerias = inicioFerias;
	}

	public String getFimFerias() {
		return fimFerias;
	}

	public void setFimFerias(String fimFerias) {
		this.fimFerias = fimFerias;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}
}
