package com.project.salemanagement.response;

import com.project.salemanagement.models.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private String email;
    private String name;

    public static List<UserResponse> fromUser(List<User> userList){
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList){
        UserResponse userResponse = UserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
        userResponseList.add(userResponse);
        }
        return userResponseList;
    }
}
