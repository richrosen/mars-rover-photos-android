package com.neurozen.marsroverphotos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Photo extends BaseModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("sol")
    @Expose
    private int sol;
    @SerializedName("camera")
    @Expose
    private Camera camera;
    @SerializedName("img_src")
    @Expose
    private String imgSrc;
    @SerializedName("earth_date")
    @Expose
    private String earthDate;
    @SerializedName("rover")
    @Expose
    private Rover rover;

    private Photo(int id, int sol, Camera camera, String imgSrc, String earthDate, Rover rover) {
        super();
        this.id = id;
        this.sol = sol;
        this.camera = camera;
        this.imgSrc = imgSrc;
        this.earthDate = earthDate;
        this.rover = rover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(sol).append(camera).append(imgSrc).append(earthDate).append(rover).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Photo) == false) {
            return false;
        }
        Photo rhs = ((Photo) other);
        return new EqualsBuilder().append(id, rhs.id).append(sol, rhs.sol).append(camera, rhs.camera).append(imgSrc, rhs.imgSrc).append(earthDate, rhs.earthDate).append(rover, rhs.rover).isEquals();
    }

    public static class Builder {
        private int id;
        private int sol;
        private Camera camera;
        private String imgSrc;
        private String earthDate;
        private Rover rover;

        public Builder() {
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withSol(int sol) {
            this.sol = sol;
            return this;
        }

        public Builder withCamera(Camera camera) {
            this.camera = camera;
            return this;
        }

        public Builder withImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
            return this;
        }

        public Builder withEarthDate(String earthDate) {
            this.earthDate = earthDate;
            return this;
        }

        public Builder withRover(Rover rover) {
            this.rover = rover;
            return this;
        }

        public Photo build() {
            return new Photo(id, sol, camera, imgSrc, earthDate, rover);
        }
    }

}
