package br.inatel.ssic.rsa.controller;

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
		Object[] aux = null;
		Integer numSup, numItens, media;
		
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		JSONObject auxJson = new JSONObject();
		
		obj = jsonArray.optJSONObject(0);
		org = obj.getString("Org");
		colab = obj.getString("Colab");
		date = obj.getString("Data");
		
		item.setCentroRsa(org);
		item.setInspetor(colab);
		item.setDataAnalise(date);
		
		List<Object[]> listMedia = service.findAvg(item);
		
		for(int i = 0; i < listMedia.size(); i++) {
			aux = listMedia.get(i);
			
			numSup = Integer.parseInt(aux[1].toString());
			numItens = Integer.parseInt(aux[2].toString());
			
			media = (int) (numItens / numSup);
			
			auxJson.put(aux[0].toString(), aux[1]);
		}
		
		return auxJson.toString();
	}
}