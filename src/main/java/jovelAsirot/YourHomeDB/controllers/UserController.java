package jovelAsirot.YourHomeDB.controllers;

import jovelAsirot.YourHomeDB.entities.User;
import jovelAsirot.YourHomeDB.payloads.UserResponseDTO;
import jovelAsirot.YourHomeDB.payloads.UserResponseFavoriteDTO;
import jovelAsirot.YourHomeDB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/me/favorites/properties/{propertyId}")
    public String updateFavoriteProperty(@PathVariable Long propertyId, @AuthenticationPrincipal User currentUser) {
        boolean propertyAdded = userService.updateFavoriteProperty(currentUser.getId(), propertyId);
        return propertyAdded ? "Property added to favorites successfully" : "Property removed from favorites successfully";
    }

    @PostMapping("/me/avatar/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO uploadAvatar(@RequestParam("image") MultipartFile image,
                                        @AuthenticationPrincipal User currentUser) throws IOException {
        this.userService.uploadProfileImage(currentUser.getId(), image);
        return new UserResponseDTO(currentUser.getId());
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
    @PreAuthorize("hasAnyAuthorities('ADMIN','USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteById(userId);
    }

    @GetMapping("/me/favorites")
    public UserResponseFavoriteDTO getFavoriteProperties(@AuthenticationPrincipal User currentUser) {
        return userService.getUserFavoriteProperties(currentUser.getId());
    }

}
