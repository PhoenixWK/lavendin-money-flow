package com.tracking_money_flow.user.domain;

import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordTest {

    /*
        * Password must be at least 10 characters long.
        * It must contain at least one lowercase letter (a-z).
        * It must contain at least one uppercase letter (A-Z).
        * It must contain at least one digit (0-9).
        * It must contain at least one special character from the set: !@#$%
     */
    static boolean passwordValidator(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*_])[A-Za-z\\\\d!@#$%&*_]{10,}$";
        Pattern pattern = Pattern.compile(regex);

        return (password == null || (password.length() < 10 && !pattern.matcher(password).matches()));
    }

    //True means invalid password
    @Test
    void testPasswordValidation1() {
        assertTrue(PasswordTest.passwordValidator("123456789"));
    }

    @Test
    void testPasswordValidation2() {
        assertFalse(PasswordTest.passwordValidator("1234567890"));
    }

    @Test
    void testPasswordValidation3() {
        assertFalse(PasswordTest.passwordValidator("user@gmail.com"));
    }

    @Test
    void testPasswordValidation4() {
        assertFalse(PasswordTest.passwordValidator("verylongemail@gmail.com"));
    }

    @Test
    void testPasswordValidation5() {
        assertFalse(PasswordTest.passwordValidator("StrongPass123"));
    }

    @Test
    void testPasswordValidation6() {
        assertFalse(PasswordTest.passwordValidator("StrongPass@123"));
    }

    @Test
    void testPasswordValidation7() {
        assertFalse(PasswordTest.passwordValidator("StrongPass@123!"));
    }

}
