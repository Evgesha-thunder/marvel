package com.bulish.marvel_characters_comics.services;

import com.bulish.marvel_characters_comics.entities.Comic;
import com.bulish.marvel_characters_comics.specifications.ComicPage;
import com.bulish.marvel_characters_comics.specifications.ComicSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ComicService {

     Comic save(Comic comic);
     List<Comic> getAllComic();
     Comic getComicById(Long id);
     Comic deleteComicById(Long id);
     Comic editComic(Long id, Comic comic);
     Comic addCharacterToComic(Long characterId, Long comicId);
     Comic removeCharacterFromComic(Long characterId, Long comicId);
     Comic saveOrUpdate(Comic comic);
     public Page<Comic> getComicsSortedFilteredPaginated(ComicPage comicPage,
                                                         ComicSearchCriteria comicSearchCriteria);
     Comic saveComicWithImage(MultipartFile file,Comic comic);


}
