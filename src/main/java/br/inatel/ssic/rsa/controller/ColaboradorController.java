package br.inatel.ssic.rsa.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.entity.Pessoa;
import br.inatel.ssic.rsa.model.service.AtividadeService;
import br.inatel.ssic.rsa.model.service.ColaboradorService;
import br.inatel.ssic.rsa.model.service.RelatorioService;

@Controller
public class ColaboradorController {
	
	@Autowired
	private ColaboradorService service;
	
	@Autowired
	private RelatorioService relService;
	
	@Autowired
	private AtividadeService atvService;
	
	@PostMapping("/colaborador/entrar")
	public String verificarLogin(Pessoa pessoa, HttpSession session, RedirectAttributes attr) {
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		try {
			if(sessaoAtual != null) {
				session.removeAttribute("colaboradorLogado");
			}
			
			Colaborador colaborador = service.verifyLogin(pessoa.getEmail(), pessoa.getSenha());
			
			session.setAttribute("colaboradorLogado", colaborador);
			return "redirect:/dashboard";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Dados incorretos. Tente novamente.");
			return "redirect:/";
		}
	}
	
	@GetMapping("/dashboard")
	public String getDashboard(HttpSession session, ModelMap model) {
		Object[] aux = null;
		Object[] auxItem = null;
		Colaborador sessaoAtual = new Colaborador();
		Item item = new Item();
		Item aux2 = new Item();
		Item aux3 = new Item();
		int media = 0;
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate lastMouth = LocalDate.now().withDayOfMonth(1).minusDays(1);
		LocalDate localDate = LocalDate.now();
		
		item.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		item.setDataAnalise(dtf.format(localDate));
		
		List<Object[]> dadosMedia = relService.findItensByMes(item);
		aux = dadosMedia.get(0);
		
		aux2.setItem(aux[1].toString()); // Máximo do mês
		
		for(int i = 0; i < dadosMedia.size(); i++) {
			aux = dadosMedia.get(i);
			
			media += Integer.parseInt(aux[1].toString());
		}
		
		media = media / dadosMedia.size();
		aux2.setAsp("" + media); // Média do mês
		
		item.setDataAnalise(dtf.format(lastMouth));
		List<Object[]> dadosLastMouth = relService.findItensByMes(item);
		auxItem = dadosLastMouth.get(0);
		aux3.setItem(auxItem[1].toString()); // Máximo do mês
		
		media = 0;
		
		for(int i = 0; i < dadosLastMouth.size(); i++) {
			auxItem = dadosLastMouth.get(i);
			
			media += Integer.parseInt(auxItem[1].toString());
		}
		
		media = media / dadosLastMouth.size();
		
		aux3.setAsp("" + media); // Média do mês
		
		model.addAttribute("item", aux2);
		model.addAttribute("item2", aux3);
		return "fragments/main";
	}
	
	@PostMapping("/colaborador/sair")
	public String logoutColaborador(HttpSession session, RedirectAttributes attr) {
		session.invalidate();
        
        attr.addFlashAttribute("success", "Até logo!");
		return "redirect:/";
	}
	
	@PostMapping("/colaborador/salvar")
	public String salvarColaborador(Colaborador colaborador, RedirectAttributes attr) {
		service.save(colaborador);
		
		attr.addFlashAttribute("success", "Colaborador cadastrado.");
		return "redirect:/colaborador/cadastro";
	}
	
	@RequestMapping(value = "/colaborador/listar", method = RequestMethod.GET)
	public String listarColaboradores(ModelMap model, HttpSession session) {
		List<Pessoa> colaboradores = new ArrayList<Pessoa>(); 
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		colaboradores = service.findByOrganizacao(sessaoAtual.getOrganizacao());
		
		model.addAttribute("colaboradores", colaboradores);
		
		return "colaborador/lista";
	}
	
	@PostMapping("/colaborador/update")
	public String editarColaborador(Colaborador colaborador, RedirectAttributes attr) {
		service.update(colaborador);
		
		attr.addFlashAttribute("success", "Colaborador atualizado.");
		return "redirect:/colaborador/cadastro";
	}
}
