package br.inatel.ssic.rsa.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.service.AtividadeService;

@Controller
public class AtividadeController {
	
	@Autowired
	private AtividadeService service;
	
	@PostMapping("/atividade/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attr) {
	    
	    try {
	    	HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
		    service.save(workbook);
		    attr.addFlashAttribute("success","Arquivo carregado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Oops! Tente novamente.");
		}
	  
	    return "redirect:/atividade/cadastro";
	}
}
