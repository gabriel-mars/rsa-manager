package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Item;

public interface RelatorioInterface {
	List<Object[]> findByMes(Item item);
	
	List<Object[]> findItensTrabalhadosMes(Item item);
	
	List<Object[]> findItensTotaisMes(Item item);
	
	List<Object[]> findByColaborador(Item item);
	
	List<Object[]> findAvg(Item item);
	
	List<Object[]> findStatusItemColab(Item item);
	
	List<Object[]> findStatusItemTime(Item item);
	
	List<Object[]> findItensByMes(Item item);
	
	List<Object[]> findByMaisRejeitados(Item item);
	
	List<Object[]> findByMaisAbonados(Item item);
}
