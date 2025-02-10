package com.project.salemanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.salemanagement.models.Role;
import com.project.salemanagement.models.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String email;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Role role;
    public static List<UserResponse> fromListUser(List<User> userList){
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
    public static UserResponse fromUser(User user){
            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .role(user.getRole())
                    .build();

        return userResponse;
    }
}
