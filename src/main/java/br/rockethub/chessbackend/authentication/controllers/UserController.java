package br.rockethub.chessbackend.authentication.controllers;

import br.rockethub.chessbackend.authentication.data.ResponseUserData;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SecurityRequirement(name = "bearer")
public class UserController extends ApiController {

    UserService userService;

    ConversionService conversionService;


    @GetMapping(value = "/user")
    public ResponseEntity<ResponseUserData> getUser() {
        try {
            return ResponseEntity.ok(conversionService.convert(userService.getAuthenticatedUser(), ResponseUserData.class));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

}
