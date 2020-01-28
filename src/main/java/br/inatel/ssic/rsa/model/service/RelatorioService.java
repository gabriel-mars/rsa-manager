package br.inatel.ssic.rsa.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.RelatorioInterface;
import br.inatel.ssic.rsa.model.dao.RelatorioDAO;
import br.inatel.ssic.rsa.model.entity.Item;

@Service
@Transactional(readOnly = false)
public class RelatorioService implements RelatorioInterface{
	
	@Autowired
	private RelatorioDAO dao;

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findByColaborador(Item item) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		return dao.findByColaborador(item);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAvg(Item item) {
		List<Object[]> listMedia = new ArrayList<Object[]>();
		Object[] aux = null;
		Integer totalItens, numSup, media;
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		List<Object[]> listAux = dao.findAvg(item);
		
		for (int i = 0; i < listAux.size(); i++) {
			aux = listAux.get(i);
			
			totalItens = Integer.parseInt(aux[2].toString());
			numSup = Integer.parseInt(aux[1].toString());
			media = (int) (totalItens / numSup);
			
			aux[1] = media;
			listMedia.add(aux);
		}
		
		return listMedia;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findStatusItemColab(Item item) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		return dao.findStatusItemColab(item);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findStatusItemTime(Item item) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		return dao.findStatusItemTime(item);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findByMes(Item item) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		return dao.findByMes(item);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findItensTrabalhadosMes(Item item) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		return dao.findItensTrabalhadosMes(item);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findItensTotaisMes(Item item) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(item.getDataAnalise(), format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		item.setDataAnalise(dateInit.format(format));
		item.setDataEnvio(dateFinish.format(format));
		
		return dao.findItensTotaisMes(item);
	}
}
