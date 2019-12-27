package br.inatel.ssic.rsa.model.base;

import br.inatel.ssic.rsa.model.entity.Colaborador;

public interface ColaboradorInterface {
	void save(Colaborador colaborador);

    void update(Colaborador colaborador);

    void delete(Long id);

    Colaborador findById(Long id);
}
