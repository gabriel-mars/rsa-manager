package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Ferias;

public interface FeriasInterface {
	void save(Ferias ferias);

    void update(Ferias ferias);

    void delete(Long id);

    Ferias findById(Long id);
    
    List<Ferias> findAll();
    
    List<Object[]> findFeriasByPeriodo(Ferias ferias);
}
