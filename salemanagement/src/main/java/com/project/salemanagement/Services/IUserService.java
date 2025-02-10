package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.UserDTO;
import com.project.salemanagement.models.User;
import com.project.salemanagement.response.UserResponse;

import java.util.List;

public interface IUserService {
    User createUser (UserDTO userDTO) throws Exception ;
    String login(String phone, String password, Long RoleId);
    List<User> getUserList() ;
    Long deleteUser(Long id);
    User getUserDetails(String token) throws Exception;
}
