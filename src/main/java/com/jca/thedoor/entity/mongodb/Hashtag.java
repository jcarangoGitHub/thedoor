package com.jca.thedoor.entity.mongodb;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document("hashtags")
@CompoundIndex(name = "user_group_name_index", def = "{'user': 1, 'group': 1, 'name': 1}", unique = true)
public class Hashtag {
    @MongoId
    private ObjectId _id;
    private String id;

    @NotNull
    @NotBlank
    private String user;

    @NotNull
    @NotBlank
    private String group;

    @NotNull
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Date creationDate;

    private Hashtag() {
        super();
    }

    public void assignStringId() {
        this.setId(this.get_id().toString());
    }

    public void summarizeHashtag() {
        this._id = null;
        this.user = null;
        this.group = null;
        this.description = null;
        this.creationDate = null;
    }

    public ObjectId get_id() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = (name.charAt(0) == '#') ? name : "#" + name;
        } else {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
