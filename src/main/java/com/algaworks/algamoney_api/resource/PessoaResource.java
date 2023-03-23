package com.algaworks.algamoney_api.resource;

import com.algaworks.algamoney_api.model.Pessoa;
import com.algaworks.algamoney_api.repository.PessoaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public List<Pessoa> listarPessoas(){
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        //melhorar o codigo
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{codigo]")
                .buildAndExpand(pessoaSalva.getCodigo()).toUri();
        response.setHeader("Location",uri.toASCIIString());

        return ResponseEntity.created(uri).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Integer codigo){
        return pessoaRepository.findById(codigo)
                .map(pessoa -> ResponseEntity.ok(pessoa))
                .orElse(ResponseEntity.notFound().build());
    }

}
