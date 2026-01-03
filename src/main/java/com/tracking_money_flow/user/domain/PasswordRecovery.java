package com.tracking_money_flow.user.domain;

import java.util.Random;

public class PasswordRecovery {
    private String attachedId;
    private String requestBy;
    private String isExpired;
    private String requestedAt;
    private String expiredAt;


    private PasswordRecovery(String attachedId, String requestBy, String isExpired, String requestedAt, String expiredAt) {
        this.attachedId = attachedId;
        this.requestBy = requestBy;
        this.isExpired = isExpired;
        this.requestedAt = requestedAt;
        this.expiredAt = expiredAt;
    }

    public static PasswordRecovery create(String requestBy, String isExpired, String requestedAt, String expiredAt) {

        if(requestBy == null || requestBy.isEmpty()) {
            throw new IllegalArgumentException("requestBy cannot be null or empty");
        }else if(isExpired == null || isExpired.isEmpty()) {
            throw new IllegalArgumentException("isExpired cannot be null or empty");
        }else if(requestedAt == null || requestedAt.isEmpty()) {
            throw new IllegalArgumentException("requestedAt cannot be null or empty");
        }

        String attachedId = generateAttachedId();

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

    public String getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(String isExpired) {
        this.isExpired = isExpired;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }
}
