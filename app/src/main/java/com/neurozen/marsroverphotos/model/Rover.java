package com.neurozen.marsroverphotos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class Rover extends BaseModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("landing_date")
    @Expose
    private String landingDate;
    @SerializedName("launch_date")
    @Expose
    private String launchDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("max_sol")
    @Expose
    private int maxSol;
    @SerializedName("max_date")
    @Expose
    private String maxDate;
    @SerializedName("total_photos")
    @Expose
    private int totalPhotos;
    @SerializedName("cameras")
    @Expose
    private List<Camera> cameras = null;

    private Rover(int id, String name, String landingDate, String launchDate,
                 String status, int maxSol, String maxDate, int totalPhotos, List<Camera> cameras) {
        super();
        this.id = id;
        this.name = name;
        this.landingDate = landingDate;
        this.launchDate = launchDate;
        this.status = status;
        this.maxSol = maxSol;
        this.maxDate = maxDate;
        this.totalPhotos = totalPhotos;
        this.cameras = cameras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rover withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rover withName(String name) {
        this.name = name;
        return this;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public Rover withLandingDate(String landingDate) {
        this.landingDate = landingDate;
        return this;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public Rover withLaunchDate(String launchDate) {
        this.launchDate = launchDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Rover withStatus(String status) {
        this.status = status;
        return this;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(landingDate).append(launchDate).append(status).append(maxSol).append(maxDate).append(totalPhotos).append(cameras).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Rover) == false) {
            return false;
        }
        Rover rhs = ((Rover) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(landingDate, rhs.landingDate).append(launchDate, rhs.launchDate).append(status, rhs.status).append(maxSol, rhs.maxSol).append(maxDate, rhs.maxDate).append(totalPhotos, rhs.totalPhotos).append(cameras, rhs.cameras).isEquals();
    }

    public static class Builder {
        private int id;
        private String name;
        private String landingDate;
        private String launchDate;
        private String status;
        private int maxSol;
        private String maxDate;
        private int totalPhotos;
        private List<Camera> cameras;

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

        public Builder withLandingDate(String landingDate) {
            this.landingDate = landingDate;
            return this;
        }

        public Builder withLaunchDate(String launchDate) {
            this.launchDate = launchDate;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withMaxSol(int maxSol) {
            this.maxSol = maxSol;
            return this;
        }

        public Builder withMaxDate(String maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        public Builder withTotalPhotos(int totalPhotos) {
            this.totalPhotos = totalPhotos;
            return this;
        }

        public Builder withCameras(List<Camera> cameras) {
            this.cameras = cameras;
            return this;
        }

        public Rover build() {
            return new Rover(id, name, landingDate, launchDate, status, maxSol, maxDate, totalPhotos, cameras);
        }
    }

    public enum Vehicles {
        CURIOSITY,
        OPPORTUNITY,
        SPIRIT;
    }
}
