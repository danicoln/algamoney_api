package com.algaworks.algamoney_api.resource;

import com.algaworks.algamoney_api.event.RecursoCriadoEvent;
import com.algaworks.algamoney_api.model.Lancamento;
import com.algaworks.algamoney_api.repository.LancamentoRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    //Desafio
    @GetMapping
    public List<Lancamento> listarLancamentos(){
        return repository.findAll();
    }
    //Desafio
    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPorCodigo(@Valid @PathVariable Integer codigo){
        return repository.findById(codigo)
                .map(lancamento -> ResponseEntity.ok(lancamento))
                .orElse(ResponseEntity.notFound().build());
    }

    //Desafio5.3
    @PostMapping
    public ResponseEntity<Lancamento> cadastrarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = repository.save(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }


}
