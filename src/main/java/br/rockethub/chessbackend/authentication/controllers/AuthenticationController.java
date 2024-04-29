package br.rockethub.chessbackend.authentication.controllers;

import br.rockethub.chessbackend.authentication.data.PasswordChangeForm;
import br.rockethub.chessbackend.authentication.data.ResponseData;
import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.exceptions.*;
import br.rockethub.chessbackend.authentication.services.AuthenticationService;
import br.rockethub.utils.status.ResponseUtils;
import br.rockethub.utils.status.StatusMessages;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController extends ApiController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    AuthenticationService authenticationService;

    @PostMapping(value = "authentication/register")
    public ResponseEntity<ResponseData<Object>> saveUser(@RequestBody User user) {
        ResponseUtils<Object> response = new ResponseUtils<>();

        try {
            authenticationService.register(user);
            return response.createSuccessResponse(null, StatusMessages.USER_REGISTERED);
        } catch (EmailExistsException | UsernameExistsException e) {
            logger.error(e.getMessage());
            return response.createErrorResponse(StatusMessages.USER_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }

    }


    @GetMapping(value = "authentication/confirm/{token}")
    public ResponseEntity<Object> confirmRegistration(@PathVariable("token") int token) {
        try {
            authenticationService.verifyToken(token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidTokenException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (ExpiredTokenException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "authentication/resend/{email:.+}")
    public ResponseEntity<Object> resendVerificationToken(@PathVariable("email") String email){
        try{
            authenticationService.resendVerificationToken(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmailNotExistException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "authentication/password/change/{email:.+}")
    public ResponseEntity<Object> changePassword(@PathVariable("email") String email){
        try{
            authenticationService.resetPasswordByEmail(email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmailNotExistException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "authentication/password/change/{token}")
    public ResponseEntity<Object> resetPassword(@Valid @RequestBody PasswordChangeForm passwordChangeForm, @PathVariable("token") int token){
        try{
            authenticationService.verifyResetPasswordToken(token, passwordChangeForm);
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
    public void setUserService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


}
