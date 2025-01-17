package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.ColaboradorInterface;
import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.InatelNRO;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.entity.Pessoa;

@Repository
public class ColaboradorDAO extends BaseDAO<Colaborador, Long> implements ColaboradorInterface{
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findByOrganizacao(String org) {
		Query query = manager.createNativeQuery("SELECT P.id as pessoa_id, P.nome, P.email "
				+ "FROM pessoa P "
				+ "INNER JOIN colaborador C On C.pessoa_id = P.id "
				+ "WHERE C.organizacao = ?")
				.setParameter(1, org);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> verifyLogin(String email, String senha) {
		Query query = manager.createNativeQuery("SELECT P.id as pessoa_id, P.nome, P.email, P.telefone, P.cpf, P.senha "
				+ "FROM pessoa P "
				+ "WHERE P.email = ? AND P.senha = ?")
				.setParameter(1, email)
				.setParameter(2, senha);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findByLogin(Long id) {
		Query query = manager.createNativeQuery("SELECT C.id_ericsson, C.organizacao FROM colaborador C WHERE C.pessoa_id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findByUpdate(Long id) {
		Query query = manager.createNativeQuery("SELECT P.id as pessoa_id, P.nome, P.email, P.cpf, P.telefone, C.id_ericsson, C.data_inicio, C.organizacao "
				+ "FROM pessoa P "
				+ "INNER JOIN colaborador C On C.pessoa_id = P.id "
				+ "WHERE P.id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}
	
	public void updateColaborador(Colaborador colaborador) {
		Query queryPessoa = manager.createNativeQuery("UPDATE pessoa SET nome = ?, email = ?, cpf = ?, telefone = ? WHERE id = ?")
				.setParameter(1, colaborador.getNome())
				.setParameter(2, colaborador.getEmail())
				.setParameter(3, colaborador.getCpf())
				.setParameter(4, colaborador.getTelefone())
				.setParameter(5, colaborador.getId());
		
		Query queryColab = manager.createNativeQuery("UPDATE colaborador SET id_ericsson = ?, data_inicio = ?, organizacao = ? WHERE pessoa_id = ?")
				.setParameter(1, colaborador.getIdEricsson())
				.setParameter(2, colaborador.getDataInicioRSA())
				.setParameter(3, colaborador.getOrganizacao())
				.setParameter(4, colaborador.getId());
		
		queryPessoa.executeUpdate();
		queryColab.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findByAtividade(String org) {
		Query query = manager.createNativeQuery("SELECT DISTINCT I.inspetor FROM item I "
				+ "WHERE I.centro_rsa = ? GROUP BY I.inspetor")
				.setParameter(1, org);
		return query.getResultList();
	}

	@Override
	public void updateSenha(Colaborador colaborador) {
		Query query = manager.createNativeQuery("UPDATE pessoa SET senha = ? WHERE email = ?")
				.setParameter(1, colaborador.getSenha())
				.setParameter(2, colaborador.getEmail());
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByColaboradoresSemEscala(String org) {
		Query query = manager.createNativeQuery("SELECT P.nome, P.id "
				+ "FROM pessoa P "
				+ "INNER JOIN colaborador C ON C.pessoa_id = P.id "
				+ "LEFT JOIN escala_colaborador EC ON EC.colaborador_id = P.id "
				+ "WHERE C.organizacao = ? AND EC.colaborador_id IS NULL")
				.setParameter(1, org);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByColaboradoresEscala(Long id) {
		Query query = manager.createNativeQuery("SELECT P.nome, EC.id "
				+ "FROM escala_colaborador EC "
				+ "INNER JOIN escala E ON E.id = EC.escala_id "
				+ "INNER JOIN colaborador C ON C.pessoa_id = EC.colaborador_id "
				+ "INNER JOIN pessoa P ON P.id = C.pessoa_id "
				+ "WHERE E.id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}

	@Override
	public void linkNames(InatelNRO colaborador) {
		manager.persist(colaborador);
		manager.flush();
		manager.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findLinkOrganizacao(String org) {
		Query query = manager.createNativeQuery("SELECT P.id as pessoa_id, P.nome, P.email "
				+ "FROM pessoa P "
				+ "INNER JOIN colaborador C ON C.pessoa_id = P.id "
				+ "LEFT JOIN inatel_nro NR ON NR.colaborador_id = P.id "
				+ "WHERE C.organizacao = ? AND NR.colaborador_id IS NULL")
				.setParameter(1, org);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findLinkAtividade(String org) {
		Query query = manager.createNativeQuery("SELECT DISTINCT I.inspetor "
				+ "FROM item I "
				+ "LEFT JOIN inatel_nro NR ON NR.nome_nro = I.inspetor "
				+ "WHERE I.centro_rsa = ? AND NR.nome_nro IS NULL "
				+ "GROUP BY I.inspetor")
				.setParameter(1, org);
		return query.getResultList();
	}
}
