package com.bulish.marvel_characters_comics.entities.dto;

import com.bulish.marvel_characters_comics.entities.Comic;
import lombok.Data;

@Data
public class PlainComicDto {
    private Long id;
    private String title;
    private String description;

    public static PlainComicDto from(Comic comic){
        PlainComicDto plainComicDto=  new PlainComicDto();
        plainComicDto.setId(comic.getId());
        plainComicDto.setTitle(comic.getTitle());
       plainComicDto.setDescription(comic.getDescription());
        return plainComicDto;
    }
}
