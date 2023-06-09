package com.algaworks.algamoney_api.service;

import com.algaworks.algamoney_api.repository.LancamentoRepository;
import com.algaworks.algamoney_api.service.exception.PessoaInexistenteOuInativaException;
import com.algaworks.algamoney_api.model.Lancamento;
import com.algaworks.algamoney_api.model.Pessoa;
import com.algaworks.algamoney_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        //o método isPresent() do Optional não funcionou.
        //!pessoa.get().getAtivo() // isPresent() // !isEmpty()
        if(pessoa == null || !pessoa.get().getAtivo()){
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }
}
