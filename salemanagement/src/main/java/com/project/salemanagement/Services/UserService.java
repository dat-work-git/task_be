package com.project.salemanagement.Services;

import com.project.salemanagement.Repositories.RoleRepo;
import com.project.salemanagement.Repositories.UserRepo;
import com.project.salemanagement.components.JwtTokenUtil;
import com.project.salemanagement.dtos.UserChangePassDTO;
import com.project.salemanagement.dtos.UserDTO;
import com.project.salemanagement.models.Role;
import com.project.salemanagement.models.User;
import com.project.salemanagement.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        if (!userRepo.existsByEmail(userDTO.getEmail())) {
            Role role = roleRepo.findById(userDTO.getRoleId()).orElseThrow(
                    () -> new IllegalArgumentException("không tìm thấy role"));
//            if (role.getName().equals(Role.ADMIN)) {
//                throw new InvalidParameterException("mày không thể tạo acc admin được thằng nhóc à!");
//
//            }
            User user = User.builder()
                    .name(userDTO.getFullName())
                    .email(userDTO.getEmail())
                    .phone(userDTO.getPhone())
                    .address(userDTO.getAddress())
                    .role(role)
                    .build();
            //if(userDTO.getFacebookAccountID() == 0 && userDTO.getGoogleAccountID()==0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            //}
            userRepo.save(user);
            return user;
        }
        {
            throw new IllegalArgumentException("User was create!");
        }
    }

    @Override
    public String login(String email, String password, Long RoleId) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty()){
            throw new IllegalArgumentException("Invalid phone number/password");
        }else {
            System.out.println("Have User");
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Invalid phone number/password");
        }
        if (user.getRole().getId() != RoleId){
            throw new IllegalArgumentException("Wrong Role!");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, password, user.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtTokenUtil.generateToken(user);
    }

    @Override
    public List<User> getUserList() {
            try {
                List<User> userList = userRepo.findAll();
                if (!userList.isEmpty()){
                    return  userRepo.findAll();
                }else {
                    throw new RuntimeException("User list empty!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Long deleteUser(Long id) {
        return 0L;
    }

    @Override
    public User getUserDetails(String token) throws Exception {
        if(jwtTokenUtil.isTokenExpired(token)){
            throw new Exception("Token is expired");
        }
        String email = jwtTokenUtil.extractEmail(token);
        User user = userRepo.findByEmail(email).orElseThrow(
                () ->  new EntityNotFoundException("User not found"));
        return user;    }

    @Override
    public User changePassword(UserChangePassDTO userChangePassDTO, String token) throws Exception {
        if(!userChangePassDTO.getRetypePassword().equals( userChangePassDTO.getNewPassword())){
            throw new Exception("Retype is not valid!");
        }
        if(jwtTokenUtil.isTokenExpired(token)){
            throw new Exception("Token is expired");
        }
        String email = jwtTokenUtil.extractEmail(token);
        User user = userRepo.findByEmail(email).orElseThrow(
                () ->  new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(userChangePassDTO.getOldPassword(), user.getPassword())){
            throw new Exception("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(userChangePassDTO.getNewPassword()));
        userRepo.save(user);
        return user;
    }

}
