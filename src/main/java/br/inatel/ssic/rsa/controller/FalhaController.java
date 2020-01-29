package br.inatel.ssic.rsa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Falha;
import br.inatel.ssic.rsa.model.service.FalhaService;

@Controller
public class FalhaController {

	@Autowired
	private FalhaService service;
	
	@PostMapping("/falha/salvar")
	public String salvarColaborador(Falha falha, RedirectAttributes attr, HttpSession session) {
		Colaborador sessaoAtual = new Colaborador();
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		try {
			falha.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
			service.save(falha);
			
			attr.addFlashAttribute("success", "Falha cadastrada.");
			return "redirect:/falha/cadastro";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Oops! Tente novamente.");
			return "redirect:/falha/cadastro";
		}
	}
}
