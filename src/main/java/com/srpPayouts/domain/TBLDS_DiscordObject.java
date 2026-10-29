package com.srpPayouts.domain;

import java.util.Objects;

public class TBLDS_DiscordObject {
    private String hullName;
    private long hullPrice;

    public String getHullName() { return hullName; }
    public void setHullName(String hullName) { this.hullName = hullName; }
    public long getHullPrice() { return hullPrice; }
    public void setHullPrice(long hullPrice) { this.hullPrice = hullPrice; }

    protected boolean canEqual(Object other) {
        return other instanceof TBLDS_DiscordObject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TBLDS_DiscordObject discordObject) || !discordObject.canEqual(this)) {
            return false;
        }
        return hullPrice == discordObject.hullPrice
                && Objects.equals(hullName, discordObject.hullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hullName, hullPrice);
    }

    @Override
    public String toString() {
        return "TBLDS_DiscordObject(hullName=" + hullName + ", hullPrice=" + hullPrice + ")";
    }
}
