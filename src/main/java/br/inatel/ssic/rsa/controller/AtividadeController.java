package br.inatel.ssic.rsa.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Item;
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
	
	@PostMapping("/atividade/relatorio")
	public String relatorioPorDia(Colaborador colaborador, ModelMap model) {
		String organizacao = colaborador.getOrganizacao();
		String data = colaborador.getDataInicioRSA();
		
		List<Item> atividades = service.getAtividades(data, organizacao);
		
		model.addAttribute("atividades", atividades);
		model.addAttribute("colaborador", colaborador);
		
		return "atividade/lista";
	}
	
	@PostMapping("/atividade/mensal")
	public String relatorioPorMes(Colaborador colaborador, ModelMap model) {
		String organizacao = colaborador.getOrganizacao();
		String data = colaborador.getDataInicioRSA();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateInit = LocalDate.parse(data, format)
	            .with(TemporalAdjusters.firstDayOfMonth());
		
		LocalDate dateFinish = LocalDate.parse(data, format)
	            .with(TemporalAdjusters.lastDayOfMonth());
		
		List<Item> atividades = service.getAtividadesMensal(dateInit.format(format), dateFinish.format(format), organizacao);
		
		model.addAttribute("atividades", atividades);
		model.addAttribute("colaborador", colaborador);

		return "atividade/mensal";
	}
}
