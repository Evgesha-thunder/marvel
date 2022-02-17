package com.bulish.marvel_characters_comics.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class MarvelComics implements Serializable {


        @Column(name = "character_id")
        Long characterId;

        @Column(name = "comic_id")
        Long comicId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarvelComics that = (MarvelComics) o;
        return Objects.equals(characterId, that.characterId) && Objects.equals(comicId, that.comicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterId, comicId);
    }
}

