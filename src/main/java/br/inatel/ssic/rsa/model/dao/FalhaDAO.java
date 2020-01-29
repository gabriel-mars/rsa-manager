package br.inatel.ssic.rsa.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.FalhaInterface;
import br.inatel.ssic.rsa.model.entity.Falha;

@Repository
public class FalhaDAO extends BaseDAO<Falha, Long> implements FalhaInterface{
	
	@PersistenceContext
	private EntityManager manager;

}
