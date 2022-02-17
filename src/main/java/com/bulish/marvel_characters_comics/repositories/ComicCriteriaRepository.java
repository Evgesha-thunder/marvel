package com.bulish.marvel_characters_comics.repositories;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.specifications.ComicPage;
import com.bulish.marvel_characters_comics.specifications.ComicSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ComicCriteriaRepository {

    private final CriteriaBuilder criteriaBuilder;
    private final EntityManager entityManager;

    @Autowired
    public ComicCriteriaRepository( EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Comic>  findAllWithFilters(ComicPage comicPage, ComicSearchCriteria comicSearchCriteria){
        CriteriaQuery<Comic> criteriaQuery = criteriaBuilder.createQuery(Comic.class);
        Root<Comic> comicRoot = criteriaQuery.from(Comic.class);
        Predicate predicate = getPredicate(comicSearchCriteria, comicRoot);
        criteriaQuery.where(predicate);
        setOrder(comicPage,criteriaQuery, comicRoot);

        TypedQuery<Comic> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(comicPage.getPageNumber() * comicPage.getPageSize());
        typedQuery.setMaxResults(comicPage.getPageSize());

        Pageable pageable  = getPagable(comicPage);

        long comicCount = getComicCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(),pageable,comicCount);
    }

    private long getComicCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Comic> countRoot = countQuery.from(Comic.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPagable(ComicPage comicPage) {
        Sort sort = Sort.by(comicPage.getSortDirection(), comicPage.getSortBy());
        return PageRequest.of(comicPage.getPageNumber(), comicPage.getPageSize(), sort);
    }

    private void setOrder(ComicPage comicPage, CriteriaQuery<Comic> criteriaQuery, Root<Comic> comicRoot) {
        if (comicPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(comicRoot.get(comicPage.getSortBy())));
        }else {
            criteriaQuery.orderBy(criteriaBuilder.desc(comicRoot.get(comicPage.getSortBy())));
        }

    }

    private Predicate getPredicate(ComicSearchCriteria comicSearchCriteria, Root<Comic> comicRoot){

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(comicSearchCriteria.getTitle())){
            predicates.add(
                    criteriaBuilder.like(comicRoot.get("title"),
                            "%" + comicSearchCriteria.getTitle() + "%")
            );
        }

        if (Objects.nonNull(comicSearchCriteria.getDescription())){
            predicates.add(
                    criteriaBuilder.like(comicRoot.get("description"),
                            "%" + comicSearchCriteria.getDescription() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
