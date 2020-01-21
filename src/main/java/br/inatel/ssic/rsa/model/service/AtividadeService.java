package br.inatel.ssic.rsa.model.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.AtividadeInterface;
import br.inatel.ssic.rsa.model.entity.Atividade;

@Service
@Transactional(readOnly = false)
public class AtividadeService implements AtividadeInterface{

	@Override
	@Async
	public void save(Atividade atividade) {
		try {
			Thread.sleep(4000);
			System.out.println("Deu certo!");
		} catch (InterruptedException e) {
			System.out.println("Não deu certo!");
		}
	}
}
