package br.inatel.ssic.rsa.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.FeriasInterface;
import br.inatel.ssic.rsa.model.dao.FeriasDAO;
import br.inatel.ssic.rsa.model.entity.Ferias;

@Service
@Transactional(readOnly = false)
public class FeriasService implements FeriasInterface{
	
	@Autowired
	private FeriasDAO dao;
	
	@Override
	public void save(Ferias ferias) {
		dao.save(ferias);
	}

	@Override
	public void update(Ferias ferias) {
		dao.updateFerias(ferias);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Ferias findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ferias> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findFeriasByPeriodo(Ferias ferias) {
		return dao.findFeriasByPeriodo(ferias);
	}

}
