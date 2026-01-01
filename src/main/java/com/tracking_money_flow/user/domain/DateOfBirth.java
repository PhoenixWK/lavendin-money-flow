package com.tracking_money_flow.user.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateOfBirth {
    private Date dateOfBirth;

    public DateOfBirth(String newDateOfBirth) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfBirth;

        try {
           dateOfBirth = formatter.parse(newDateOfBirth);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date of birth format. Expected format: yyyy-MM-dd");
        }

        if(dateOfBirth == null || dateOfBirth.after(new Date())) {
            throw new IllegalArgumentException("Date of birth cannot be null or in the future");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public Date value() {
        return dateOfBirth;
    }

    public int hashCode() {
        return dateOfBirth.hashCode();
    }
}
