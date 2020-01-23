package br.inatel.ssic.rsa.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
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
}
