package com.bulish.marvel_characters_comics.entities.exeptions;

import java.text.MessageFormat;

public class ComicNotFoundException extends RuntimeException{

    public ComicNotFoundException(Long id) {
        super(MessageFormat.format("This comic with id: {0} is not found", id));
    }
}
