package com.devofindia.naturewallpaper.Modals;

public class WallpaperModal {
    private int id;
    private String orignalUrl,mediumUrl;

    public WallpaperModal() {
    }

    public WallpaperModal(int id, String orignalUrl, String mediumUrl) {
        this.id = id;
        this.orignalUrl = orignalUrl;
        this.mediumUrl = mediumUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrignalUrl() {
        return orignalUrl;
    }

    public void setOrignalUrl(String orignalUrl) {
        this.orignalUrl = orignalUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }
}
