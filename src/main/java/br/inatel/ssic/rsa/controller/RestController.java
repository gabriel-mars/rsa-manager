package br.inatel.ssic.rsa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.service.ColaboradorService;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private ColaboradorService colabService;
	
	@RequestMapping("/colaborador/inatel")
	public List<Item> getColaboradoresInatel(){
		List<Item> colaboradores = new ArrayList<>();

		colaboradores = colabService.findByAtividade("INATEL");
		
		return colaboradores;
	}
	
	@RequestMapping("/colaborador/fitec")
	public List<Item> getColaboradoresFitec(){
		List<Item> colaboradores = new ArrayList<>();

		colaboradores = colabService.findByAtividade("FITEC");
		
		return colaboradores;
	}
	
	@RequestMapping("/colaborador/ericsson")
	public List<Item> getColaboradoresEricsson(){
		List<Item> colaboradores = new ArrayList<>();

		colaboradores = colabService.findByAtividade("ERICSSON");
		
		return colaboradores;
	}
}
