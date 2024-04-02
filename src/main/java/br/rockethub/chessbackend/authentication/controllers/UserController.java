package br.rockethub.chessbackend.authentication.controllers;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.services.UserService;
import br.rockethub.utils.exceptions.EmailExistsException;
import br.rockethub.utils.exceptions.UsernameExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class UserController extends ApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    UserService userService;


    @PostMapping(value = "user/register")
    public ResponseEntity<Object> saveUser(@RequestBody User user, WebRequest request) {
        try {
            userService.register(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EmailExistsException | UsernameExistsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



}
