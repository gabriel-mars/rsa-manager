package br.inatel.ssic.rsa.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

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

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getFalhasColab(Falha falha) {
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(falha.getData(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(falha.getData(), format) // Data final de busca
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		falha.setData(dateInit.format(format));
		falha.setFalhaPrimaria(dateFinish.format(format)); // Data final de busca
		
		return dao.getFalhasColab(falha);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getFalhasColabDetail(Falha falha) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(falha.getData(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(falha.getData(), format) // Data final de busca
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		falha.setData(dateInit.format(format));
		falha.setFalhaPrimaria(dateFinish.format(format)); // Data final de busca
		
		return dao.getFalhasColabDetail(falha);
	}
}
