package de.darfichraus.entity;

import org.pac4j.mongo.profile.MongoProfile;
import org.springframework.data.mongodb.core.mapping.Field;

public class Users {
    private String id;
    @Field("id")
    private String userId;
    private String linkedId;
    @Field("serializedprofile")
    private MongoProfile mongoProfile;
    private String username;
    private String password;


    public String getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(String linkedid) {
        this.linkedId = linkedid;
    }


    public MongoProfile getMongoProfile() {
        return mongoProfile;
    }

    public void setMongoProfile(MongoProfile mongoProfile) {
        this.mongoProfile = mongoProfile;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
