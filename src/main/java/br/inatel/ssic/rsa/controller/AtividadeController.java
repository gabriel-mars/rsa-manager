package br.inatel.ssic.rsa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Atividade;
import br.inatel.ssic.rsa.model.service.AtividadeService;

@Controller
public class AtividadeController {
	
	@Autowired
	private AtividadeService service;
	
	private String fileLocation;
	
	@PostMapping("/atividade/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, ModelMap model, RedirectAttributes attr) throws IOException {
	    Atividade atividade = new Atividade();
		
//		InputStream in = file.getInputStream();
//	    File currDir = new File(".");
//	    String path = currDir.getAbsolutePath();
//	    fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
//	    FileOutputStream f = new FileOutputStream(fileLocation);
//	    int ch = 0;
//	    while ((ch = in.read()) != -1) {
//	        f.write(ch);
//	    }
//	    
//	    f.flush();
//	    f.close();
	    
	    // Nova implementação
	    HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
	    HSSFSheet worksheet = workbook.getSheetAt(0);
	    int cont = 0;
	    try {
		    for(int i=1; i < worksheet.getPhysicalNumberOfRows(); i++) {
		        //HSSFRow row = worksheet.getRow(i);
		        cont = cont + 1;
		    }
		    
		    HSSFRow row = worksheet.getRow(2);
		    System.out.println(row.getCell(0).toString());
		    service.save(atividade);
		    attr.addFlashAttribute("success","Arquivo carregado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Oops! Tente novamente.");
		}
	    
	    
	    
	    return "redirect:/atividade/cadastro";
	}
}
