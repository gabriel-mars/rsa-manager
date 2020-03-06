package br.inatel.ssic.rsa.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "escala")
@DynamicUpdate
public class Escala extends AbstractEntity<Long>{
	
	@Column(name = "titulo", nullable = false, length = 50)
	private String titulo;
	
	@Column(name = "inicio_escala", nullable = false, length = 25)
	private String inicioEscala;
	
	@Column(name = "fim_escala", nullable = false, length = 25)
	private String fimEscala;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getInicioEscala() {
		return inicioEscala;
	}
	public void setInicioEscala(String inicioEscala) {
		this.inicioEscala = inicioEscala;
	}
	public String getFimEscala() {
		return fimEscala;
	}
	public void setFimEscala(String fimEscala) {
		this.fimEscala = fimEscala;
	}
}
