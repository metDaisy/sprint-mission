package com.sprint.mission.entity;

public enum EntityType {
    USER("user", "usr"),
    CHANNEL("channel", "chn"),
    MESSAGE("message", "msg");

    private final String type;
    private final String abbre;

    EntityType(String type, String abbre) {
        this.type = type;
        this.abbre = abbre;
    }

    public String type() {
        return type;
    }

    public String abbre() {
        return abbre;
    }

    @Override
    public String toString() {
        return type;
    }

    public boolean equals(EntityType other) {
        return type.equals(other.type);
    }
}
