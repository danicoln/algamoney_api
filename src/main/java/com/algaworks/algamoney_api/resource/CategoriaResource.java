package com.algaworks.algamoney_api.resource;

import com.algaworks.algamoney_api.model.Categoria;
import com.algaworks.algamoney_api.repository.CategoriaRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }
//    public ResponseEntity<?> listar(){
//        List<Categoria> categorias = categoriaRepository.findAll();
//        return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build();
//    } // no retorno, o código verifica se a lista está vazia, se sim, retorna um 404, se não, 200ok.

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED) // ao terminar a execução deste método, retornará 201
    public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        //melhorar este código
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(categoriaSalva.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());

        // com o retorno created, não precisamos passar a anotação de status.
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    //Desafio 3.7 - Retornar 404 caso não exista a categoria
    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Integer codigo){
        return categoriaRepository.findById(codigo)
                .map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }
}
