package com.algaworks.algamoney_api.resource;

import com.algaworks.algamoney_api.event.RecursoCriadoEvent;
import com.algaworks.algamoney_api.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.algamoney_api.domain.model.Lancamento;
import com.algaworks.algamoney_api.repository.LancamentoRepository;
import com.algaworks.algamoney_api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney_api.repository.projection.ResumoLancamento;
import com.algaworks.algamoney_api.service.LancamentoService;
import com.algaworks.algamoney_api.service.exception.PessoaInexistenteOuInativaException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('read')")
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return repository.filtrar(lancamentoFilter, pageable);
    }

    /**
     * aula: 7.1. Implementando projeção de lançamento
     * */
    @GetMapping(params = "resumo") // precisamos passar o parâmetro(se tiver a palavra "resumo" na pesquisa, este recurso será chamado)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('read')")
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable){
        return repository.resumir(lancamentoFilter, pageable);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('read')")
    public ResponseEntity<Lancamento> buscarPorCodigo(@Valid @PathVariable Integer codigo){
        return repository.findById(codigo)
                .map(lancamento -> ResponseEntity.ok(lancamento))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('write')")
    public ResponseEntity<Lancamento> cadastrarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    /**Exception apenas de Lançamentos*/
    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<AlgamoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);

    }
    //TODO: Testar método
    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and hasAuthority('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer codigo){
        lancamentoService.remover(codigo);
    }

}
