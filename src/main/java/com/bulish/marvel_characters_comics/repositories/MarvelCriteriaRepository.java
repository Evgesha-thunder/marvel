package com.bulish.marvel_characters_comics.repositories;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import com.bulish.marvel_characters_comics.specifications.CharacterPage;
import com.bulish.marvel_characters_comics.specifications.CharacterSearchCriteria;
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
public class MarvelCriteriaRepository {


    private final CriteriaBuilder criteriaBuilder;
    private final EntityManager entityManager;

    @Autowired
    public MarvelCriteriaRepository( EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<MarvelCharacter> findAllWithFilters(CharacterPage characterPage,
                                                    CharacterSearchCriteria characterSearchCriteria){
        CriteriaQuery<MarvelCharacter> criteriaQuery = criteriaBuilder.createQuery(MarvelCharacter.class);
        Root<MarvelCharacter> marvelCharacterRoot = criteriaQuery.from(MarvelCharacter.class);
        Predicate predicate = getPredicate(characterSearchCriteria, marvelCharacterRoot);
        criteriaQuery.where(predicate);
        setOrder(characterPage,criteriaQuery, marvelCharacterRoot);

        TypedQuery<MarvelCharacter> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(characterPage.getPageNumber() * characterPage.getPageSize());
        typedQuery.setMaxResults(characterPage.getPageSize());

        Pageable pageable  = getPagable(characterPage);

        long characterCount = getCharacterCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(),pageable,characterCount);
    }

    private long getCharacterCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<MarvelCharacter> countRoot = countQuery.from(MarvelCharacter.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPagable(CharacterPage characterPage) {
        Sort sort = Sort.by(characterPage.getSortDirection(), characterPage.getSortBy());
        return PageRequest.of(characterPage.getPageNumber(), characterPage.getPageSize(), sort);
    }

    private void setOrder(CharacterPage characterPage, CriteriaQuery<MarvelCharacter> criteriaQuery, Root<MarvelCharacter> characterRoot) {
        if (characterPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(characterRoot.get(characterPage.getSortBy())));
        }else {
            criteriaQuery.orderBy(criteriaBuilder.desc(characterRoot.get(characterPage.getSortBy())));
        }

    }

    private Predicate getPredicate(CharacterSearchCriteria characterSearchCriteria, Root<MarvelCharacter> marvelRoot){

        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(characterSearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(marvelRoot.get("name"),
                            "%" + characterSearchCriteria.getName() + "%")
            );
        }

        if (Objects.nonNull(characterSearchCriteria.getDescription())){
            predicates.add(
                    criteriaBuilder.like(marvelRoot.get("description"),
                            "%" + characterSearchCriteria.getDescription() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

