package com.web.mb.AiSearching.service;

import com.web.mb.AiSearching.repository.UserLikeToolsRepository;
import com.web.mb.AttractionSearching.model.UserLikeTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLikeToolsService {

    @Autowired
    private UserLikeToolsRepository userLikeToolsRepository;

    @Transactional
    public void addUserLike(String userId, String userSite) {
        try {
            System.out.println("Adding like for User ID: " + userId + ", Site: " + userSite);

            // USER_LIKE_TOOLS에서 기존 기록 확인
            List<UserLikeTools> existingLikes = userLikeToolsRepository.findByUserId(userId)
                    .stream()
                    .filter(like -> like.getUserSite().equals(userSite))
                    .collect(Collectors.toList());

            if (!existingLikes.isEmpty()) {
                UserLikeTools like = existingLikes.get(0);
                like.setUserLikes(like.getUserLikes() + 1);
                userLikeToolsRepository.save(like);
                System.out.println("Updated like for User ID: " + userId + ", Site: " + userSite);
            } else {
                UserLikeTools newLike = new UserLikeTools();
                newLike.setUserId(userId);
                newLike.setUserSite(userSite);
                newLike.setUserLikes(1);
                userLikeToolsRepository.save(newLike);
                System.out.println("Added new like for User ID: " + userId + ", Site: " + userSite);
            }
        } catch (Exception e) {
            System.err.println("Error in addUserLike: " + e.getMessage());
            throw e; // 예외를 다시 던져서 호출한 메서드에서 처리할 수 있도록 함
        }
    }

    public List<UserLikeTools> getUserLikedSites(String userId) {
        List<UserLikeTools> userLikes = userLikeToolsRepository.findByUserId(userId);
        System.out.println("User Likes Retrieved: " + userLikes);
        return userLikes != null ? userLikes : new ArrayList<>();
    }
}