package br.rockethub.chessbackend.authentication.data;

import br.rockethub.utils.status.ResponseUtils;
import br.rockethub.utils.status.StatusMessages;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    private boolean success;
    private StatusMessages message;
    private T data;
    private int code;

    public static <T> ResponseData<T> empty() {
        return success(null);
    }

    public static <T> ResponseData<T> success(T data) {
        return ResponseData.<T>builder()
                .message(StatusMessages.SUCCESS)
                .data(data)
                .code(StatusMessages.SUCCESS.code)
                .success(true)
                .build();
    }

    public static <T> ResponseData<T> error() {
        return ResponseData.<T>builder()
                .message(StatusMessages.ERROR)
                .code(StatusMessages.ERROR.code)
                .success(false)
                .build();
    }
}