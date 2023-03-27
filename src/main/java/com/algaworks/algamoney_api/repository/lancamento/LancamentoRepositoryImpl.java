package com.algaworks.algamoney_api.repository.lancamento;

import com.algaworks.algamoney_api.model.Lancamento;
import com.algaworks.algamoney_api.repository.filter.LancamentoFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

        Root<Lancamento> root = criteria.from(Lancamento.class);
        //criar as restricoes

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
        return query.getResultList();
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
        if(lancamentoFilter.getDataVendimentoDe() != null){
//            predicates.add(e)

        }
        if(lancamentoFilter.getDataVendimentoAte() != null){
//            predicates.add(e)

        }

        return predicates.toArray(new Predicate[predicates.size()]);

    }


}
