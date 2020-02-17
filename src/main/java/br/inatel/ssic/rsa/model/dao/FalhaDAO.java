package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.FalhaInterface;
import br.inatel.ssic.rsa.model.entity.Falha;

@Repository
public class FalhaDAO extends BaseDAO<Falha, Long> implements FalhaInterface{
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFalhasColab(Falha falha) {
		Query query = manager.createNativeQuery("SELECT DISTINCT COUNT (F.falha_primaria) FILTER (WHERE F.colaborador = ?) AS falhas, F.falha_primaria "
				+ "FROM falha F "
				+ "WHERE F.centro_RSA = ? AND F.colaborador = ? AND DATE(F.data_falha) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.data_falha) <= DATE(TO_DATE(?, 'DD/MM/YYY'))"
				+ "GROUP BY F.falha_primaria")
				.setParameter(1, falha.getColaborador())
				.setParameter(2, falha.getCentroRsa())
				.setParameter(3, falha.getColaborador())
				.setParameter(4, falha.getData())
				.setParameter(5, falha.getFalhaPrimaria()); // Data final de busca
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFalhasColabDetail(Falha falha) {
		Query query = manager.createNativeQuery("SELECT F.site, F.data_falha, F.falha_secundaria, F. descricao "
				+ "FROM falha F WHERE F.centro_RSA = ? AND F.colaborador = ? AND DATE(F.data_falha) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.data_falha) <= DATE(TO_DATE(?, 'DD/MM/YYY'))")
				.setParameter(1, falha.getCentroRsa())
				.setParameter(2, falha.getColaborador())
				.setParameter(3, falha.getData())
				.setParameter(4, falha.getFalhaPrimaria()); // Data final de busca
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFalhasTime(Falha falha) {
		Query query = manager.createNativeQuery("SELECT DISTINCT COUNT (F.falha_primaria) FILTER (WHERE F.centro_RSA = ?) AS falhas, F.falha_primaria "
				+ "FROM falha F "
				+ "WHERE F.centro_RSA = ? AND DATE(F.data_falha) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.data_falha) <= DATE(TO_DATE(?, 'DD/MM/YYY')) "
				+ "GROUP BY F.falha_primaria")
				.setParameter(1, falha.getCentroRsa())
				.setParameter(2, falha.getCentroRsa())
				.setParameter(3, falha.getData())
				.setParameter(4, falha.getFalhaPrimaria()); // Data final de busca
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFalhasTimeDetail(Falha falha) {
		Query query = manager.createNativeQuery("SELECT F.site, F.data_falha, F.falha_secundaria, F. descricao "
				+ "FROM falha F "
				+ "WHERE F.centro_RSA = ? AND DATE(F.data_falha) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.data_falha) <= DATE(TO_DATE(?, 'DD/MM/YYY'))")
				.setParameter(1, falha.getCentroRsa())
				.setParameter(2, falha.getData())
				.setParameter(3, falha.getFalhaPrimaria()); // Data final de busca
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFalhasColabSemanal(Falha falha) {
		Query query = manager.createNativeQuery("SELECT DISTINCT COUNT (F.falha_primaria) FILTER (WHERE F.colaborador = ?) AS falhas, F.falha_primaria "
				+ "FROM falha F "
				+ "WHERE F.centro_RSA = ? AND F.colaborador = ? AND DATE(F.data_falha) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.data_falha) <= DATE(TO_DATE(?, 'DD/MM/YYY'))"
				+ "GROUP BY F.falha_primaria")
				.setParameter(1, falha.getColaborador())
				.setParameter(2, falha.getCentroRsa())
				.setParameter(3, falha.getColaborador())
				.setParameter(4, falha.getData())
				.setParameter(5, falha.getFalhaPrimaria()); // Data final de busca
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFalhasColabDetailSemanal(Falha falha) {
		Query query = manager.createNativeQuery("SELECT F.site, F.data_falha, F.falha_secundaria, F. descricao "
				+ "FROM falha F WHERE F.centro_RSA = ? AND F.colaborador = ? AND DATE(F.data_falha) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(F.data_falha) <= DATE(TO_DATE(?, 'DD/MM/YYY'))")
				.setParameter(1, falha.getCentroRsa())
				.setParameter(2, falha.getColaborador())
				.setParameter(3, falha.getData())
				.setParameter(4, falha.getFalhaPrimaria()); // Data final de busca
		return query.getResultList();
	}
}
