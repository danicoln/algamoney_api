package com.algaworks.algamoney_api.repository.lancamento;

import com.algaworks.algamoney_api.domain.model.Lancamento;
import com.algaworks.algamoney_api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney_api.repository.projection.ResumoLancamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);
        //criar as restricoes

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
        
        adicionarRestricoesDePaginacao(query, pageable);
        
        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    /**
     * aula: 7.1. Implementando projeção de lançamento
     * */
    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();                               // sempre iniciando com o construtor (builder)
        CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);     // retornamos esse buider para ResumoLancamento
        Root<Lancamento> root = criteria.from(Lancamento.class);                                    // Buscamos o ResumoLancamento de uma entidade, que é Root<Lancamento>, é daqui que iremos fazer a consulta

        criteria.select(builder.construct(ResumoLancamento.class,                   // daqui, fazemos a seleção do que queremos. Observação: Temos que passar os atributos na ordem correta como está em ResumoLancamento
                root.get("codigo"), root.get("descricao"),
                root.get("dataVencimento"), root.get("dataPagamento"),
                root.get("valor"), root.get("tipo"),
                root.get("categoria").get("nome"),
                root.get("pessoa").get("nome")
        ));
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);  // criamos as restrições
        criteria.where(predicates);                                                 // fazemos o filtro e retornamos.

        TypedQuery<ResumoLancamento> query = entityManager.createQuery(criteria);

        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));

    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return entityManager.createQuery(criteria).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {

        List<Predicate> predicates = new ArrayList<>();

        if(!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())){
            predicates.add(builder.like(
                    //o metodo lower é como se chamasse no BD
                    //WHERE descricao LIKE '%algum texto aqui%'

                    builder.lower(root.get("descricao")),
                    "%" + lancamentoFilter.getDescricao()
                            .toLowerCase() + "%"));
        }
        if(lancamentoFilter.getDataVencimentoDe() != null){
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("dataVencimento"),
                            lancamentoFilter.getDataVencimentoDe()));
        }
        if(lancamentoFilter.getDataVencimentoAte() != null){
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("dataVencimento"),
                            lancamentoFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
