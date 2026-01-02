package com.tracking_money_flow.user.domain;

import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    @Test
    void testUserCreation1() {
        UserId id = UserId.create(UserId.generate());
        Email email = new Email("johndoe123@gmail.com");
        UserName name = new UserName("johndoe");
        Password password = Password.hashed("securePassword!23");

         // January 1, 2013

        User user = User.reconstruct(id, email, password, name, AuthProvider.EMAIL_AND_PASSWORD, new DateOfBirth("1990/10/01"), UserStatus.ACTIVE,  LocalDateTime.now(), LocalDateTime.now());

        assertNotNull(user);
        assertEquals("johndoe123@gmail.com", user.getEmail().value());
        assertEquals("johndoe", user.getUsername().value());
        assertEquals("securePassword!23", user.getPassword().value());
    }

    //test invalid email
    @Test
    void testUserCreation2() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserId id = UserId.create(UserId.generate());
            Email email = new Email("johndoe123@gmail");
            UserName name = new UserName("johndoe");
            Password password = Password.hashed("securePassword!23");


            User.reconstruct(id, email, password, name, AuthProvider.EMAIL_AND_PASSWORD, new DateOfBirth("1990/10/01"), UserStatus.ACTIVE,  LocalDateTime.now(), LocalDateTime.now());
        });
    }

    //test invalid password
    @Test
    void testUserCreation3() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserId id = UserId.create(UserId.generate());
            Email email = new Email("johndoe123@gmail.com");
            UserName name = new UserName("johndoe");
            Password password = Password.raw("1");

            User.reconstruct(id, email, password, name, AuthProvider.EMAIL_AND_PASSWORD, new DateOfBirth("1990/10/01"), UserStatus.ACTIVE,  LocalDateTime.now(), LocalDateTime.now());
        });
    }

    //test invalid username
    @Test
    void testUserCreation4() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserId id = UserId.create(UserId.generate());
            Email email = new Email("johndoe123@gmail.com");
            UserName name = new UserName("");
            Password password = Password.hashed("johndoe@123");

            User.reconstruct(id, email, password, name, AuthProvider.EMAIL_AND_PASSWORD, new DateOfBirth("1990/10/01"), UserStatus.ACTIVE,  LocalDateTime.now(), LocalDateTime.now());
        });
    }

    //test invalid date of birth (future date)
    @Test
    void testUserCreation5() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserId id = UserId.create(UserId.generate());
            Email email = new Email("johndoe123@gmail.com");
            UserName name = new UserName("JohnDoe");
            Password password = Password.hashed("johndoe@123");;

            User.reconstruct(id, email, password, name, AuthProvider.EMAIL_AND_PASSWORD, new DateOfBirth("1990/10/01"), UserStatus.ACTIVE,  LocalDateTime.now(), LocalDateTime.now());
        });
    }
}
