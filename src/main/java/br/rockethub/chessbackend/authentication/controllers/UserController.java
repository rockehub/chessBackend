package br.rockethub.chessbackend.authentication.controllers;

import br.rockethub.chessbackend.authentication.data.ResponseData;
import br.rockethub.chessbackend.authentication.data.ResponseUserData;
import br.rockethub.chessbackend.authentication.exceptions.UserNotActivatedException;
import br.rockethub.chessbackend.authentication.services.UserService;
import br.rockethub.utils.status.ResponseUtils;
import br.rockethub.utils.status.StatusMessages;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SecurityRequirement(name = "bearer")
public class UserController extends ApiController {

    UserService userService;

    ConversionService conversionService;


    @GetMapping(value = "/user")
    public ResponseEntity<ResponseData<ResponseUserData>> getUser() {
        ResponseUtils<ResponseUserData> response = new ResponseUtils<>();
        try {
            return response.createSuccessResponse(
                    conversionService.convert(userService.getAuthenticatedUser(), ResponseUserData.class),
                    StatusMessages.USER_FOUND);
        } catch (UsernameNotFoundException e) {
            return response.createErrorResponse(StatusMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (UserNotActivatedException e) {
            return response.createErrorResponse(StatusMessages.USER_NOT_ACTIVATED, HttpStatus.CONFLICT);
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
