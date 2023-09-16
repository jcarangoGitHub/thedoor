package com.jca.thedoor.payload;

public class DeleteObjectsRequest {
    private String[] objects;
    private String user;
    private String group;

    public String[] getObjects() {
        return objects;
    }

    public void setObjects(String[] objects) {
        this.objects = objects;
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
