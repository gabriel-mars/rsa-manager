package br.inatel.ssic.rsa.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.inatel.ssic.rsa.model.entity.Ferias;
import br.inatel.ssic.rsa.model.service.EscalaService;
import br.inatel.ssic.rsa.model.service.FeriasService;

@Controller
public class FeriasController {

	@Autowired
	private FeriasService service;
	
	@Autowired
	private EscalaService escalaService;
	
	@PostMapping("/ferias/atribuir")
	public String saveEscala(Ferias ferias, RedirectAttributes attr) {
		String aux = "";
		String inicioEscala = "";
		Object[] auxEscala = null;
		
		List<Object[]> escala = escalaService.findByColaborador(ferias.getColaboradorId());
		auxEscala = escala.get(0);
		inicioEscala = auxEscala[0].toString();
		
		String dia = LocalDate.parse(                               
	             ferias.getInicioFerias() ,
	             DateTimeFormatter.ofPattern( "dd/MM/yyyy" ))                                    
	         .getDayOfWeek()                       
	         .getDisplayName( TextStyle.FULL, Locale.US ).toString();
		
		switch (dia) {
			case "Monday":
				aux = "Segunda-feira";
				break;
				
			case "Tuesday":
				aux = "Terça-feira";
				break;
				
			case "Wednesday":
				aux = "Quarta-feira";
				break;
				
			case "Thursday":
				aux = "Quinta-feira";
				break;
				
			case "Friday":
				aux = "Sexta-feira";
				break;
				
			case "Saturday":
				aux = "Sábado";
				break;
				
			case "Sunday":
				aux = "Domingo";
				break;
	
			default:
				break;
		}
		
		if ( aux.intern().equals("Domingo") || aux.intern().equals("Sábado") ) {
			
			attr.addFlashAttribute("fail", "Não é permitido o inicio das férias em finais de semana. Confira os dados e tente novamente");
			return "redirect:/ferias/cadastro";
		} else if ( aux.intern().equals("Segunda-feira") && inicioEscala.intern().equals("Terça-feira") ) {
			
			attr.addFlashAttribute("fail", "Não é permitido o início das férias em dias fora da escala. Confira os dados e tente novamente.");
			return "redirect:/ferias/cadastro";
		} else {
			service.save(ferias);
			
			attr.addFlashAttribute("success", "Férias cadastrada");
			return "redirect:/ferias/cadastro";
		}
	}
}