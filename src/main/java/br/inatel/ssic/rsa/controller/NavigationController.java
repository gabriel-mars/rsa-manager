package br.inatel.ssic.rsa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.entity.Pessoa;
import br.inatel.ssic.rsa.model.service.ColaboradorService;

@Controller
public class NavigationController {
	
	@Autowired
	private ColaboradorService service;

	@GetMapping("/")
	public String getHome(ModelMap model) {
		Pessoa pessoa = new Pessoa();
		model.addAttribute("pessoa", pessoa);
		
		return "index";
	}
	
	// Métodos Colaborador
	@GetMapping("/colaborador/cadastro")
	public String getCadastroColaborador(ModelMap model) {
		Colaborador colaborador = new Colaborador();
		
		model.addAttribute("colaborador", colaborador);
		return "colaborador/cadastro";
	}
	
	@GetMapping("/colaborador/editar/{id}")
	public String getEditarColaborador(@PathVariable("id") Long id, ModelMap model) {
		Colaborador colaborador = service.findById(id);
		
		model.addAttribute("colaborador", colaborador);
		
		return "colaborador/cadastro";
	}
	
	// Métodos Atividade
	@GetMapping("/atividade/cadastro")
	public String getCadastroAtividade() {
		return "atividade/cadastro";
	}
	
	@GetMapping("/atividade/diario")
	public String getAtividadeDiaria(ModelMap model) {
		Colaborador colaborador = new Colaborador();
		
		model.addAttribute("colaborador", colaborador);
		return "atividade/lista";
	}
	
	// Métodos Relatório
	@GetMapping("/relatorio/individual")
	public String getRelatorioIndividual(ModelMap model) {
		Item item = new Item();
		
		model.addAttribute("item", item);

		return "relatorio/individual";
	}
	
	@GetMapping("/relatorio/time")
	public String getRelatorioTime(ModelMap model) {
		
		return "relatorio/time";
	}
}
