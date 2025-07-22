package com.jca.thedoor.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private List<String> securedPaths;
    private String frontendOrigin;

    // Getters y Setters
    public List<String> getSecuredPaths() {
        return securedPaths;
    }

    public void setSecuredPaths(List<String> securedPaths) {
        this.securedPaths = securedPaths;
    }

    public String getFrontendOrigin() {
        return frontendOrigin;
    }

    public void setFrontendOrigin(String frontendOrigin) {
        this.frontendOrigin = frontendOrigin;
    }
}


