package com.bulish.marvel_characters_comics.entities.dto;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ComicDto {

    private Long id;
    private String title;
    private String description;
    private List<MarvelCharacterDto> marvelCharacterDto = new ArrayList<>();

    public static ComicDto from(Comic comic){
         ComicDto comicDto = new ComicDto();
         comicDto.setId(comic.getId());
         comicDto.setTitle(comic.getTitle());
         comicDto.setDescription(comic.getDescription());
         comicDto.setMarvelCharacterDto(comic.getCharacters().stream().map(MarvelCharacterDto::from).collect(Collectors.toList()));
        return comicDto;
    }
}
