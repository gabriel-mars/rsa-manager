package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.RelatorioInterface;
import br.inatel.ssic.rsa.model.entity.Item;

@Repository
public class RelatorioDAO implements RelatorioInterface{
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByColaborador(Item item) {
		Query query = manager.createNativeQuery("SELECT I.data_analise, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado' OR I.status = 'Aprovado') AS sum_itens_ap_re "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND I.inspetor = ? AND I.data_analise BETWEEN ? AND ? "
				+ "GROUP BY I.data_analise ORDER BY I.data_analise")
				.setParameter(1, item.getCentroRsa())
				.setParameter(2, item.getInspetor())
				.setParameter(3, item.getDataAnalise())
				.setParameter(4, item.getDataEnvio());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAvg(Item item) {
		Query query = manager.createNativeQuery("SELECT I.data_analise, "
				+ "COUNT (DISTINCT I.inspetor) FILTER (WHERE I.centro_rsa = ?) AS num_sup_rsa, "
				+ "COUNT (I.status) FILTER (WHERE I.centro_rsa = ? AND I.status = 'Rejeitado' OR I.status = 'Aprovado') AS num_itens "
				+ "FROM item I WHERE I.data_analise BETWEEN ? AND ? "
				+ "GROUP BY I.data_analise ORDER BY I.data_analise")
				.setParameter(1, item.getCentroRsa())
				.setParameter(2, item.getCentroRsa())
				.setParameter(3, item.getDataAnalise())
				.setParameter(4, item.getDataEnvio());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findStatusItemColab(Item item) {
		Query query = manager.createNativeQuery("SELECT COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados "
				+ "FROM item I "
				+ "WHERE I.inspetor = ? AND I.data_analise BETWEEN ? AND ?")
				.setParameter(1, item.getInspetor())
				.setParameter(2, item.getDataAnalise())
				.setParameter(3, item.getDataEnvio());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findStatusItemTime(Item item) {
		Query query = manager.createNativeQuery("SELECT COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND I.data_analise BETWEEN ? AND ?")
				.setParameter(1, item.getCentroRsa())
				.setParameter(2, item.getDataAnalise())
				.setParameter(3, item.getDataEnvio());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByMes(Item item) {
		Query query = manager.createNativeQuery("SELECT I.data_analise, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND I.data_analise BETWEEN ? AND ? "
				+ "GROUP BY I.data_analise ORDER BY I.data_analise")
				.setParameter(1, item.getCentroRsa())
				.setParameter(2, item.getDataAnalise())
				.setParameter(3, item.getDataEnvio());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findItensTrabalhadosMes(Item item) {
		Query query = manager.createNativeQuery("SELECT COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND I.data_analise BETWEEN ? AND ?")
				.setParameter(1, item.getCentroRsa())
				.setParameter(2, item.getDataAnalise())
				.setParameter(3, item.getDataEnvio());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findItensTotaisMes(Item item) {
		Query query = manager.createNativeQuery("SELECT COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado' OR I.status = 'Aprovado') AS sum_itens_ap_re "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND I.data_analise BETWEEN ? AND ?")
				.setParameter(1, item.getCentroRsa())
				.setParameter(2, item.getDataAnalise())
				.setParameter(3, item.getDataEnvio());
		return query.getResultList();
	}
}
