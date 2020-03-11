package br.inatel.ssic.rsa.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.EscalaInterface;
import br.inatel.ssic.rsa.model.dao.EscalaDAO;
import br.inatel.ssic.rsa.model.entity.Escala;
import br.inatel.ssic.rsa.model.entity.EscalaColaborador;

@Service
@Transactional(readOnly = false)
public class EscalaService implements EscalaInterface{
	
	@Autowired
	private EscalaDAO dao;

	@Override
	public void save(Escala escala) {
		dao.save(escala);
	}

	@Override
	public void update(Escala escala) {
		dao.updateEscala(escala);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Escala findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Escala> findAll() {
		return dao.findAll();
	}

	@Override
	public void atribuirEscala(EscalaColaborador escala) {
		dao.atribuirEscala(escala);
	}

	@Override
	public void removerColaborador(Long id) {
		dao.removerColaborador(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findByColaborador(Long id) {
		return dao.findByColaborador(id);
	}
}
