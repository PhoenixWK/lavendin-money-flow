package com.tracking_money_flow.role.infrastructure.api.request;

public record UpdateRowRequest(
        Long id,
        String name
) {
}
