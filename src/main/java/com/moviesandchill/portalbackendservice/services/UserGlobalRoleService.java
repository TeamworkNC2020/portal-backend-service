package com.moviesandchill.portalbackendservice.services;

import com.moviesandchill.portalbackendservice.dto.globalrole.GlobalRoleDto;

import java.util.List;

public interface UserGlobalRoleService {
    List<GlobalRoleDto> getAllGlobalRoles(long userId);
}
