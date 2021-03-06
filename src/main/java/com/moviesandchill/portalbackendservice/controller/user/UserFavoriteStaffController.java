package com.moviesandchill.portalbackendservice.controller.user;

import com.moviesandchill.portalbackendservice.dto.film.staff.StaffDto;
import com.moviesandchill.portalbackendservice.service.user.UserFavoriteStaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(
        path = "api/v1/users/{userId}/favorite-staffs",
        produces = "application/json"
)
@Slf4j
public class UserFavoriteStaffController {
    private final UserFavoriteStaffService userFavoriteStaffService;

    public UserFavoriteStaffController(UserFavoriteStaffService userFavoriteStaffService) {
        this.userFavoriteStaffService = userFavoriteStaffService;
    }

    @GetMapping
    List<StaffDto> getAllFavoriteStaffs(@PathVariable long userId) {
        return userFavoriteStaffService.getAllFavoriteStaffs(userId);
    }

    @PostMapping
    @Secured("ROLE_USER")
    void addFavoriteStaff(@PathVariable long userId, @RequestBody long staffId) {
        userFavoriteStaffService.addFavoriteStaff(userId, staffId);
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    void deleteAllFavoriteStaffs(@PathVariable long userId) {
        userFavoriteStaffService.deleteAllFavoriteStaffs(userId);
    }

    @DeleteMapping("/{staffId}")
    @Secured("ROLE_USER")
    void deleteFavoriteStaff(@PathVariable long userId, @PathVariable long staffId) {
        userFavoriteStaffService.deleteFavoriteStaff(userId, staffId);
    }

}
