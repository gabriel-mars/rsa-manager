package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.FeriasInterface;
import br.inatel.ssic.rsa.model.entity.Ferias;

@Repository
public class FeriasDAO extends BaseDAO<Ferias, Long> implements FeriasInterface{

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findFeriasByPeriodo(Ferias ferias) {
		Query query = manager.createNativeQuery("SELECT F.id, P.nome, F.inicio_ferias, F.fim_ferias "
				+ "FROM ferias F "
				+ "INNER JOIN colaborador C ON C.pessoa_id = F.colaborador_id "
				+ "INNER JOIN pessoa P ON P.id = C.pessoa_id "
				+ "WHERE DATE(F.inicio_ferias) >= DATE(TO_DATE(?, 'DD/MM/YYY'))")
				.setParameter(1, ferias.getInicioFerias());
		return query.getResultList();
	}
	
	public void updateFerias(Ferias ferias) {
		Query query = manager.createNativeQuery("UPDATE ferias SET colaborador_id = ?, inicio_ferias = ?, fim_ferias = ? WHERE id = ?")
				.setParameter(1, ferias.getColaboradorId())
				.setParameter(2, ferias.getInicioFerias())
				.setParameter(3, ferias.getFimFerias())
				.setParameter(4, ferias.getId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findFeriasBySemana(Ferias ferias) {
		Query query = manager.createNativeQuery("SELECT F.id, P.nome, F.inicio_ferias, F.fim_ferias "
				+ "FROM ferias F "
				+ "INNER JOIN colaborador C ON C.pessoa_id = F.colaborador_id "
				+ "INNER JOIN pessoa P ON P.id = C.pessoa_id "
				+ "WHERE DATE(F.inicio_ferias) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.inicio_ferias) <= DATE(TO_DATE(?, 'DD/MM/YYY'))")
				.setParameter(1, ferias.getInicioFerias())
				.setParameter(2, ferias.getFimFerias());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findFeriasByNRO(Ferias ferias) {
		Query query = manager.createNativeQuery("SELECT NR.nome_nro "
				+ "FROM inatel_nro NR "
				+ "INNER JOIN ferias F ON F.colaborador_id = NR.colaborador_id "
				+ "WHERE DATE(F.inicio_ferias) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.inicio_ferias) <= DATE(TO_DATE(?, 'DD/MM/YYY'))")
				.setParameter(1, ferias.getInicioFerias())
				.setParameter(2, ferias.getFimFerias());
		return query.getResultList();
	}
}
