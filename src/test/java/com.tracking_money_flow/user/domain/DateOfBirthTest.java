package com.tracking_money_flow.user.domain;

import org.testng.annotations.Test;


import java.time.LocalDate;


public class DateOfBirthTest {

    @Test
    void should_return_successful_when_date_of_birth_is_after_the_current_date() {
        // Test implementation goes here
        LocalDate customDate = LocalDate.of(2026, 1, 1);
        LocalDate today = LocalDate.now();
        assert customDate.isAfter(today);
    }

    @Test
    void should_return_successful_when_date_of_birth_is_before_the_current_date() {
        // Test implementation goes here
        LocalDate customDate = LocalDate.of(1990, 5, 15);
        LocalDate today = LocalDate.now();
        assert customDate.isBefore(today);
    }
}
