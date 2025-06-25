package com.jca.thedoor.entity.mongodb;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Merge {
    private String id;
    private String reviewer;
    @NotNull
    private Date date;

    private Coworker coworker;

    /** GETS AND SETS **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Coworker getCoworker() {
        return coworker;
    }

    public void setCoworker(Coworker coworker) {
        this.coworker = coworker;
    }
}
