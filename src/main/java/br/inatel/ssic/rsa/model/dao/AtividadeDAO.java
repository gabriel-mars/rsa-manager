package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.AtividadeInterface;
import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.entity.Item;

@Repository
public class AtividadeDAO extends BaseDAO<Item, Long> implements AtividadeInterface{
	
	@PersistenceContext
	private EntityManager manager;
	
	public void saveAll(List<Item> itens) {
		
		for(Item i : itens) {
			manager.persist(i);
		}
		
		manager.flush();
		manager.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getAtividades(String data, String organizacao) {
		Query query = manager.createNativeQuery("SELECT DISTINCT I.inspetor, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado' OR I.status = 'Aprovado') AS sum_itens_ap_re, "
				+ "COUNT (I.status) AS sum_total, "
				+ "COUNT(DISTINCT I.site) AS sum_site "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND I.data_analise = ? "
				+ "GROUP BY I.inspetor ORDER BY sum_itens_ap_re DESC")
				.setParameter(1, organizacao)
				.setParameter(2, data);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getAtividadesMensal(String dataInicial, String dataFinal, String organizacao) {
		Query query = manager.createNativeQuery("SELECT DISTINCT I.inspetor, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado' OR I.status = 'Aprovado') AS sum_itens_ap_re, "
				+ "COUNT (I.status) AS sum_total "
				+ "FROM item I "
				+ "WHERE I.centro_rsa = ? AND DATE(I.data_analise) >= DATE(TO_DATE(?, 'DD/MM/YYY')) AND DATE(I.data_analise) <= DATE(TO_DATE(?, 'DD/MM/YYY'))  "
				+ "GROUP BY I.inspetor ORDER BY sum_itens_ap_re DESC")
				.setParameter(1, organizacao)
				.setParameter(2, dataInicial)
				.setParameter(3, dataFinal);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getAtividadesMoreMouths(String data, String organizacao, String last) {
		Query query = manager.createNativeQuery("SELECT DISTINCT I.inspetor, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado' OR I.status = 'Aprovado') AS sum_itens_ap_re, "
				+ "COUNT (I.status) AS sum_total, "
				+ "COUNT(DISTINCT I.site) AS sum_site "
				+ "FROM item I "
				+ "INNER JOIN inatel_nro NR ON NR.nome_nro = I.inspetor "
				+ "INNER JOIN colaborador C ON C.pessoa_id = NR.colaborador_id "
				+ "WHERE I.centro_rsa = ? AND I.data_analise = ? AND DATE(C.data_inicio) <= DATE(TO_DATE(?, 'DD/MM/YYYY')) "
				+ "GROUP BY I.inspetor ORDER BY sum_itens_ap_re DESC")
				.setParameter(1, organizacao)
				.setParameter(2, data)
				.setParameter(3, last);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getAtividadesMinusMouths(String data, String organizacao, String last) {
		Query query = manager.createNativeQuery("SELECT DISTINCT I.inspetor, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado') AS itens_rejeitados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Aprovado') AS itens_aprovados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Abonado') AS itens_abonados, "
				+ "COUNT(I.status) FILTER (WHERE I.status = 'Rejeitado' OR I.status = 'Aprovado') AS sum_itens_ap_re, "
				+ "COUNT (I.status) AS sum_total, "
				+ "COUNT(DISTINCT I.site) AS sum_site "
				+ "FROM item I "
				+ "INNER JOIN inatel_nro NR ON NR.nome_nro = I.inspetor "
				+ "INNER JOIN colaborador C ON C.pessoa_id = NR.colaborador_id "
				+ "WHERE I.centro_rsa = ? AND I.data_analise = ? AND DATE(C.data_inicio) >= DATE(TO_DATE(?, 'DD/MM/YYYY')) "
				+ "GROUP BY I.inspetor ORDER BY sum_itens_ap_re DESC")
				.setParameter(1, organizacao)
				.setParameter(2, data)
				.setParameter(3, last);
		return query.getResultList();
	}
}
