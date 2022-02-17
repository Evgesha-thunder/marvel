package com.bulish.marvel_characters_comics.repositories;

import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<MarvelCharacter,Long> {


}

