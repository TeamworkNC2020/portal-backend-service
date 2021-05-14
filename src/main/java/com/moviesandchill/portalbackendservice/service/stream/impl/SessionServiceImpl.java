package com.moviesandchill.portalbackendservice.service.stream.impl;

import com.moviesandchill.portalbackendservice.dto.chat.chat.ChatDto;
import com.moviesandchill.portalbackendservice.dto.chat.chat.NewChatDto;
import com.moviesandchill.portalbackendservice.dto.chat.notification.NewNotificationDto;
import com.moviesandchill.portalbackendservice.dto.stream.session.NewSessionDto;
import com.moviesandchill.portalbackendservice.dto.stream.session.SessionDto;
import com.moviesandchill.portalbackendservice.dto.stream.session.SessionParDto;
import com.moviesandchill.portalbackendservice.dto.stream.watcher.WatcherDto;
import com.moviesandchill.portalbackendservice.mapper.CommonMapper;
import com.moviesandchill.portalbackendservice.service.chat.ChatService;
import com.moviesandchill.portalbackendservice.service.chat.NotificationService;
import com.moviesandchill.portalbackendservice.service.stream.SessionService;
import com.moviesandchill.portalbackendservice.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    private String streamServiceUrl;

    private final ChatService chatService;
    private final CommonMapper commonMapper;
    private final NotificationService notificationService;

    public SessionServiceImpl(ChatService chatService, CommonMapper commonMapper, NotificationService notificationService) {
        this.chatService = chatService;
        this.commonMapper = commonMapper;
        this.notificationService = notificationService;
    }


    @Override
    public List<SessionDto> getAllSession() {
        String url = streamServiceUrl + "/sessions";
        Optional<SessionDto[]> listSessionDtoOptional = RestTemplateUtils.get(url, SessionDto[].class);
        return commonMapper.toList(listSessionDtoOptional);
    }

    @Override
    public void deleteAllSession() {
        String url = streamServiceUrl + "/sessions";
        RestTemplateUtils.delete(url);
    }

    @Override
    public Optional<SessionDto> getSessionById(Long sessionID) {
        String url = streamServiceUrl + "/sessions/" + sessionID;
        return RestTemplateUtils.get(url, null, SessionDto.class);
    }

    @Override
    public Optional<SessionDto> addSession(NewSessionDto newSessionDto) {
        NewChatDto newChatDto = new NewChatDto(""); //FIXME(anton) ?
        ChatDto chat = chatService.addChat(newChatDto);
        long chatId = chat.getChatId();

        SessionDto sessionDto = SessionDto.builder()
                .chatID(chatId)
                .filmID(newSessionDto.getFilmID())
                .organizerID(newSessionDto.getOrganizerID())
                .stopTime(LocalTime.MIN)
                .build();

        String url = streamServiceUrl + "/sessions";
        return RestTemplateUtils.post(url, sessionDto, SessionDto.class);
    }

    @Override
    public void deleteSessionById(Long sessionID) {
        String url = streamServiceUrl + "/sessions/" + sessionID;
        RestTemplateUtils.delete(url);
    }

    @Override
    public Optional<SessionDto> addSessionByParameters(SessionParDto sessionParDto) {
        String url = streamServiceUrl + "/sessions/param";
        return RestTemplateUtils.post(url, sessionParDto, SessionDto.class);
    }

    @Override
    public List<WatcherDto> getAllWatcherWithSession(Long sessionID) {
        String url = streamServiceUrl + "/sessions/" + sessionID + "/watchers";
        Optional<WatcherDto[]> listWatcherDtoOptional = RestTemplateUtils.get(url, WatcherDto[].class);
        return commonMapper.toList(listWatcherDtoOptional);
    }

    @Override
    public void addWatcherToSession(Long watcherID, Long sessionID) throws Exception {
        String url = streamServiceUrl + "/sessions/" + sessionID + "/watchers/" + watcherID;
        RestTemplateUtils.post(url, null);
    }

    @Override
    public void inviteFriendToSession(Long sessionID, Long userID) {
        var sessionDto = getSessionById(sessionID).orElseThrow();
        var notification = NewNotificationDto.builder()
                .text("hi! go to stream: https://mac21-ui.herokuapp.com/room/" + sessionDto.getSessionID())
                .userId(userID)
                .build();

        notificationService.addNotification(notification);
    }

    @Autowired
    public void setStreamServiceUrl(@Value("${endpoint.stream-service.url}") String streamServiceUrl) {
        this.streamServiceUrl = streamServiceUrl;
    }
}
