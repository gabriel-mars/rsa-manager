package br.inatel.ssic.rsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Escala;
import br.inatel.ssic.rsa.model.entity.EscalaColaborador;
import br.inatel.ssic.rsa.model.entity.Falha;
import br.inatel.ssic.rsa.model.entity.Ferias;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.entity.Pessoa;
import br.inatel.ssic.rsa.model.service.ColaboradorService;
import br.inatel.ssic.rsa.model.service.EscalaService;
import br.inatel.ssic.rsa.model.service.FeriasService;

@Controller
public class NavigationController {
	
	@Autowired
	private ColaboradorService service;
	
	@Autowired
	private EscalaService escalaService;
	
	@Autowired
	private FeriasService feriasService;
	
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
	
	@GetMapping("/falha/semanal/individual")
	public String getFalhaColabSemanal() {
		
		return "falha/semanal";
	}
	
	@GetMapping("/falha/quantitativo")
	public String getFalhaQuantitativo(ModelMap model) {
		Falha falha = new Falha();
		List<Object[]> falhas = new ArrayList<Object[]>();
		
		model.addAttribute("falha", falha);
		model.addAttribute("falhas", falhas);
		return "falha/quantitativo";
	}
	
	@GetMapping("/falha/qualitativo")
	public String getFalhaQualitativo(ModelMap model) {
		Falha falha = new Falha();
		
		model.addAttribute("falha", falha);
		return "falha/qualitativo";
	}
	
	@GetMapping("/falha/melhoria")
	public String getFalhaMelhoria() {
		return "falha/melhoria";
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
	
	// Métodos de escala
	@GetMapping("/escala/cadastro")
	public String novaEscala(ModelMap model) {
		Escala escala = new Escala();
		
		model.addAttribute("escala", escala);
		return "escala/cadastro";
	}
	
	@GetMapping("/escala/lista")
	public String listEscalas(ModelMap model) {
		List<Escala> escalas = escalaService.findAll();		
		
		model.addAttribute("escalas", escalas);
		return "escala/lista";
	}
	
	@GetMapping("/escala/editar/{id}")
	public String getEditarEscala(@PathVariable("id") Long id, ModelMap model) {
		Escala escala = escalaService.findById(id);
		
		model.addAttribute("escala", escala);
		return "escala/cadastro";
	}
	
	@GetMapping("/escala/atribuir")
	public String getAtribuirEscala(ModelMap model, HttpSession session) {
		EscalaColaborador escala = new EscalaColaborador();
		Colaborador sessaoAtual = new Colaborador();
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		List<Escala> escalas = escalaService.findAll();
		List<Object[]> colaboradores = service.findByColaboradoresSemEscala(sessaoAtual.getOrganizacao());
		
		model.addAttribute("escala", escala);
		model.addAttribute("colaboradores", colaboradores);
		model.addAttribute("escalas", escalas);
		return "escala/atribuir";
	}
	
	@GetMapping("/escala/colaboradores/{id}")
	public String colaboradoresEscala(@PathVariable("id") Long id, ModelMap model) {
		Escala escala = escalaService.findById(id);
		List<Object[]> colaboradores = service.findByColaboradoresEscala(id);
		
		model.addAttribute("escala", escala);
		model.addAttribute("colaboradores", colaboradores);
		return "escala/colaboradores";
	}
	
	// Métodos de Férias
	@GetMapping("/ferias/cadastro")
	public String cadastroFerias(ModelMap model, HttpSession session) {
		Ferias ferias = new Ferias();
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		List<Pessoa> colaboradores = service.findByOrganizacao(sessaoAtual.getOrganizacao());
		
		model.addAttribute("ferias", ferias);
		model.addAttribute("colaboradores", colaboradores);
		return "ferias/cadastro";
	}
	
	@GetMapping("/ferias/lista")
	public String listaFerias(ModelMap model) {
		Ferias ferias = new Ferias();
		
		model.addAttribute("ferias", ferias);
		return "ferias/lista";
	}
	
	@GetMapping("/ferias/editar/{id}")
	public String getEditarFerias(@PathVariable("id") Long id, ModelMap model) {
		Ferias ferias = feriasService.findById(id);
		
		model.addAttribute("ferias", ferias);
		return "escala/cadastro";
	}
}
