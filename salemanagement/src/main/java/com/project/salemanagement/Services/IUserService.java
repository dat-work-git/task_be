package com.project.salemanagement.Services;

import com.project.salemanagement.dtos.UserDTO;
import com.project.salemanagement.models.User;

public interface IUserService {
    User createUser (UserDTO userDTO) throws Exception ;
    String login(String phone, String password, Long RoleId);
    Long deleteUser(Long id);
}
