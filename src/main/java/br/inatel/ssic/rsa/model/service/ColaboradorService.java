package br.inatel.ssic.rsa.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.inatel.ssic.rsa.model.base.ColaboradorInterface;
import br.inatel.ssic.rsa.model.dao.ColaboradorDAO;
import br.inatel.ssic.rsa.model.entity.Colaborador;
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
		dao.update(colaborador);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Colaborador findById(Long id) {
		return dao.findById(id);
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
			
        	return sessaoAtual;
        }
	}

}
