package net.mahamanjari.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.mahamanjari.springboot.dto.UserDto;
import net.mahamanjari.springboot.entity.User;
import net.mahamanjari.springboot.exception.EmailAlreadyExistsException;
import net.mahamanjari.springboot.exception.ResourceNotFoundException;
import net.mahamanjari.springboot.mapper.AutoUserMapper;
import net.mahamanjari.springboot.repository.UserRepository;
import net.mahamanjari.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDTO) {
        //Convert User Dto to User JPA entity
        //User user = UserMapper.mapToUser(userDTO);

        //User user = modelMapper.map(userDTO,User.class);

        Optional<User> optionalUser = userRepository.findByMail(userDTO.getMail());

        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for a User");
        }

        User user = AutoUserMapper.MAPPER.mapToUser(userDTO);

        User savedUser = userRepository.save(user);

        //Convert User JPA entity to UserDto
        //UserDto savedUserDto = UserMapper.mapToUserDTO(savedUser);

        //UserDto savedUserDto = modelMapper.map(savedUser,UserDto.class);

        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        //return UserMapper.mapToUserDTO(userReturned);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

//        List<UserDto> userDtos = new ArrayList<>();
//        for (User user: users) {
//            userDtos.add(UserMapper.mapToUserDTO(user));
//        }
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User existing = userRepository.findById((userDto.getId())).orElseThrow(()->new ResourceNotFoundException("User","id", userDto.getId()));
        existing.setFirstName(userDto.getFirstName());
        existing.setLastName(userDto.getLastName());
        existing.setMail(userDto.getMail());
        User updatedUser = userRepository.save(existing);
        //return UserMapper.mapToUserDTO(updatedUser);
        return modelMapper.map(updatedUser,UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {

        User existingUser = userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User","id",userId)
        );
        userRepository.deleteById(userId);
    }


}
