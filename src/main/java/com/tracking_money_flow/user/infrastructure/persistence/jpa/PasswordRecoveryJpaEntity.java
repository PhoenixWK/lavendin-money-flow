package com.tracking_money_flow.user.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "password_recovery")
public class PasswordRecoveryJpaEntity {

    @Id
    @Column(name = "attached_id")
    private String attachedId;

    @Column(name = "requested_by", nullable = false)
    private String requestBy;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "requested_at")
    private Timestamp requestedAt;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    public PasswordRecoveryJpaEntity() {
    }

    public PasswordRecoveryJpaEntity(String attachedId, String requestBy, boolean isExpired, Timestamp requestedAt, Timestamp expiredAt) {
        this.attachedId = attachedId;
        this.requestBy = requestBy;
        this.isExpired = isExpired;
        this.requestedAt = requestedAt;
        this.expiredAt = expiredAt;
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
