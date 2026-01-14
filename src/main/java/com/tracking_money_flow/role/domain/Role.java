package com.tracking_money_flow.role.domain;


public class Role {
    private Long id;
    private String name;

    private Role(Long id, String name) {
        this.id = id;
        this.name = name.toUpperCase();
    }

    public static Role register(String name) {
        if(name.isEmpty()) return null;
        return new Role(null, name);
    }

    public static Role reconstruct(Long id, String name) {
        return new Role(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isNew() {
        return id == null;
    }

    public void setName(String name) {
        this.name = name;
    }
}
