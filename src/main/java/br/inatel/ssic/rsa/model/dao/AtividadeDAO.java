package br.inatel.ssic.rsa.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.AtividadeInterface;
import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.entity.Atividade;
import br.inatel.ssic.rsa.model.entity.Item;

@Repository
public class AtividadeDAO extends BaseDAO<Atividade, Long> implements AtividadeInterface{
	
	@PersistenceContext
	private EntityManager manager;
	
	public void saveAll(List<Item> itens) {
		
		for(Item i : itens) {
			manager.persist(i);
		}
		
		manager.flush();
		manager.clear();
	}
}
