package com.neurozen.marsroverphotos.model.manifest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neurozen.marsroverphotos.model.BaseModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PhotoManifest extends BaseModel {

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
    @SerializedName("photos")
    @Expose
    private List<PhotoMetadata> photos = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoManifest withName(String name) {
        this.name = name;
        return this;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public PhotoManifest withLandingDate(String landingDate) {
        this.landingDate = landingDate;
        return this;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public PhotoManifest withLaunchDate(String launchDate) {
        this.launchDate = launchDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PhotoManifest withStatus(String status) {
        this.status = status;
        return this;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }

    public PhotoManifest withMaxSol(int maxSol) {
        this.maxSol = maxSol;
        return this;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public PhotoManifest withMaxDate(String maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public PhotoManifest withTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
        return this;
    }

    public List<PhotoMetadata> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoMetadata> photos) {
        this.photos = photos;
    }

    public PhotoManifest withPhotos(List<PhotoMetadata> photos) {
        this.photos = photos;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(landingDate).append(launchDate).append(status).append(maxSol).append(maxDate).append(totalPhotos).append(photos).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhotoManifest) == false) {
            return false;
        }
        PhotoManifest rhs = ((PhotoManifest) other);
        return new EqualsBuilder().append(name, rhs.name).append(landingDate, rhs.landingDate).append(launchDate, rhs.launchDate).append(status, rhs.status).append(maxSol, rhs.maxSol).append(maxDate, rhs.maxDate).append(totalPhotos, rhs.totalPhotos).append(photos, rhs.photos).isEquals();
    }

}
