package br.inatel.ssic.rsa.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.service.AtividadeService;
import br.inatel.ssic.rsa.model.service.RelatorioService;

@Controller
public class RelatorioController {
	
	@Autowired
	private RelatorioService service;
	
	@Autowired
	private AtividadeService atvService;

	@PostMapping("/relatorio/colaborador")
	@ResponseBody
	public String getRelatorioIndividual(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, colab, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		colab = obj.getString("Colab");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setInspetor(colab);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findByColaborador(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString(), aux[1]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/media/dia")
	@ResponseBody
	public String getMediaDiaria(@RequestBody String ary) throws JSONException {
		Item item = new Item();
		String org, colab, date = null;
		
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		colab = obj.getString("Colab");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setInspetor(colab);
		item.setDataAnalise(date);
		
		List<Object[]> listMedia = service.findAvg(item);
		
		String data = new Gson().toJson(listMedia);	
		
		return data;
	}
	
	@PostMapping("/relatorio/itens/colaborador")
	@ResponseBody
	public String getItensIndividual(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, colab, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		colab = obj.getString("Colab");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setInspetor(colab);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findStatusItemColab(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString() + ':' + aux[1].toString(), aux[2]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/itens/time")
	@ResponseBody
	public String getItensTime(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, colab, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		colab = obj.getString("Colab");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setInspetor(colab);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findStatusItemTime(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);

			auxJson.put(aux[0].toString() + ':' + aux[1].toString(), aux[2]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/time")
	@ResponseBody
	public String getRelatorioTime(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findByMes(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString() + ':' + aux[1].toString(), aux[2]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/itens/trabalhado")
	@ResponseBody
	public String getItensTrabalhadosMes(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findItensTrabalhadosMes(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString(), aux[1]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/itens/total")
	@ResponseBody
	public String getItensTotaisMes(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findItensTotaisMes(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString(), aux[1]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/itens/mensal")
	@ResponseBody
	public String getItensMes(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findItensByMes(item);
		
		String data = new Gson().toJson(dados);
		
		return data;
	}
	
	@PostMapping("/relatorio/diario")
	@ResponseBody
	public String getRelatorioDiario(@RequestBody String ary) throws JSONException {
		List<Item> dados = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		if (!org.intern().equals("INATEL")) {
			dados = atvService.getAtividades(date, org);
		} else {
			LocalDate lastMouth = LocalDate.now().minusMonths(6);
			String formattedDate = lastMouth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			dados = atvService.getAtividadesMoreMouths(date, org, formattedDate);
		}
		
		String data = new Gson().toJson(dados);	
		
		return data;
	}
	
	@GetMapping("/relatorio/dashboard/reprovado")
	@ResponseBody
	public String getItensReprovados(HttpSession session, Item item, ModelMap model) {
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		item.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		
		List<Object[]> dados = service.findByMaisRejeitados(item);
		String data = new Gson().toJson(dados);	 
		
		return data;
	}
	
	@GetMapping("/relatorio/dashboard/abonado")
	@ResponseBody
	public String getItensAbonados(HttpSession session, Item item, ModelMap model) {
		Colaborador sessaoAtual = new Colaborador();
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		item.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		
		List<Object[]> dados = service.findByMaisAbonados(item);
		String data = new Gson().toJson(dados);	 
		
		return data;
	}
	
	@PostMapping("/relatorio/time/reprovado")
	@ResponseBody
	public String getRelatorioRejeitados(@RequestBody String ary, HttpSession session) throws JSONException {
		Colaborador sessaoAtual = new Colaborador();
		Item item = new Item();
		
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String date = null;
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
	
		obj = jsonArray.optJSONObject(0);
		date = obj.getString("Data");
		item.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findByMaisRejeitados(item);
		
		String data = new Gson().toJson(dados);	
		
		return data;
	}
	
	@PostMapping("/relatorio/time/abonado")
	@ResponseBody
	public String getRelatorioAbonados(@RequestBody String ary, HttpSession session) throws JSONException {
		Colaborador sessaoAtual = new Colaborador();
		Item item = new Item();
		
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String date = null;
		
		sessaoAtual = (Colaborador) session.getAttribute("colaboradorLogado");
		
		obj = jsonArray.optJSONObject(0);
		date = obj.getString("Data");
		item.setCentroRsa(sessaoAtual.getOrganizacao().toUpperCase());
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findByMaisAbonados(item);
		
		String data = new Gson().toJson(dados);	
		
		return data;
	}
	
	@PostMapping("/relatorio/diario/trabalhado")
	@ResponseBody
	public String getItensTrabalhadosDario(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findItensTrabalhadosDiario(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString(), aux[1]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/diario/total")
	@ResponseBody
	public String getItensTotaisDiario(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findItensTotaisDiario(item);
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			
			auxJson.put(aux[0].toString(), aux[1]);
		}

		model.addAttribute("item", item);
		
		return auxJson.toString();
	}
	
	@PostMapping("/relatorio/diario/sites")
	@ResponseBody
	public String getSitesDiario(@RequestBody String ary, Item item, HttpSession session) throws JSONException {
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String org, date = null;
		Map<String, List<Object[]>> dados = new HashMap<String, List<Object[]>>();
		String colaborador = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setDataAnalise(date);
		
		List<String> colaboradores = service.findColaboradoresDiario(item);
		
		for (int i = 0; i < colaboradores.size(); i++) {
			colaborador = colaboradores.get(i);
			item.setInspetor(colaborador);
			
			List<Object[]> sitesColab = service.findItensSiteDiario(item);
			
			dados.put(colaborador, sitesColab);
		}
		
		String data = new Gson().toJson(dados);	
		
		return data;
	}
	
	@PostMapping("/relatorio/diario/menos")
	@ResponseBody
	public String getRelatorioDiarioMenos(@RequestBody String ary) throws JSONException {
		List<Item> dados = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String org, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		date = obj.getString("Data");
		
		if (!org.intern().equals("INATEL")) {
			dados = atvService.getAtividades(date, org);
		} else {
			LocalDate lastMouth = LocalDate.now().minusMonths(6);
			String formattedDate = lastMouth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			dados = atvService.getAtividadesMinusMouths(date, org, formattedDate);
		}
		
		String data = new Gson().toJson(dados);	
		
		return data;
	}
}
