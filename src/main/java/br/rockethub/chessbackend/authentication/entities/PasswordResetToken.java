package br.rockethub.chessbackend.authentication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class PasswordResetToken implements Serializable {

    @Id
    @UuidGenerator
    private UUID id;

    private int passwordResetToken;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry_date;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(final int passwordResetToken, Date expiry_date) {
        super();
        this.passwordResetToken = passwordResetToken;
        this.expiry_date = expiry_date;
    }


    public PasswordResetToken(final User user, final int passwordResetToken, Date expiry_date) {
        super();
        this.user = user;
        this.passwordResetToken = passwordResetToken;
        this.expiry_date = expiry_date;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(int passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
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

    public void updateToken(int token, Date expiry_date) {
        this.passwordResetToken = token;
        this.expiry_date = expiry_date;
    }
}
