package com.moviesandchill.portalbackendservice.service.user;

import com.moviesandchill.portalbackendservice.dto.user.achievement.AchievementDto;

import java.util.List;

public interface UserAchievementService {
    List<AchievementDto> getAllAchievements(long userId);

    boolean addAchievement(long userId, long achievementId);

    boolean deleteAllAchievements(long userId);

    boolean deleteAchievement(long userId, long achievementId);
}
