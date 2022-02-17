package com.bulish.marvel_characters_comics.entities.dto;

import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MarvelCharacterDto {

        private Long id;
        private String name;
        private String description;
        private List<ComicDto> comicsDto = new ArrayList<>();

        public static MarvelCharacterDto from(MarvelCharacter marvelCharacter){
           MarvelCharacterDto marvelCharacterDto = new MarvelCharacterDto();
            marvelCharacterDto.setId(marvelCharacter.getId());
            marvelCharacterDto.setName(marvelCharacter.getName());
            marvelCharacterDto.setDescription(marvelCharacter.getDescription());
           marvelCharacterDto.setComicsDto(marvelCharacter.getComics().stream().map(ComicDto::from).collect(Collectors.toList()));
            return marvelCharacterDto;
        }
}
