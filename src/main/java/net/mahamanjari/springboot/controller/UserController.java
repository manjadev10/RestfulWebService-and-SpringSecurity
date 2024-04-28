package net.mahamanjari.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.mahamanjari.springboot.dto.UserDto;
import net.mahamanjari.springboot.entity.User;
import net.mahamanjari.springboot.exception.ErrorDetails;
import net.mahamanjari.springboot.exception.ResourceNotFoundException;
import net.mahamanjari.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Crud Rest API User Resource",
        description = "Performs CRUD operation on User Data"
)
@Controller
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create User Rest API",
            description = "Create User Rest API is used to save user in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        UserDto savedUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get User Rest API By Giving ID as Paramter",
            description = "Get User Rest API is used to retrieve user details in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<UserDto> getUserById(@Param("id")Long id){
        UserDto userReturned = userService.getUserById(id);
        return ResponseEntity.ok(userReturned);
    }

    @Operation(
            summary = "Get User Rest API By Giving ID in Path",
            description = "Get User Rest API is used to retrieve user details in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserByIdPath(@PathVariable("id") Long id){
        UserDto userReturned = userService.getUserById(id);
        return ResponseEntity.ok(userReturned);
    }

    @Operation(
            summary = "Get ALL User Rest API By Giving ID as Paramter",
            description = "Get ALL User Rest API is used to retrieve all user details in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @Operation(
            summary = "Update User Rest API",
            description = "Update User Rest API is used to update specific user in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id")Long id, @RequestBody @Valid UserDto userDto){
        userDto.setId(id);
        UserDto uUpdated =userService.updateUser(userDto);
        return ResponseEntity.ok(uUpdated);
    }

    @Operation(
            summary = "Delete User Rest API",
            description = "Delete User Rest API is used to delete specific user in database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted!",HttpStatus.OK);
    }
}
