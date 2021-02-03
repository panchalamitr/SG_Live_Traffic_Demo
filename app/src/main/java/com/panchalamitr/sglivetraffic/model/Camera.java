
package com.panchalamitr.sglivetraffic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Camera {

    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("image")
    private String image;
    @SerializedName("location")
    private Location location;
    @SerializedName("camera_id")
    private String cameraId;
    @SerializedName("image_metadata")
    private ImageMetadata imageMetadata;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public ImageMetadata getImageMetadata() {
        return imageMetadata;
    }

    public void setImageMetadata(ImageMetadata imageMetadata) {
        this.imageMetadata = imageMetadata;
    }

}
