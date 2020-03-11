package br.inatel.ssic.rsa.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.FeriasInterface;
import br.inatel.ssic.rsa.model.entity.Ferias;

@Repository
public class FeriasDAO extends BaseDAO<Ferias, Long> implements FeriasInterface{

	@PersistenceContext
	private EntityManager manager;
}
