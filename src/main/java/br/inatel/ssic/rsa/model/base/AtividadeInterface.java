package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Item;

public interface AtividadeInterface {
	List<Item> getAtividades(String data, String organizacao);
}
