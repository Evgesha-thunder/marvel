package com.bulish.marvel_characters_comics.entities.exeptions;

import java.text.MessageFormat;

public class CharacterIsAlreadyAssignedToComic extends RuntimeException{
    public CharacterIsAlreadyAssignedToComic(final Long characterId, final Long comicId) {
        super(MessageFormat.format("Character: {0} is already assigned to this comic: {1}", characterId, comicId));
    }
}
