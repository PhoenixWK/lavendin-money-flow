package com.tracking_money_flow.user.domain;

import java.time.LocalDateTime;


public class User {

    private final UserId id;
    private final Email email;
    private UserName username;
    private Password password;
    private UserStatus status;
    private DateOfBirth dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(UserId id, Email email, Password password, UserName username, DateOfBirth dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.status = UserStatus.ACTIVE;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User register(Email email, Password password, UserName username, DateOfBirth dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(UserId.create(UserId.generate()),  email, password, username, dateOfBirth, createdAt, updatedAt);
    }

    public void changePassword(Password newPassword) {
        this.password = newPassword;
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    // getters only, no random setter

    public UserId getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserName getUsername() {
        return username;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

