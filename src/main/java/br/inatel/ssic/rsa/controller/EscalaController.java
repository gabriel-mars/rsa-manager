package br.inatel.ssic.rsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Escala;
import br.inatel.ssic.rsa.model.entity.EscalaColaborador;
import br.inatel.ssic.rsa.model.service.EscalaService;

@Controller
public class EscalaController {
	
	@Autowired
	private EscalaService service;
	
	@PostMapping("/escala/salvar")
	public String saveEscala(Escala escala, RedirectAttributes attr) {
		service.save(escala);
		
		attr.addFlashAttribute("success", "Escala cadastrada");
		return "redirect:/escala/cadastro";
	}
	
	@PostMapping("/escala/update")
	public String updateEscala(Escala escala, RedirectAttributes attr) {
		service.update(escala);
		
		attr.addFlashAttribute("success", "Escala atualizada");
		return "redirect:/escala/cadastro";
	}
	
	@PostMapping("/escala/colaborador/atribuir")
	public String atribuirEscala(EscalaColaborador escala, RedirectAttributes attr) {
		service.atribuirEscala(escala);
		
		attr.addFlashAttribute("success", "Escala atualizada");
		return "redirect:/escala/atribuir";
	}
	
	@GetMapping("/escala/remover/{id}")
	public String removerColaboradorEscala(@PathVariable("id") Long id, RedirectAttributes attr) {
		
		try {
			service.removerColaborador(id);
			attr.addFlashAttribute("success", "Colaborador removido.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível remover o colaborador solicitado.");
		}
		
		return "redirect:/escala/lista";
	}
}
