package com.bulish.marvel_characters_comics.entities.exeptions;

import java.text.MessageFormat;

public class ComicIsAlreadyAssignedException extends RuntimeException{
    public ComicIsAlreadyAssignedException(final Long comicId, final Long characterId) {
        super(MessageFormat.format("Comic: {0} is already assigned to this character: {1}",comicId, characterId));
    }
}
