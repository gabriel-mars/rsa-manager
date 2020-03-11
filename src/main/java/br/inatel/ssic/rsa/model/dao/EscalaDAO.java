package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.EscalaInterface;
import br.inatel.ssic.rsa.model.entity.Escala;
import br.inatel.ssic.rsa.model.entity.EscalaColaborador;

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

	@Override
	public void atribuirEscala(EscalaColaborador escala) {
		Query query = manager.createNativeQuery("INSERT INTO escala_colaborador (colaborador_id, escala_id) VALUES (?, ?)")
				.setParameter(1, escala.getColaboradorId())
				.setParameter(2, escala.getEscalaId());
		query.executeUpdate();
	}

	@Override
	public void removerColaborador(Long id) {
		Query query = manager.createNativeQuery("DELETE FROM escala_colaborador WHERE id = ?")
				.setParameter(1, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByColaborador(Long id) {
		Query query = manager.createNativeQuery("SELECT E.inicio_escala, E.titulo "
				+ "FROM escala E "
				+ "INNER JOIN escala_colaborador EC ON EC.escala_id = E.id "
				+ "WHERE EC.colaborador_id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}
}
