package com.algaworks.algamoney_api.service;

import com.algaworks.algamoney_api.model.Pessoa;
import com.algaworks.algamoney_api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Integer codigo, Pessoa pessoa){
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

        // o metodo abaixo copia os dados de pessoa salva em pessoaSalva, menos o codigo
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Integer codigo, Boolean ativo) {
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
        pessoaSalva.setAtivo(ativo);
        pessoaRepository.save(pessoaSalva);

    }

    private Pessoa buscarPessoaPeloCodigo(Integer codigo) {
        Pessoa pessoaSalva = pessoaRepository.findById(codigo)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return pessoaSalva;
    }
}
