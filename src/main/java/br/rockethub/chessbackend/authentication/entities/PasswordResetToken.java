package br.rockethub.chessbackend.authentication.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class PasswordResetToken implements Serializable {

    @Id
    @UuidGenerator
    private UUID id;

    private String password_reset_token;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Temporal(TemporalType.DATE)
    private Date expiry_date;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(final String password_reset_token, Date expiry_date) {
        super();
        this.password_reset_token = password_reset_token;
        this.expiry_date = expiry_date;
    }


    public PasswordResetToken(final User user, final String password_reset_token, Date expiry_date) {
        super();
        this.user = user;
        this.password_reset_token = password_reset_token;
        this.expiry_date = expiry_date;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPasswordResetToken() {
        return password_reset_token;
    }

    public void setPasswordResetToken(String password_reset_token) {
        this.password_reset_token = password_reset_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiry_date;
    }

    public void setExpiryDate(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void updateToken(String token, Date expiry_date) {
        this.password_reset_token = token;
        this.expiry_date = expiry_date;
    }
}
