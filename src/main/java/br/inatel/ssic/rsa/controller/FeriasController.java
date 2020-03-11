package br.inatel.ssic.rsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Ferias;
import br.inatel.ssic.rsa.model.service.FeriasService;

@Controller
public class FeriasController {

	@Autowired
	private FeriasService service;
	
	@PostMapping("/ferias/atribuir")
	public String saveEscala(Ferias ferias, RedirectAttributes attr) {
		service.save(ferias);
		
		attr.addFlashAttribute("success", "Férias cadastrada");
		return "redirect:/ferias/cadastro";
	}
}
