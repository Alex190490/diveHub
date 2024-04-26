package alexDavid.controller;

import alexDavid.Contact.MessageService;
import alexDavid.dtos.UserDto.UserRequestDto;
import alexDavid.dtos.UserDto.UserResponseDto;
import alexDavid.mappers.UserMapper;
import alexDavid.service.UserDetailsServiceImpl;
import alexDavid.service.WishListService.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/divehub/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserDetailsServiceImpl userService;
    private final WishListService wishListService;
    private final MessageService messageService;
    //private final ProductCart productCart;
    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser(
    ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.getAllUsers()));
    }


    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUser(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.loadUserByUserEmail(email)));
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String email,
            @RequestBody UserRequestDto user
    ) {
        return ResponseEntity.ok(userMapper.toResponse(userService.updateUser(email, userMapper.toModel(user))));
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(
            @PathVariable String email
    ) {
        //productCart.delete(email);
        messageService.deleteAllMessages(email);
        wishListService.cleanWishList(email);
        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


