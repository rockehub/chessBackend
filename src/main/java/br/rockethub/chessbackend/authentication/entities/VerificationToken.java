package br.rockethub.chessbackend.authentication.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class VerificationToken implements Serializable {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @UuidGenerator
    private UUID id;


    private int token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, mappedBy = "token")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiry_date;


    public void updateToken(int token, Date expiry_date) {
        this.token = token;
        this.expiry_date = expiry_date;
    }

    public VerificationToken() {
        super();
    }

    public VerificationToken(final int token, Date expiry_date) {
        super();
        this.token = token;
        this.expiry_date = expiry_date;
    }

    public VerificationToken(final User user, final int token, Date expiry_date) {
        super();
        this.user = user;
        this.token = token;
        this.expiry_date = expiry_date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
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

    public static int getExpiration() {
        return EXPIRATION;
    }
}
