package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Escala;
import br.inatel.ssic.rsa.model.entity.EscalaColaborador;

public interface EscalaInterface {
	void save(Escala escala);

    void update(Escala escala);

    void delete(Long id);

    Escala findById(Long id);
    
    List<Escala> findAll();
    
    void atribuirEscala(EscalaColaborador escala);
    
    void removerColaborador(Long id);
    
    List<Object[]> findByColaborador(Long id);
}
