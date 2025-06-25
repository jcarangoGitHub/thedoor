package com.jca.thedoor.entity.mongodb;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Deploy {
    private String id;
    @NotNull
    private String enviroment;
    @NotNull
    private Date date;

    /** GETS AND SETS **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
