package com.jca.thedoor.security.jwt.logout;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

// Lombok annotations
public class OnUserLogoutSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private final String userEmail;
    private final String token;
    private final transient LogOutRequest logOutRequest;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(Object source, String userEmail, String token, LogOutRequest logOutRequest, Date eventTime) {
        super(source);
        this.userEmail = userEmail;
        this.token = token;
        this.logOutRequest = logOutRequest;
        this.eventTime = eventTime;
    }
    // All Arguments Constructor with modifications


    public String getUserEmail() {
        return userEmail;
    }

    public String getToken() {
        return token;
    }

    public Date getEventTime() {
        return eventTime;
    }
}
