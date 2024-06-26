package br.rockethub.chessbackend.authentication.controllers.advices;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        JSONObject json = new JSONObject();
        json.put("error", e.getMessage());
        logger.error("Error: ", e);

        return new ResponseEntity<>(json.toString(),headers, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<Object> handleForbiddenException(HttpMessageNotReadableException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        JSONObject json = new JSONObject();

        if (e.getCause() != null) {
            json.put("error", e.getCause().getMessage());
        } else {
            json.put("error", e.getMessage());
        }

        json.put("error", e.getMessage());
        return new ResponseEntity<>(json.toString(),headers, org.springframework.http.HttpStatus.FORBIDDEN);
    }
}
