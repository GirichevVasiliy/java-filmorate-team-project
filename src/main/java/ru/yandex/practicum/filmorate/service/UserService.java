package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.dao.impl.FriendListDb;
import ru.yandex.practicum.filmorate.exception.FilmorateNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao storage;

    private final FriendListDb friendListDb;

    public User create(User user) {
        return storage.create(user);
    }

    public List<User> getAllUsers(){
        return storage.getAll();
    }

    public User getUserBy(Long id) {
        return storage.getBy(id).orElseThrow(() -> new FilmorateNotFoundException("Пользователь не найден."));
    }

    public User update(User user) {
        getUserBy(user.getId());
        return storage.update(user);
    }

    public void addFriend(Long userId, Long friendId) {
        friendListDb.addFriend(userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        getUserBy(userId);
        getUserBy(friendId);
        friendListDb.removeFriend(userId, friendId);
    }

    public List<User> getUserFriends(Long userId) {
        getUserBy(userId);
        return friendListDb.getFriends(userId);
    }

    public void approveFriend(Long userId, Long friendID) {
        friendListDb.approveFriend(userId, friendID);
    }

    public List<User> getMutualFriends(Long userId, Long otherUserId) {
        getUserBy(userId);
        getUserBy(otherUserId);
        return friendListDb.getCommonFriends(userId, otherUserId);
    }
}