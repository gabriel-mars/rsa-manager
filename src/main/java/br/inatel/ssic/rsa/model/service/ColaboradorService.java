package br.inatel.ssic.rsa.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.ColaboradorInterface;
import br.inatel.ssic.rsa.model.dao.ColaboradorDAO;
import br.inatel.ssic.rsa.model.entity.Colaborador;

@Service
@Transactional(readOnly = false)
public class ColaboradorService implements ColaboradorInterface{

	@Autowired
	private ColaboradorDAO dao;
	
	@Override
	public void save(Colaborador colaborador) {
		dao.save(colaborador);
	}

	@Override
	public void update(Colaborador colaborador) {
		dao.update(colaborador);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Colaborador findById(Long id) {
		return dao.findById(id);
	}

}
