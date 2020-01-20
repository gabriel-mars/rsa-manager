package br.inatel.ssic.rsa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Pessoa;
import br.inatel.ssic.rsa.model.service.ColaboradorService;

@Controller
public class ColaboradorController {
	@Autowired
	private ColaboradorService service;
	
	@PostMapping("/colaborador/entrar")
	public String verificarLogin(Pessoa pessoa, HttpSession session, RedirectAttributes attr) {
		Colaborador sessaoAtual = new Colaborador();
		Pessoa aux = new Pessoa();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		try {
			if(sessaoAtual != null) {
				session.removeAttribute("colaboradorLogado");
			}
			
			Colaborador colaborador = service.verifyLogin(pessoa.getEmail(), pessoa.getSenha());
			
			session.setAttribute("colaboradorLogado", colaborador);
			return "dashboard";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Dados incorretos. Tente novamente.");
			return "redirect:/";
		}
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
}
