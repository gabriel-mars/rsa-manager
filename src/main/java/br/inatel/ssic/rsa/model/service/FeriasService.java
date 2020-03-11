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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = true)
	public Ferias findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ferias> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}