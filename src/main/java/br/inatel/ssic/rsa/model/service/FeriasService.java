package br.inatel.ssic.rsa.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findFeriasBySemana(Ferias ferias) {
		return dao.findFeriasBySemana(ferias);
	}

	@Override
	public List<String> findFeriasByNRO(Ferias ferias) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(ferias.getInicioFerias(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(ferias.getInicioFerias(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		ferias.setInicioFerias(dateInit.format(format));
		ferias.setFimFerias(dateFinish.format(format));
		
		return dao.findFeriasByNRO(ferias);
	}
}
