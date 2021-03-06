package com.moviesandchill.portalbackendservice.controller.film;

import com.moviesandchill.portalbackendservice.dto.film.country.CountryDto;
import com.moviesandchill.portalbackendservice.dto.film.film.FilmDto;
import com.moviesandchill.portalbackendservice.dto.film.film.FilmPageDto;
import com.moviesandchill.portalbackendservice.dto.film.film.FullFilmDto;
import com.moviesandchill.portalbackendservice.dto.film.film.RandFilmDto;
import com.moviesandchill.portalbackendservice.dto.film.genre.GenreDto;
import com.moviesandchill.portalbackendservice.dto.film.review.ReviewDto;
import com.moviesandchill.portalbackendservice.dto.film.screenshot.ScreenshotDto;
import com.moviesandchill.portalbackendservice.dto.film.staff.StaffDto;
import com.moviesandchill.portalbackendservice.mapper.CommonMapper;
import com.moviesandchill.portalbackendservice.service.film.FilmService;
import com.moviesandchill.portalbackendservice.utils.RestTemplateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping(
        path = "api/v1/films",
        produces = "application/json"
)
public class FilmController {

    private final FilmService filmService;
    private final CommonMapper commonMapper;

    public FilmController(FilmService filmService, CommonMapper commonMapper) {
        this.filmService = filmService;
        this.commonMapper = commonMapper;
    }

    @GetMapping
    public List<FilmDto> getAllFilm() {
        return filmService.getAllFilm();
    }

    @GetMapping("/allpage")
    public List<FilmPageDto> getAllPageFilm() {
        return filmService.getAllPageFilm();
    }

    @GetMapping("/newfilms")
    public List<FilmPageDto> getFirstNewFilms() {
        return filmService.getFirstNewFilms();
    }

    @GetMapping("/randomthreefilms")
    public List<RandFilmDto> getRandomThreeFilms() {
        return filmService.getRandomThreeFilms();
    }

    @GetMapping("/popularfilms")
    public List<FilmPageDto> getFirstPopularFilms() {
        return filmService.getFirstPopularFilms();
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    public void deleteAllFilm() {
        filmService.deleteAllFilm();
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<FilmDto> getFilmById(@PathVariable Long filmId) {
        return commonMapper.toResponseEntity(filmService.getFilmById(filmId));
    }

    @GetMapping("/full/{filmId}")
    public ResponseEntity<FullFilmDto> getFullFilmById(@PathVariable Long filmId) {
        return commonMapper.toResponseEntity(filmService.getFullFilmById(filmId));
    }

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<FilmDto> addFilm(@RequestBody FilmDto filmDto) {
        return commonMapper.toResponseEntity(filmService.addFilm(filmDto));
    }

    @DeleteMapping("/{filmId}")
    @Secured("ROLE_USER")
    public void deleteFilmById(@PathVariable Long filmId) {
        filmService.deleteFilmById(filmId);
    }

    @GetMapping("/{filmId}/genres")
    public List<GenreDto> getAllGenreWithFilm(@PathVariable Long filmId) {
        return filmService.getAllGenreWithFilm(filmId);
    }

    @PostMapping("/{filmId}/addGenre/{genreId}")
    @Secured("ROLE_ADMIN")
    public void addGenreToFilm(@PathVariable Long filmId, @PathVariable Long genreId) throws Exception {
        filmService.addGenreToFilm(filmId, genreId);
    }

    @GetMapping("/{filmId}/staffs")
    public List<StaffDto> getAllStaffWithFilm(@PathVariable Long filmId) {
        return filmService.getAllStaffWithFilm(filmId);
    }

    @PostMapping("/{filmId}/setAgeLimit{ageLimitID}")
    public void setAgeLimitByFilmId(@PathVariable Long filmId,@PathVariable  Long ageLimitID) throws Exception {
        filmService.setAgeLimitByFilmId(filmId,ageLimitID);
    }


    @PostMapping("/{filmId}/addStaff/{staffId}")
    @Secured("ROLE_USER")
    public void addStaffToFilm(@PathVariable Long filmId, @PathVariable Long staffId) throws Exception {
        filmService.addStaffToFilm(filmId, staffId);
    }

    @GetMapping("/{filmId}/reviews")
    public List<ReviewDto> getAllReviewWithFilm(@PathVariable Long filmId) {
        return filmService.getAllReviewWithFilm(filmId);
    }

    @PostMapping("/{filmId}/addReview/{reviewId}")
    @Secured("ROLE_USER")
    public void addReviewToFilm(@PathVariable Long filmId, @PathVariable Long reviewId) throws Exception {
        filmService.addReviewToFilm(filmId, reviewId);
    }

    @GetMapping("/{filmId}/screenshots")
    public List<ScreenshotDto> getAllScreenshotWithFilm(@PathVariable Long filmId) {
        return filmService.getAllScreenshotWithFilm(filmId);
    }

    @PostMapping("/{filmId}/addScreenshot/{screenshotId}")
    @Secured("ROLE_USER")
    public void addScreenshotToFilm(@PathVariable Long filmId, @PathVariable Long screenshotId) throws Exception {
        filmService.addScreenshotToFilm(filmId, screenshotId);
    }

    @PostMapping("/{filmId}/addListGenre")
    public void addListGenreToFilm(@PathVariable Long filmId,@RequestBody  List<Long> genres) throws Exception {
        filmService.addListGenreToFilm(filmId,genres);
    }

    @PostMapping("/{filmId}/addListStaff")
    public void addListStaffToFilm(@PathVariable Long filmId,@RequestBody  List<Long> staffs) throws Exception {
        filmService.addListStaffToFilm(filmId,staffs);
    }
}
