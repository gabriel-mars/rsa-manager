package br.inatel.ssic.rsa.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.FalhaInterface;
import br.inatel.ssic.rsa.model.dao.FalhaDAO;
import br.inatel.ssic.rsa.model.entity.Falha;

@Service
@Transactional(readOnly = false)
public class FalhaService implements FalhaInterface{
	
	@Autowired
	private FalhaDAO dao;

	@Override
	public void save(Falha falha) {
		dao.save(falha);
	}
}
