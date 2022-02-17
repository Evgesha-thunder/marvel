package com.bulish.marvel_characters_comics.entities;


import com.bulish.marvel_characters_comics.entities.dto.ComicDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comics")
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "characters_comics",
            joinColumns = @JoinColumn(name = "comic_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    @JsonIgnore
    private List<MarvelCharacter> characters = new ArrayList<>();


    public void addCharacter(MarvelCharacter character){
        characters.add(character);
    }

    public void deleteCharacter(MarvelCharacter character){
        characters.remove(character);
    }

        public static Comic from(ComicDto comicDto){
        Comic comic = new Comic();
        comic.setTitle(comicDto.getTitle());
         return comic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comic comic = (Comic) o;
        return Objects.equals(id, comic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
