package com.moviesandchill.portalbackendservice.service.user;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.moviesandchill.portalbackendservice.dto.film.film.FilmPageDto;
import com.moviesandchill.portalbackendservice.service.film.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserFavoriteFilmService {

    private final String userServiceUrl;
    private final FilmService filmService;
    private final RestTemplate restTemplate;

    public UserFavoriteFilmService(@Value("${endpoints.user-service-url}") String userServiceUrl, FilmService filmService, RestTemplate restTemplate) {
        this.userServiceUrl = userServiceUrl;
        this.filmService = filmService;
        this.restTemplate = restTemplate;
    }

    public List<FilmPageDto> getAllFavoriteFilms(long userId) {
        String url = userServiceUrl + "/api/v1/users/" + userId + "/favorite-films";
        var json = restTemplate.getForObject(url, ArrayNode.class);
        List<FilmPageDto> dtos = new ArrayList<>();

        assert json != null;
        for (var obj : json) {
            long filmId = obj.get("filmId").asLong();
            var filmDto = filmService.getFilmPageDtoById(filmId);
            if (filmDto != null) {
                dtos.add(filmDto);
            }
        }
        return dtos;
    }

    public void addFavoriteFilm(long userId, long filmId) {
        String url = userServiceUrl + "/api/v1/users/" + userId + "/favorite-films";
        restTemplate.postForObject(url, filmId, Void.class);
    }

    public void deleteAllFavoriteFilms(long userId) {
        String url = userServiceUrl + "/api/v1/users/" + userId + "/favorite-films";
        restTemplate.delete(url);
    }

    public void deleteFavoriteFilm(long userId, long filmId) {
        String url = userServiceUrl + "/api/v1/users/" + userId + "/favorite-films/" + filmId;
        restTemplate.delete(url);
    }
}
