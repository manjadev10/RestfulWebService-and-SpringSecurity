package net.mahamanjari.springboot.service;

import net.mahamanjari.springboot.dto.UserDto;
import net.mahamanjari.springboot.entity.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto userDto);

    void deleteUser(Long userId);

}
