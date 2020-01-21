package br.inatel.ssic.rsa.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.AtividadeInterface;
import br.inatel.ssic.rsa.model.entity.Atividade;
import br.inatel.ssic.rsa.model.entity.Item;

@Service
@Transactional(readOnly = false)
public class AtividadeService implements AtividadeInterface{

	@Async
	public void save(HSSFWorkbook workbook) throws IOException {
		List<Atividade> atividades = new ArrayList<Atividade>();
		List<Item> itens = new ArrayList<Item>();
		Atividade atividade = new Atividade();
	    Item item = new Item();

	    HSSFSheet worksheet = workbook.getSheetAt(0);
	    
	    try {
		    for(int i=1; i < worksheet.getPhysicalNumberOfRows(); i++) {
		    	HSSFRow row = worksheet.getRow(i);
		    	atividade = new Atividade();
		    	item = new Item();
		    	
		    	// Contrução Atividade
		    	Integer aux = (int) Double.parseDouble(row.getCell(0).toString());
		    	atividade.setOs(aux);
		    	atividade.setSite(row.getCell(5).toString());
		    	atividade.setEmpresa(row.getCell(11).toString());
		    	
		    	// Construção Item
		    	item.setOrdem(row.getCell(1).toString());
		    	item.setItem(row.getCell(2).toString());
		    	item.setDescricao(row.getCell(3).toString());
		    	item.setStatus(row.getCell(7).toString());
		    	item.setOfensor(row.getCell(9).toString());
		    	item.setAsp(row.getCell(12).toString());
		    	item.setCentroRsa(row.getCell(14).toString());
		    	
		    	// Split para separação de data e hora
		    	// Envio
		    	String conteudoEnvio = row.getCell(13).toString();
		    	conteudoEnvio.replace(" ", "|");
		    	String[] arrayEnvio = new String[2];
		    	arrayEnvio = conteudoEnvio.split("|");
		    	System.out.println(arrayEnvio[1]);
//		    	item.setDataEnvio(arrayEnvio[0]);
//		    	item.setHoraEnvio(arrayEnvio[1]);
		    	
		    	// Análise
		    	String conteudoAnalise = row.getCell(16).toString();
		    	String arrayAnalise[] = new String[2];
		    	arrayAnalise = conteudoAnalise.split(" ");
//		    	item.setDataAnalise(arrayAnalise[0]);
//		    	item.setHoraAnalise(arrayAnalise[1]);
		    	
		    	atividades.add(atividade);
		    	itens.add(item);
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
