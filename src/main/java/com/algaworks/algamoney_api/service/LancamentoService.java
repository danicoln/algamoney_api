package com.algaworks.algamoney_api.service;

import com.algaworks.algamoney_api.domain.model.Categoria;
import com.algaworks.algamoney_api.repository.CategoriaRepository;
import com.algaworks.algamoney_api.repository.LancamentoRepository;
import com.algaworks.algamoney_api.service.exception.CategoriaInexistenteOuInativaException;
import com.algaworks.algamoney_api.service.exception.DadosObrigatoriosLancamentoException;
import com.algaworks.algamoney_api.service.exception.PessoaInexistenteOuInativaException;
import com.algaworks.algamoney_api.domain.model.Lancamento;
import com.algaworks.algamoney_api.domain.model.Pessoa;
import com.algaworks.algamoney_api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

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
     * 7.9. Desafio: Atualização de lançamento*/
    public Lancamento atualizar(Integer codigo, Lancamento lancamento) {
        Optional<Lancamento> lancamentoSalvo = buscarLancamentoExistente(codigo);

        if (lancamentoSalvo.isPresent()) {
            validarPessoa(lancamento);
        }
        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return lancamentoRepository.save(lancamentoSalvo.get());
    }

    private void validarPessoa(Lancamento lancamento) {
        Integer codigoPessoa = lancamento.getPessoa().getCodigo();
        if (codigoPessoa != null) {
            Optional<Pessoa> pessoaOptional = pessoaRepository.findById(codigoPessoa);

            if (pessoaOptional.isPresent()) {
                Pessoa pessoa = pessoaOptional.get();

                if (pessoa.isInativo()) {
                    throw new PessoaInexistenteOuInativaException();
                }
            } else {
                throw new PessoaInexistenteOuInativaException();
            }
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
