package com.tracking_money_flow.user.domain;

import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserNameTest {

    /*
        Username can contains lowercase, uppercase letters and digits only
    */
    boolean validation(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(username).matches();
    }

    @Test
    void testUserNameValidation1() {
        assertFalse(validation(null));
    }

    @Test
    void testUserNameValidation2() {
        assertFalse(validation(""));
        assertFalse(validation("    "));
    }

    @Test
    void testUserNameValidation3() {
        assertTrue(validation("username1"));
    }

    @Test
    void testUserNameValidation4() {
        assertTrue(validation("USERNAME1"));
    }

    @Test
    void testUserNameValidation5() {
        assertTrue(validation("Username"));
    }

    @Test
    void testUserNameValidation6() {
        assertTrue(validation("Username1"));
    }

    @Test
    void testUserNameValidation7() {
        assertFalse(validation("User@name1"));
    }
}
