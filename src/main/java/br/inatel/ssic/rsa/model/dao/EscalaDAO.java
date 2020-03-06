package br.inatel.ssic.rsa.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.EscalaInterface;
import br.inatel.ssic.rsa.model.entity.Escala;

@Repository
public class EscalaDAO extends BaseDAO<Escala, Long> implements EscalaInterface{

	@PersistenceContext
	private EntityManager manager;
	
	public void updateEscala(Escala escala) {
		Query query = manager.createNativeQuery("UPDATE escala SET titulo = ?, inicio_escala = ?, fim_escala = ? WHERE id = ?")
				.setParameter(1, escala.getTitulo())
				.setParameter(2, escala.getInicioEscala())
				.setParameter(3, escala.getFimEscala())
				.setParameter(4, escala.getId());
		query.executeUpdate();
	}
}
