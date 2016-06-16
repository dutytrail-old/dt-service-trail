package com.dutytrail.service.trail.entity;

public enum Status {

    CREATED("created"),
    DONE("done"),
    DELETED("deleted");

    private String description;

    Status(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public static Status get(String description) {
        for(Status s : values()) {
            if(s.description.equals(description)) return s;
        }
        return null;
    }
}
