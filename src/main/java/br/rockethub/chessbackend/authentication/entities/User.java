package br.rockethub.chessbackend.authentication.entities;

import br.rockethub.chessbackend.ChessBackendApplication;
import br.rockethub.utils.validations.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private String surname;
    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    private String password;

    @ValidEmail
    @NotEmpty
    @NonNull
    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date created_at;

    @Temporal(TemporalType.DATE)
    private Date activated_at;

    private boolean active;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private VerificationToken token;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private PasswordResetToken password_reset_token;


    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getActivatedAt() {
        return activated_at;
    }

    public void setActivatedAt(Date activated_at) {
        this.activated_at = activated_at;
    }

    public boolean isActive() {
        return active;
    }

    public void setIsActive(boolean is_active) {
        this.active = is_active;
    }

    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }

    public PasswordResetToken getPasswordResetToken() {
        return password_reset_token;
    }

    public void setPasswordResetToken(PasswordResetToken password_reset_token) {
        this.password_reset_token = password_reset_token;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
