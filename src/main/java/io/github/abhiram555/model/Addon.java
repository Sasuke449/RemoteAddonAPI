package io.github.abhiram555.model;


public abstract class Addon {
    private AddonInfo addonInfo;

    public AddonInfo getAddonInfo(){
        return this.addonInfo;
    }

    public void setAddonInfo(AddonInfo addonInfo){
        this.addonInfo = addonInfo;
    }
}
