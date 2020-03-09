package br.inatel.ssic.rsa.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.ColaboradorInterface;
import br.inatel.ssic.rsa.model.dao.ColaboradorDAO;
import br.inatel.ssic.rsa.model.entity.Colaborador;
import br.inatel.ssic.rsa.model.entity.Item;
import br.inatel.ssic.rsa.model.entity.Pessoa;

@Service
@Transactional(readOnly = false)
public class ColaboradorService implements ColaboradorInterface{

	@Autowired
	private ColaboradorDAO dao;
	
	@Override
	public void save(Colaborador colaborador) {
		dao.save(colaborador);
	}

	@Override
	public void update(Colaborador colaborador) {
		dao.updateColaborador(colaborador);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Colaborador findById(Long id) {
		Colaborador colaborador = new Colaborador();
		Object[] aux = null;
		List<Object[]> pessoa = dao.findByUpdate(id);
		
		aux = pessoa.get(0);
		
		colaborador.setId(Long.parseLong(aux[0].toString()));
		colaborador.setNome(aux[1].toString());
		colaborador.setEmail(aux[2].toString());
		colaborador.setCpf(aux[3].toString());
		colaborador.setTelefone(aux[4].toString());
		colaborador.setIdEricsson(aux[5].toString());
		colaborador.setDataInicioRSA(aux[6].toString());
		colaborador.setOrganizacao(aux[7].toString());
		
		return colaborador;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Pessoa> findByOrganizacao(String org) {
		return dao.findByOrganizacao(org);
	}

	@Transactional(readOnly = true)
	public Colaborador verifyLogin(String email, String senha) throws Exception{
        List<Object[]> pessoaLogin = dao.verifyLogin(email, senha);;
        List<Object[]> colaboradorLogin = new ArrayList<Object[]>();
        Object[] auxPessoa = null;
        Object[] auxColab = null;
        Colaborador sessaoAtual = new Colaborador();

        if(pessoaLogin.get(0) == null) {
        	throw new Exception("Escola não cadastrada.");
        } else {
        	auxPessoa = pessoaLogin.get(0);
			
        	colaboradorLogin = dao.findByLogin(Long.parseLong(auxPessoa[0].toString()));
        	auxColab = colaboradorLogin.get(0);
        	
        	sessaoAtual.setId(Long.parseLong(auxPessoa[0].toString()));
			sessaoAtual.setNome(auxPessoa[1].toString());
			sessaoAtual.setEmail(auxPessoa[2].toString());
			sessaoAtual.setTelefone(auxPessoa[3].toString());
			sessaoAtual.setCpf(auxPessoa[4].toString());
			sessaoAtual.setIdEricsson(auxColab[0].toString());
			sessaoAtual.setOrganizacao(auxColab[1].toString());
			sessaoAtual.setSenha(auxPessoa[5].toString());
			
        	return sessaoAtual;
        }
	}

	@Override
	@Transactional(readOnly = true)
	public List<Item> findByAtividade(String org) {
		return dao.findByAtividade(org);
	}

	@Override
	public void updateSenha(Colaborador colaborador) {
		dao.updateSenha(colaborador);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findByColaboradoresSemEscala(String org) {
		return dao.findByColaboradoresSemEscala(org);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findByColaboradoresEscala(Long id) {
		return dao.findByColaboradoresEscala(id);
	}
}
