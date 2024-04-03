package br.rockethub.chessbackend.authentication.controllers;

import br.rockethub.chessbackend.authentication.data.PasswordChangeForm;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.exceptions.*;
import br.rockethub.chessbackend.authentication.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends ApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    UserService userService;

    @PostMapping(value = "user/register")
    public ResponseEntity<Object> saveUser(@RequestBody User user) {
        try {
            userService.register(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EmailExistsException | UsernameExistsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }


    @GetMapping(value = "user/confirm/{token}")
    public ResponseEntity<Object> confirmRegistration(@PathVariable("token") int token) {
        try {
            userService.verifyToken(token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidTokenException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ExpiredTokenException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "user/resend/{email:.+}")
    public ResponseEntity<Object> resendVerificationToken(@PathVariable("email") String email){
        try{
            userService.resendVerificationToken(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmailNotExistException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "user/password/change/{email:.+}")
    public ResponseEntity<Object> changePassword(@PathVariable("email") String email){
        try{
            userService.resetPasswordByEmail(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmailNotExistException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "user/password/change/{token}")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody PasswordChangeForm passwordChangeForm, @PathVariable("token") int token){
        try{
            userService.verifyResetPasswordToken(token, passwordChangeForm);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidTokenException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ExpiredTokenException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
