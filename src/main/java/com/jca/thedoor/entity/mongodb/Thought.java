package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document("thoughts")
@CompoundIndex(name = "user_notebook_title_index", def = "{'user': 1, 'notebook': 1, 'title': 1}", unique = true)
public class Thought extends MongoModel{
    /*@MongoId
    private ObjectId _id;
    private String id;*/

    @NotNull
    @NotBlank
    private String user;

    @NotNull
    @NotBlank
    private String notebook;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String status;

    private String link;

    private String linkCode;

    private String scenario;

    private List<ObjectSummarized> hashtags;

    private String description;

    private String solution;

    private List<ObjectSummarized> coworkers;

    private List<PullRequest> pullsRequest;

    private List <Merge> merges;

    private List <Deploy> deploys;

    @NotNull
    private Date creationDate;

    private Date startDate;
    private Date endDate;

    private Thought() {
        super();
    }



    // Getters and Setters

    /*public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNotebook() {
        return notebook;
    }

    public void setNotebook(String notebook) {
        this.notebook = notebook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(String linkCode) {
        this.linkCode = linkCode;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public List<ObjectSummarized> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<ObjectSummarized> hashtags) {
        this.hashtags = hashtags;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public List<ObjectSummarized> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(List<ObjectSummarized> coworkers) {
        this.coworkers = coworkers;
    }

    public List<PullRequest> getPullsRequest() {
        return pullsRequest;
    }

    public void setPullsRequest(List<PullRequest> pullsRequest) {
        this.pullsRequest = pullsRequest;
    }

    public List<Merge> getMerges() {
        return merges;
    }

    public void setMerges(List<Merge> merges) {
        this.merges = merges;
    }

    public List<Deploy> getDeploys() {
        return deploys;
    }

    public void setDeploys(List<Deploy> deploys) {
        this.deploys = deploys;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static class Builder {
        private final Thought thought;

        public Builder() {
            thought = new Thought();
        }

        public Builder id(ObjectId _id) {
            // thought._id = _id;
            return this;
        }

        public Builder id(String id) {
            // thought.id = id;
            return this;
        }

        public Builder user(String user) {
            thought.user = user;
            return this;
        }

        public Builder notebook(String notebook) {
            thought.notebook = notebook;
            return this;
        }

        public Builder title(String title) {
            thought.title = title;
            return this;
        }

        public Builder type(String type) {
            thought.type = type;
            return this;
        }

        public Builder description(String description) {
            thought.description = description;
            return this;
        }

        public Builder status(String status) {
            thought.status = status;
            return this;
        }

        public Builder creationDate(Date creationDate) {
            thought.creationDate = creationDate;
            return this;
        }

        public Builder startDate(Date startDate) {
            thought.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            thought.endDate = endDate;
            return this;
        }

        public Thought build() {
            return thought;
        }
    }
}
    /*Thought thought = new Thought.Builder()
            .id(new ObjectId())
            .user("John Doe")
            .type("Type")
            .description("Description")
            .status("Status")
            .creationDate(new Date())
            .startDate(new Date())
            .endDate(new Date())
            .build();*/