package br.rockethub.chessbackend.authentication.events;

import br.rockethub.chessbackend.authentication.entities.User;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

public class OnRegistrationCompleteEvent extends ApplicationEvent implements Serializable {


    private User user;

    public OnRegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
