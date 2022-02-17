package com.bulish.marvel_characters_comics.services;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.entities.MarvelCharacter;
import com.bulish.marvel_characters_comics.specifications.CharacterPage;
import com.bulish.marvel_characters_comics.specifications.CharacterSearchCriteria;
import com.bulish.marvel_characters_comics.specifications.ComicPage;
import com.bulish.marvel_characters_comics.specifications.ComicSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CharacterService {

    MarvelCharacter save(MarvelCharacter character);
     List<MarvelCharacter> getAllCharacters();
     MarvelCharacter getCharacterById(Long id);
     MarvelCharacter deleteCharacterById(Long id);
     MarvelCharacter editCharacter(Long id, MarvelCharacter character);
     MarvelCharacter addComicToCharacter(Long characterId, Long comicId);
     MarvelCharacter removeComicFromCharacter(Long characterId, Long comicId);
     MarvelCharacter saveOrUpdate(MarvelCharacter character);
     Page<MarvelCharacter> getCharactersSortedFilteredPaginated(CharacterPage characterPage,
                                                         CharacterSearchCriteria characterSearchCriteria);
     MarvelCharacter saveCharacterWithImage(MultipartFile file, MarvelCharacter marvelCharacter);



}
