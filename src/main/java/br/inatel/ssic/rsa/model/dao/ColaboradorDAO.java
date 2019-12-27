package br.inatel.ssic.rsa.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.inatel.ssic.rsa.model.base.BaseDAO;
import br.inatel.ssic.rsa.model.base.ColaboradorInterface;
import br.inatel.ssic.rsa.model.entity.Colaborador;

@Repository
public class ColaboradorDAO extends BaseDAO<Colaborador, Long> implements ColaboradorInterface{
	
	@PersistenceContext
	private EntityManager manager;
}
