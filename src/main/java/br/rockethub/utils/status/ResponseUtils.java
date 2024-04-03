package br.rockethub.utils.status;

import br.rockethub.chessbackend.authentication.data.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils<T> {

    public ResponseEntity<ResponseData<T>> createSuccessResponse(T data, StatusMessages message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(
                ResponseData.<T>builder()
                        .success(true)
                        .code(message.code)
                        .data(data)
                        .message(message).build(),headers, HttpStatus.OK);
    }


    public ResponseEntity<ResponseData<T>> createErrorResponse(StatusMessages message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(ResponseData.<T>builder()
                .message(message)
                .code(message.code)
                .success(false)
                .build(), headers,status);
    }
}
