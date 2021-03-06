package com.moviesandchill.portalbackendservice.controller.film;

import com.moviesandchill.portalbackendservice.service.film.FilmUploadContentService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping(
        path = "api/v1/filmContent",
        produces = "application/json"
)
public class FilmUploadContentController {

    private final FilmUploadContentService filmUploadContentService;

    public FilmUploadContentController(FilmUploadContentService filmUploadContentService) {
        this.filmUploadContentService = filmUploadContentService;
    }

    @PostMapping(path = "/{filmId}/uploadPoster", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public void uploadFilmPoster(@PathVariable long filmId, @RequestPart("file") MultipartFile file) throws Exception {
        filmUploadContentService.uploadFilmPoster(filmId, file);
    }

    @PostMapping(path = "/{filmId}/uploadScreenshot", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public void uploadFilmScreenshot(@PathVariable long filmId, @RequestPart("file") MultipartFile file) throws Exception {
        filmUploadContentService.uploadFilmScreenshot(filmId, file);
    }

    @PostMapping(path = "/{filmId}/uploadVideo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured("ROLE_USER")
    public void uploadFilmVideo(@PathVariable long filmId, @RequestPart("file") MultipartFile file) throws Exception {
        filmUploadContentService.uploadFilmVideo(filmId, file);
    }
}
