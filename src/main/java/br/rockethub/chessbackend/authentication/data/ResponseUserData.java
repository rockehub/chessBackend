package br.rockethub.chessbackend.authentication.data;


import jakarta.validation.constraints.NotBlank;

public class ResponseUserData {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
