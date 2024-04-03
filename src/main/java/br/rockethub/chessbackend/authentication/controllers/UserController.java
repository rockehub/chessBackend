package br.rockethub.chessbackend.authentication.controllers;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SecurityRequirement(name = "bearer")
public class UserController extends ApiController {

    UserService userService;


    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser() {
        try {
            return ResponseEntity.ok(userService.getAuthenticatedUser());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
