package jovelAsirot.YourHomeDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jovelAsirot.YourHomeDB.entities.User;
import jovelAsirot.YourHomeDB.payloads.UserResponseDTO;
import jovelAsirot.YourHomeDB.services.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/profile/avatar/upload/{userId}")
    public UserResponseDTO uploadAvatar(@PathVariable Long userId, @RequestParam("image") MultipartFile image) throws IOException {
        this.userService.uploadProfileImage(userId, image);
        return new UserResponseDTO(userId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUsers(page, size, sortBy);
    }

    @GetMapping("/me")
    public User getMeProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PutMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@PathVariable Long userId, @RequestBody User userBody) {
        return this.userService.updateById(userId, userBody);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteById(userId);
    }

}
