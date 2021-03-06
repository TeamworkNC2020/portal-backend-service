package com.moviesandchill.portalbackendservice.controller.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.moviesandchill.portalbackendservice.dto.stream.session.NewSessionDto;
import com.moviesandchill.portalbackendservice.dto.stream.session.SessionDto;
import com.moviesandchill.portalbackendservice.dto.stream.session.SessionParDto;
import com.moviesandchill.portalbackendservice.dto.stream.watcher.WatcherDto;
import com.moviesandchill.portalbackendservice.mapper.CommonMapper;
import com.moviesandchill.portalbackendservice.service.stream.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(
        path = "api/v1/sessions",
        produces = "application/json"
)
public class SessionController {

    private final SessionService sessionService;
    private final CommonMapper commonMapper;

    public SessionController(SessionService sessionService, CommonMapper commonMapper) {
        this.sessionService = sessionService;
        this.commonMapper = commonMapper;
    }

    @GetMapping
    public List<SessionDto> getAllSession() {
        return sessionService.getAllSession();
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    public void deleteAllSession() {
        sessionService.deleteAllSession();
    }

    @GetMapping("/{sessionID}")
    public ResponseEntity<SessionParDto> getSessionById(@PathVariable Long sessionID) {
        return commonMapper.toResponseEntity(sessionService.getSessionById(sessionID));
    }

    @PostMapping()
    @Secured("ROLE_USER")
    public ResponseEntity<SessionDto> addSession(@RequestBody NewSessionDto newSessionDto) {
        return commonMapper.toResponseEntity(sessionService.addSession(newSessionDto));
    }

    @DeleteMapping("/{sessionID}")
    @Secured("ROLE_USER")
    public void deleteSessionById(@PathVariable Long sessionID) {
        sessionService.deleteSessionById(sessionID);
    }

    @PostMapping("/{sessionID}/setTimeAndState")
    @Secured("ROLE_USER")
    public void setSessionTimeAndState(@PathVariable Long sessionID, @RequestBody JsonNode jsonNode) throws Exception {
        sessionService.setSessionTimeAndState(sessionID, jsonNode);
    }

    @PostMapping("/param")
    @Secured("ROLE_USER")
    public ResponseEntity<SessionDto> addSessionByParameters(@RequestBody SessionParDto sessionParDto) {
        return commonMapper.toResponseEntity(sessionService.addSessionByParameters(sessionParDto));
    }

    @GetMapping("/{sessionID}/watchers")
    public List<WatcherDto> getAllWatcherWithSession(@PathVariable Long sessionID) {
        return sessionService.getAllWatcherWithSession(sessionID);
    }

    @PostMapping("/{sessionID}/addWatcher/{watcherID}")
    public void addWatcherToSession(@PathVariable Long sessionID, @PathVariable Long watcherID) throws Exception {
        sessionService.addWatcherToSession(watcherID, sessionID);
    }

    @PostMapping("/{sessionID}/invite")
    @Secured("ROLE_USER")
    public void inviteFriendToSession(@PathVariable Long sessionID, @RequestBody Long friendID) {
        sessionService.inviteFriendToSession(sessionID, friendID);
    }
}
