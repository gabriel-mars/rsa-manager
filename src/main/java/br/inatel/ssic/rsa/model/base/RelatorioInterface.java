package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Item;

public interface RelatorioInterface {
	List<Object[]> findByColaborador(Item item);
	
	List<Object[]> findAvg(Item item);
}
