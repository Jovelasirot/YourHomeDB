package jovelAsirot.YourHomeDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jovelAsirot.YourHomeDB.exceptions.BadRequestException;
import jovelAsirot.YourHomeDB.payloads.UserDTO;
import jovelAsirot.YourHomeDB.payloads.UserLoginDTO;
import jovelAsirot.YourHomeDB.payloads.UserLoginResponseDTO;
import jovelAsirot.YourHomeDB.payloads.UserResponseDTO;
import jovelAsirot.YourHomeDB.services.AuthService;
import jovelAsirot.YourHomeDB.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@RequestBody @Validated UserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return new UserResponseDTO(this.userService.save(body).getId());
        }
    }

    @PostMapping("/login")
    public UserLoginResponseDTO loginUser(@RequestBody @Validated UserLoginDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new UserLoginResponseDTO(this.authService.authenticateUserAndGenerateToken(body));
    }

}
