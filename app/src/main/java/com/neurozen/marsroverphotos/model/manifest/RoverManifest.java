
package com.neurozen.marsroverphotos.model.manifest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neurozen.marsroverphotos.model.BaseModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RoverManifest extends BaseModel {

    @SerializedName("photo_manifest")
    @Expose
    private PhotoManifest photoManifest;

    public PhotoManifest getPhotoManifest() {
        return photoManifest;
    }

    public void setPhotoManifest(PhotoManifest photoManifest) {
        this.photoManifest = photoManifest;
    }

    public RoverManifest withPhotoManifest(PhotoManifest photoManifest) {
        this.photoManifest = photoManifest;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(photoManifest).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RoverManifest) == false) {
            return false;
        }
        RoverManifest rhs = ((RoverManifest) other);
        return new EqualsBuilder().append(photoManifest, rhs.photoManifest).isEquals();
    }

}
