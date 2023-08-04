package com.jca.thedoor.payload;

public class DeleteCoworkersRequest {
    private String[] coworkers;
    private String user;
    private String group;

    public String[] getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(String[] coworkers) {
        this.coworkers = coworkers;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
