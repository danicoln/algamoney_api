package com.algaworks.algamoney_api.service;

import com.algaworks.algamoney_api.domain.model.Categoria;
import com.algaworks.algamoney_api.repository.CategoriaRepository;
import com.algaworks.algamoney_api.repository.LancamentoRepository;
import com.algaworks.algamoney_api.resource.LancamentoResource;
import com.algaworks.algamoney_api.service.exception.CategoriaInexistenteOuInativaException;
import com.algaworks.algamoney_api.service.exception.DadosObrigatoriosLancamentoException;
import com.algaworks.algamoney_api.service.exception.PessoaInexistenteOuInativaException;
import com.algaworks.algamoney_api.domain.model.Lancamento;
import com.algaworks.algamoney_api.domain.model.Pessoa;
import com.algaworks.algamoney_api.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    private static final Logger logger = LoggerFactory.getLogger(LancamentoResource.class);

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Lancamento salvar(Lancamento lancamento) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        //o método isPresent() do Optional não funcionou.
        //!pessoa.get().getAtivo() // isPresent() // !isEmpty()
        if (pessoa == null || pessoa.get().isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        Optional<Categoria> categoria = categoriaRepository.findById(lancamento.getCategoria().getCodigo());

        if (categoria == null) {
            throw new CategoriaInexistenteOuInativaException();
        }
        if (lancamento.getDescricao() == null || lancamento.getDataVencimento() == null || lancamento.getValor() == null || lancamento.getTipo() == null || lancamento.getCategoria() == null || lancamento.getPessoa() == null) {
            throw new DadosObrigatoriosLancamentoException();
        }
        return lancamentoRepository.save(lancamento);
    }

    /**
     * 7.9. Desafio: Atualização de lançamento
     */
    public Lancamento atualizar(Integer codigo, Lancamento lancamento) {
        Optional<Lancamento> lancamentoSalvo = buscarLancamentoExistente(codigo);

        if (!lancamento.getPessoa().equals(lancamentoSalvo.get().getPessoa())) {
            validarPessoa(lancamento);
        }
        Lancamento lancamentoParaAtualizar = lancamentoSalvo.get();
        BeanUtils.copyProperties(lancamento, lancamentoParaAtualizar, "codigo");

        return lancamentoRepository.save(lancamentoParaAtualizar);
    }

    private void validarPessoa(Lancamento lancamento) {
        Integer codigoPessoa = lancamento.getPessoa().getCodigo();
        Optional<Pessoa> pessoaOptional = null;
        if (codigoPessoa != null) {
            pessoaOptional = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

            if (!pessoaOptional.isPresent() || pessoaOptional.get().isInativo()) {

                throw new PessoaInexistenteOuInativaException();
            }
        } else {
            throw new PessoaInexistenteOuInativaException();
        }
    }


    private Optional<Lancamento> buscarLancamentoExistente(Integer codigo) {
        Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(codigo);
        if (lancamentoSalvo == null) {
            throw new IllegalArgumentException();
        }
        return lancamentoSalvo;
    }

    public void remover(Integer codigo) {
        Optional<Lancamento> lancamentoId = lancamentoRepository.findById(codigo);
        if (lancamentoId.isPresent()) {
            lancamentoRepository.deleteById(lancamentoId.get().getCodigo());
        }
    }
}
