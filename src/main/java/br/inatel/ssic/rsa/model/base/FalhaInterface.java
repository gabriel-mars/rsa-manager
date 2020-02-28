package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Falha;

public interface FalhaInterface {
	void save(Falha falha);
	
	List<Object[]> getFalhasColab(Falha falha);
	
	List<Object[]> getFalhasTime(Falha falha);
	
	List<Object[]> getFalhasColabDetail(Falha falha);
	
	List<Object[]> getFalhasTimeDetail(Falha falha);
	
	List<Object[]> getFalhasQuantitativo(Falha falha);
	
	List<Object[]> getTotalReprovacoes(Falha falha);
}
