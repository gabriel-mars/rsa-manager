package br.inatel.ssic.rsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Pessoa;

@Controller
public class NavigationController {
	
	@GetMapping("/")
	public String getHome(ModelMap model) {
		Pessoa pessoa = new Pessoa();
		model.addAttribute("pessoa", pessoa);
		
		return "index";
	}
	
	@GetMapping("/colaborador/cadastro")
	public String getCadastroColaborador(ModelMap model) {
		Colaborador colaborador = new Colaborador();
		
		model.addAttribute("colaborador", colaborador);
		return "colaborador/cadastro";
	}
}
