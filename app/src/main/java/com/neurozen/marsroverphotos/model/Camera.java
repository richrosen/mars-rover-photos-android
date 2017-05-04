package com.neurozen.marsroverphotos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Camera extends BaseModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rover_id")
    @Expose
    private int roverId;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    private Camera(int id, String name, int roverId, String fullName) {
        super();
        this.id = id;
        this.name = name;
        this.roverId = roverId;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoverId() {
        return roverId;
    }

    public void setRoverId(int roverId) {
        this.roverId = roverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(roverId).append(fullName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Camera) == false) {
            return false;
        }
        Camera rhs = ((Camera) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(roverId, rhs.roverId).append(fullName, rhs.fullName).isEquals();
    }

    public static class Builder {

        private int id;
        private String name;
        private int roverId;
        private String fullName;

        public Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRoverId(int roverId) {
            this.roverId = roverId;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Camera build() {
            return new Camera(id, name, roverId, fullName);
        }
    }
}