package br.rockethub.chessbackend.authentication.converters;

import br.rockethub.chessbackend.authentication.data.ResponseUserData;
import br.rockethub.chessbackend.authentication.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class UserToResponseUserDataConverter implements Converter<User, ResponseUserData> {

    @Override
    public ResponseUserData convert(@NonNull User source) {
        ResponseUserData responseUserData = new ResponseUserData();
        responseUserData.setUsername(source.getUsername());
        responseUserData.setEmail(source.getEmail());
        responseUserData.setName(source.getName());
        responseUserData.setSurname(source.getSurname());
        return responseUserData;
    }


}
