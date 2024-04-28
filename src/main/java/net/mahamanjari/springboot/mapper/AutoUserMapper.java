package net.mahamanjari.springboot.mapper;

import net.mahamanjari.springboot.dto.UserDto;
import net.mahamanjari.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);
    //Mapstruct will create method at compilation time
    //@Mapping(source = "email",target = "emailAddress") // When field names are different using Mapping
    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);

    //Note: both classes should have same field names, or else mapping fails
}
