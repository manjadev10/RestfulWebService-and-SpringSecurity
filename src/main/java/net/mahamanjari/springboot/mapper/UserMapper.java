package net.mahamanjari.springboot.mapper;

import net.mahamanjari.springboot.dto.UserDto;
import net.mahamanjari.springboot.entity.User;

public class UserMapper {

    //Convert UserDto to JPA Entity
    public static UserDto mapToUserDTO(User user){

        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMail()
        );

        return userDto;
    }

    //Convert User JPA Entity to UserDto
    public static User mapToUser(UserDto userDto){
        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getMail()
        );
        return user;
    }

}
