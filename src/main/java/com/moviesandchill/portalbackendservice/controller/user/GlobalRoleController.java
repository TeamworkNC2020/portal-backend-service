package com.moviesandchill.portalbackendservice.controller.user;

import com.moviesandchill.portalbackendservice.dto.user.globalrole.GlobalRoleDto;
import com.moviesandchill.portalbackendservice.dto.user.globalrole.NewGlobalRoleDto;
import com.moviesandchill.portalbackendservice.dto.user.globalrole.UpdateGlobalRoleDto;
import com.moviesandchill.portalbackendservice.service.user.GlobalRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(
        path = "api/v1/global-roles",
        produces = "application/json"
)
@Slf4j
public class GlobalRoleController {

    private final GlobalRoleService globalRoleService;


    public GlobalRoleController(GlobalRoleService globalRoleService) {
        this.globalRoleService = globalRoleService;
    }

    @GetMapping
    public List<GlobalRoleDto> getAllGlobalRoles() {
        return globalRoleService.getAllGlobalRoles();
    }

    @PostMapping
    @Secured("ROLE_USER")
    public GlobalRoleDto addGlobalRole(@RequestBody NewGlobalRoleDto newGlobalRoleDto) {
        return globalRoleService.addGlobalRole(newGlobalRoleDto);
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    public void deleteAllGlobalRoles() {
        globalRoleService.deleteAllGlobalRoles();
    }

    public GlobalRoleDto getGlobalRole(@PathVariable long globalRoleId) {
        return globalRoleService.getGlobalRole(globalRoleId);
    }

    @PutMapping("/{globalRoleId}")
    @Secured("ROLE_USER")
    public void updateGlobalRole(@PathVariable long globalRoleId, @RequestBody UpdateGlobalRoleDto updateUserDto) {
        globalRoleService.updateGlobalRole(globalRoleId, updateUserDto);
    }

    @DeleteMapping("/{globalRoleId}")
    @Secured("ROLE_USER")
    public void deleteGlobalRole(@PathVariable long globalRoleId) {
        globalRoleService.deleteGlobalRole(globalRoleId);
    }
}
