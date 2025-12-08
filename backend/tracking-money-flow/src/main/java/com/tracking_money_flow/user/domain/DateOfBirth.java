package com.tracking_money_flow.user.domain;

import java.util.Date;

public class DateOfBirth {
    private Date dateOfBirth;

    public DateOfBirth(Date dateOfBirth) {
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
