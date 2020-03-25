package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Item;

public interface AtividadeInterface {
	List<Item> getAtividades(String data, String organizacao);
	
	List<Item> getAtividadesMensal(String dataInicial, String dataFinal, String organizacao);
	
	List<Item> getAtividadesMoreMouths(String data, String organizacao, String last);
	
	List<Item> getAtividadesMinusMouths(String data, String organizacao, String last);
}
