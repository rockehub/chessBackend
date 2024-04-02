package br.rockethub.chessbackend.authentication.data;

import br.rockethub.chessbackend.authentication.entities.User;
import br.rockethub.chessbackend.authentication.events.OnRegistrationCompleteEvent;

import java.io.Serializable;

public class RegistrationTokenData implements Serializable {

    OnRegistrationCompleteEvent event;

    User user;

    int token;


    public RegistrationTokenData(OnRegistrationCompleteEvent event, User user, int token) {
        super();
        this.event = event;
        this.user = user;
        this.token = token;
    }



    public OnRegistrationCompleteEvent getEvent() {
        return event;
    }

    public void setEvent(OnRegistrationCompleteEvent event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
