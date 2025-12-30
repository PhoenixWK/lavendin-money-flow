package com.tracking_money_flow.user.domain;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailTest {

    //Common standard
    @Test
    void test_email_01() {
        String emailStr = "user@gmail.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //There is a dot (.).
    @Test
    void test_email_02() {
        String emailStr = "john.doe@yahoo.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //There is an underscore (_).
    @Test
    void test_email_03() {
        String emailStr = "user_name@outlook.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //There is a hyphen (-).
    @Test
    void test_email_04() {
        String emailStr = "user-name@mail.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //There is a plus (+).
    @Test
    void test_email_05() {
        String emailStr = "user+test@gmail.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //Internal email
    @Test
    void test_email_06() {
        String emailStr = "admin@company.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //The domain has -
    @Test
    void test_email_07() {
        String emailStr = "support@my-company.io";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //Subdomain
    @Test
    void test_email_08() {
        String emailStr = "dev.team@corp.example.com";
        assertTrue(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //Missing @ symbol
    @Test
    void test_email_09() {
        String emailStr = "usergmail.com";
        assertFalse(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //Missing domain
    @Test
    void test_email_10() {
        String emailStr = "user@";
        assertFalse(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //Missing local-part
    @Test
    void test_email_11() {
        String emailStr = "@gmail.com";
        assertFalse(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }

    //! is not allowed
    @Test
    void test_email_12() {
        String emailStr = "@user@gmail";
        assertFalse(emailStr.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));
    }
}
