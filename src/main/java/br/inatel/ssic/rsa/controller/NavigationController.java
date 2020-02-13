package br.inatel.ssic.rsa.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Falha;
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
	@GetMapping("/colaborado/solicitar")
	public String getSolicitacaoCadastro(ModelMap model) {
		Colaborador colaborador = new Colaborador();
		model.addAttribute("colaborador", colaborador);
		
		return "colaborador/solicitar";
	}
	
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
	
	@GetMapping("/colaborador/senha")
	public String getEditarSenha(ModelMap model) {
		Pessoa pessoa = new Pessoa();
		
		model.addAttribute("pessoa", pessoa);
		
		return "colaborador/senha";
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
	
	@GetMapping("/atividade/mensal")
	public String getAtividadeMensal(ModelMap model) {
		Colaborador colaborador  = new Colaborador();
		
		model.addAttribute("colaborador", colaborador);
		return "atividade/mensal";
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
	
	@GetMapping("/relatorio/diario")
	public String getRelatorioDiario(ModelMap model) {
		return "relatorio/diario";
	}
	
	// Métodos Falha
	@GetMapping("/falha/cadastro")
	public String getCadastroFalha(ModelMap model, HttpSession session) {
		Falha falha = new Falha();
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		List<Item> colaboradores = service.findByAtividade(sessaoAtual.getOrganizacao().toUpperCase());
		
		model.addAttribute("falha", falha);
		model.addAttribute("colaboradores", colaboradores);
		
		return "falha/cadastro";
	}
	
	@GetMapping("/falha/individual")
	public String getFalhaIndividual(ModelMap model, HttpSession session) {
		Item item = new Item();
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		List<Item> colaboradores = service.findByAtividade(sessaoAtual.getOrganizacao().toUpperCase());
		
		model.addAttribute("item", item);
		model.addAttribute("colaboradores", colaboradores);

		return "falha/individual";
	}
	
	@GetMapping("/falha/time")
	public String getFalhaTime(ModelMap model) {
		Item item = new Item();
		
		model.addAttribute("item", item);

		return "falha/time";
	}
	
	// Métodos para análise de Itens
	@GetMapping("/relatorio/item/reprovado")
	public String getItensReprovados() {
		return "item/reprovado";
	}
	
	@GetMapping("/relatorio/item/abonado")
	public String getItensAbonados() {
		return "item/abonado";
	}
}
