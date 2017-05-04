package com.neurozen.marsroverphotos.model.manifest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neurozen.marsroverphotos.model.BaseModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PhotoMetadata extends BaseModel {

    @SerializedName("sol")
    @Expose
    private int sol;
    @SerializedName("total_photos")
    @Expose
    private int totalPhotos;
    @SerializedName("cameras")
    @Expose
    private List<String> cameras = null;

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public PhotoMetadata withSol(int sol) {
        this.sol = sol;
        return this;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public PhotoMetadata withTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
        return this;
    }

    public List<String> getCameras() {
        return cameras;
    }

    public void setCameras(List<String> cameras) {
        this.cameras = cameras;
    }

    public PhotoMetadata withCameras(List<String> cameras) {
        this.cameras = cameras;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(sol).append(totalPhotos).append(cameras).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PhotoMetadata) == false) {
            return false;
        }
        PhotoMetadata rhs = ((PhotoMetadata) other);
        return new EqualsBuilder().append(sol, rhs.sol).append(totalPhotos, rhs.totalPhotos).append(cameras, rhs.cameras).isEquals();
    }

}
