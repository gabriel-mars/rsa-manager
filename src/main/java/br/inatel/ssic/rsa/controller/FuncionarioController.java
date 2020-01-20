package br.inatel.ssic.rsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.service.ColaboradorService;

@Controller
public class FuncionarioController {
	@Autowired
	private ColaboradorService service;
	
	@PostMapping("/colaborador/salvar")
	public String salvarColaborador(Colaborador colaborador, RedirectAttributes attr) {
		service.save(colaborador);
		
		attr.addFlashAttribute("success", "Colaborador cadastrado.");
		return "redirect:/colaborador/cadastro";
	}
}
