package xyz.vkislitsin.backend.services;

import java.util.List;

import xyz.vkislitsin.backend.entities.User;
import xyz.vkislitsin.backend.models.UserModel;

public interface IUserService {
    List<UserModel> getAllUsers(boolean sort) throws Exception;
    UserModel getUserById(Long userId) throws Exception;
    UserModel addNewUser(String userName) throws Exception;
    User getUserByName(String userName);
}
