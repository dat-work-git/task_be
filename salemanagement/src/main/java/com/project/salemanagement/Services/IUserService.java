package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.UserChangePassDTO;
import com.project.salemanagement.dtos.UserDTO;
import com.project.salemanagement.dtos.UserUpdateDTO;
import com.project.salemanagement.models.User;
import com.project.salemanagement.response.UserResponse;

import java.util.List;

public interface IUserService {
    User createUser (UserDTO userDTO) throws Exception ;
    String login(String phone, String password, Long RoleId) throws Exception;
    User updateUser(UserUpdateDTO userUpdateDTO, String userEmail)throws Exception;
    List<User> getUserList() ;
    User getUserDetails(String token) throws Exception;
    User changePassword(UserChangePassDTO userChangePassDTO, String token) throws Exception;
    Boolean deleteUser( String userEmail) throws Exception;
}
