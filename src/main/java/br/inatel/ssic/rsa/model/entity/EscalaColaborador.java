package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "escala_colaborador")
@DynamicUpdate
public class EscalaColaborador extends AbstractEntity<Long>{
	@Column(name = "escala_id")
	private Long escalaId;
	
	@Column(name = "colaborador_id")
	private Long colaboradorId;

	public Long getEscalaId() {
		return escalaId;
	}

	public void setEscalaId(Long escalaId) {
		this.escalaId = escalaId;
	}

	public Long getColaboradorId() {
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId) {
		this.colaboradorId = colaboradorId;
	}
}
