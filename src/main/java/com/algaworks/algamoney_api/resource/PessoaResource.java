package com.algaworks.algamoney_api.resource;

import com.algaworks.algamoney_api.event.RecursoCriadoEvent;
import com.algaworks.algamoney_api.domain.model.Pessoa;
import com.algaworks.algamoney_api.repository.PessoaRepository;
import com.algaworks.algamoney_api.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    /**
     * Publicador de evento de aplicação
     */
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('write')")
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('read')")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Integer codigo) {
        return pessoaRepository.findById(codigo)
                .map(pessoa -> ResponseEntity.ok(pessoa))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and hasAuthority('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Integer codigo) {
        this.pessoaRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('write')")
    public ResponseEntity<Pessoa> atualizar(@Valid @PathVariable Integer codigo, @RequestBody Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{codigo}/ativo")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Integer codigo, @RequestBody Boolean ativo) {
        pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('read')")
    public Page<Pessoa> pesquisar(@RequestParam(required = false, defaultValue = "") String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContaining(nome, pageable);
    }

}
