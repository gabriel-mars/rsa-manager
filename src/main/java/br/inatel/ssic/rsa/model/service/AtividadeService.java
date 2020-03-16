package br.inatel.ssic.rsa.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.AtividadeInterface;
import br.inatel.ssic.rsa.model.dao.AtividadeDAO;
import br.inatel.ssic.rsa.model.entity.Item;

@Service
@Transactional(readOnly = false)
public class AtividadeService implements AtividadeInterface{
	
	@Autowired
	private AtividadeDAO dao;

	@Async
	public void save(HSSFWorkbook workbook) throws IOException {
		List<Item> itens = new ArrayList<Item>();
	    Item item = new Item();

	    HSSFSheet worksheet = workbook.getSheetAt(0);
	    
	    try {
		    for(int i=1; i < worksheet.getPhysicalNumberOfRows(); i++) {
		    	HSSFRow row = worksheet.getRow(i);
		    	item = new Item();
		    	
		    	// Construção Item
		    	Integer aux = (int) Double.parseDouble(row.getCell(0).toString());
		    	String inspetor = row.getCell(15).toString();
		    
		    	item.setSite(row.getCell(5).toString());
		    	item.setOrdem(row.getCell(1).toString());
		    	item.setItem(row.getCell(2).toString());
		    	item.setDescricao(row.getCell(3).toString());
		    	item.setStatus(row.getCell(7).toString());
		    	item.setOfensor(row.getCell(9).toString());
		    	item.setCentroRsa(row.getCell(14).toString());
		    	
		    	if (inspetor.intern().equals(" ") || inspetor.intern().equals("")) {
					inspetor = "JOSÉ";
					item.setInspetor(inspetor);
				} else if(inspetor.intern().equals("ANA")) {
					inspetor = "ANA C.";
					item.setInspetor(inspetor);
				} else if(inspetor.intern().equals("Ana")) {
					inspetor = "ANA L.";
					item.setInspetor(inspetor);
				} else if(inspetor.intern().equals("GABRIEL")) {
					inspetor = "GABRIEL M.";
					item.setInspetor(inspetor);
				} else if(inspetor.intern().equals("Gabriel")) {
					inspetor = "GABRIEL J.";
					item.setInspetor(inspetor);
				} else {
					item.setInspetor(row.getCell(15).toString().toUpperCase());
				}
		    	
		    	// Split para separação de data e hora
		    	// Envio
		    	String conteudoEnvio = row.getCell(13).toString();	
		    	if (conteudoEnvio.intern().equals("-")) {
		    		item.setDataEnvio("-");
			    	item.setHoraEnvio("-");
		    	} else {
		    		conteudoEnvio = row.getCell(13).getDateCellValue().toLocaleString().toString();
		    		String arrayEnvio[] = new String[2];
			    	arrayEnvio = conteudoEnvio.split(" ");
			    	item.setDataEnvio(arrayEnvio[0]);
			    	item.setHoraEnvio(arrayEnvio[1]);
		    	}
		    	
		    	// Análise
		    	String conteudoAnalise = row.getCell(16).getDateCellValue().toLocaleString().toString();
		    	String[] arrayAnalise = new String[2];
		    	arrayAnalise = conteudoAnalise.split(" ");
		    	item.setDataAnalise(arrayAnalise[0]);
		    	item.setHoraAnalise(arrayAnalise[1]);

		    	itens.add(item);
		    }

		    dao.saveAll(itens);
		    System.out.println("Dados salvos!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Item> getAtividades(String data, String organizacao) {
		List<Item> atividades = dao.getAtividades(data, organizacao);
		
		return atividades;
	}

	@Override
	public List<Item> getAtividadesMensal(String dataInicial, String dataFinal, String organizacao) {
		List<Item> atividades = dao.getAtividadesMensal(dataInicial, dataFinal, organizacao);
		
		return atividades;
	}
}
