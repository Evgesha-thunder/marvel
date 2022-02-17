package com.bulish.marvel_characters_comics.entities.exeptions;

import java.text.MessageFormat;

public class CharacterNotFoundException extends RuntimeException{

    public CharacterNotFoundException(Long id) {
        super(MessageFormat.format("Character with id : {0} is not found", id));
    }
}
