package com.bulish.marvel_characters_comics.entities;


import com.bulish.marvel_characters_comics.entities.dto.MarvelCharacterDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "characters")
public class MarvelCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String imageLink;




    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "characters_comics",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id"))
    @JsonIgnore
    List<Comic> comics = new ArrayList<>();


    public void addComic(Comic comic){
        comics.add(comic);
    }

    public void deleteComic(Comic comic){
        comics.remove(comic);
    }

    public static MarvelCharacter from(MarvelCharacterDto marvelCharacterDto){
         MarvelCharacter marvelCharacter = new MarvelCharacter();
         marvelCharacter.setName(marvelCharacter.getName());
         return marvelCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarvelCharacter character = (MarvelCharacter) o;
        return Objects.equals(id, character.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
