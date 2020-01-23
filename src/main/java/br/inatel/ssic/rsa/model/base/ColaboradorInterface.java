package br.inatel.ssic.rsa.model.base;

import java.util.List;

import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.entity.Pessoa;

public interface ColaboradorInterface {
	void save(Colaborador colaborador);

    void update(Colaborador colaborador);

    void delete(Long id);

    Colaborador findById(Long id);
    
    List<Pessoa> findByOrganizacao(String org);
    
    List<Item> findByAtividade(String org);
}
