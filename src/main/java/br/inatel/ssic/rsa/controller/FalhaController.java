package br.inatel.ssic.rsa.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

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
	
	@PostMapping("/falha/individual")
	@ResponseBody
	public String getFalhasIndividuais(@RequestBody String ary, Falha falha, ModelMap model, HttpSession session) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String date, colab = null;
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		obj = jsonArray.optJSONObject(0);
		colab = obj.getString("Colab");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		
		falha.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		falha.setData(dtf.format(localDate));
		falha.setColaborador(colab);
		
		List<Object[]> dados = service.getFalhasColab(falha);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[1].toString(), aux[0]);
		}

		model.addAttribute("falha", falha);
		
		return auxJson.toString();
	}
	
	@PostMapping("/falha/individual/detalhe")
	@ResponseBody
	public String getFalhasIndividuaisDetail(@RequestBody String ary, Falha falha, ModelMap model, HttpSession session) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String colab = null;
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		obj = jsonArray.optJSONObject(0);
		colab = obj.getString("Colab");
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate localDate = LocalDate.now();
		
		falha.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		falha.setData(dtf.format(localDate));
		falha.setColaborador(colab);
		
		List<Object[]> dados = service.getFalhasColabDetail(falha);
		String data = new Gson().toJson(dados);	

		model.addAttribute("falha", falha);
		
		return data;
	}
	
	@PostMapping("/falha/mensal/time")
	@ResponseBody
	public String getFalhasTimeMes(@RequestBody String ary, Falha falha, ModelMap model, HttpSession session) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String date = null;
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		obj = jsonArray.optJSONObject(0);
		date = obj.getString("Data");
		
		falha.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		falha.setData(date);
		
		List<Object[]> dados = service.getFalhasTime(falha);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[1].toString(), aux[0]);
		}

		model.addAttribute("falha", falha);
		
		return auxJson.toString();
	}
	
	@PostMapping("/falha/mensal/time/detalhe")
	@ResponseBody
	public String getFalhasTimeDetailsMes(@RequestBody String ary, Falha falha, ModelMap model, HttpSession session) throws JSONException {
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String date = null;
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		obj = jsonArray.optJSONObject(0);
		date = obj.getString("Data");
		
		falha.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		falha.setData(date);
		
		List<Object[]> dados = service.getFalhasTimeDetail(falha);
		
		String data = new Gson().toJson(dados);	
		
		model.addAttribute("falha", falha);
	
		return data;
	}
}
