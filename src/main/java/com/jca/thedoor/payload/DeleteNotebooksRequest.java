package com.jca.thedoor.payload;

public class DeleteNotebooksRequest {
    private String[] names;
    private String user;
    
    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

