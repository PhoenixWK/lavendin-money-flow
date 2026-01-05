package com.tracking_money_flow.user.domain;

import com.tracking_money_flow.user.domain.exception.InvalidDataException;

import java.sql.Timestamp;
import java.util.Random;

public class PasswordRecovery {
    private String attachedId;
    private String requestBy;
    private boolean isExpired;
    private Timestamp requestedAt;
    private Timestamp expiredAt;


    private PasswordRecovery(String attachedId, String requestBy, boolean isExpired, Timestamp requestedAt, Timestamp expiredAt) {
        this.attachedId = attachedId;
        this.requestBy = requestBy;
        this.isExpired = isExpired;
        this.requestedAt = requestedAt;
        this.expiredAt = expiredAt;
    }

    public static PasswordRecovery create(String requestBy, boolean isExpired, Timestamp requestedAt, Timestamp expiredAt) {

        if(requestBy.isEmpty()) {
            throw new InvalidDataException("requestBy cannot be null or empty");
        }else if(requestedAt == null) {
            throw new InvalidDataException("requestedAt cannot be null or empty");
        }

        String attachedId = generateAttachedId();

        return new PasswordRecovery(attachedId, requestBy, isExpired, requestedAt, expiredAt);
    }

    public static PasswordRecovery createWithAttachedId(String attachedId, String requestBy, boolean isExpired, Timestamp requestedAt, Timestamp expiredAt) {

        if(attachedId.isEmpty()) {
            throw new InvalidDataException("Attached id is not valid");
        }else if(requestBy.isEmpty()) {
            throw new InvalidDataException("requestBy cannot be null or empty");
        }else if(requestedAt == null || expiredAt == null) {
            throw new InvalidDataException("requestedAt or expireAt cannot be null or empty");
        }

        return new PasswordRecovery(attachedId, requestBy, isExpired, requestedAt, expiredAt);
    }

    public static String generateAttachedId() {
        Random random = new Random();
        StringBuilder attachedId = new StringBuilder();

        for(int i = 0; i < 6; i++) {
            attachedId.append(random.nextInt(10));
        }

        return attachedId.toString();
    }

    public String getAttachedId() {
        return attachedId;
    }

    public void setAttachedId(String attachedId) {
        this.attachedId = attachedId;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }
}
