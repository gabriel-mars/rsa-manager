package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Escala;

public interface EscalaInterface {
	void save(Escala escala);

    void update(Escala escala);

    void delete(Long id);

    Escala findById(Long id);
    
    List<Escala> findAll();
}
