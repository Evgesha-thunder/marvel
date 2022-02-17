package com.bulish.marvel_characters_comics.entities.dto;

import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import lombok.Data;

@Data
public class PlainMarvelDto {

    private Long id;
    private String name;
    private String description;

    public static PlainMarvelDto from(MarvelCharacter marvelCharacter){
       PlainMarvelDto plainMarvelDto = new PlainMarvelDto();
        plainMarvelDto.setId(marvelCharacter.getId());
       plainMarvelDto.setName(marvelCharacter.getName());
       plainMarvelDto.setDescription(marvelCharacter.getDescription());
        return plainMarvelDto;
    }
}
