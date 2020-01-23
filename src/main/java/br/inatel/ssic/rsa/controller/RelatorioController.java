package br.inatel.ssic.rsa.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.service.RelatorioService;

@Controller
public class RelatorioController {
	
	@Autowired
	private RelatorioService service;

	@PostMapping("/relatorio/colaborador")
	@ResponseBody
	public String getRelatorioIndividual(@RequestBody String ary, Item item, ModelMap model) throws JSONException {
		Object[] aux = null;
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		String org, colab, date = null;
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		colab = obj.getString("Colab");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setInspetor(colab);
		item.setDataAnalise(date);
		
		List<Object[]> dados = service.findByColaborador(item);
	
		JsonArray jsonDays = new JsonArray();
		JsonArray jsonItensEsforco = new JsonArray();
		JsonObject json = new JsonObject();
		
		for(int i = 0; i < dados.size(); i++) {
			aux = dados.get(i);
			jsonDays.add(aux[0].toString());
			jsonItensEsforco.add(aux[1].toString());
		}
		
		json.add("label", jsonDays);
		json.add("x", jsonItensEsforco);
		
		model.addAttribute("item", item);
		
		return json.toString();
	}
}
