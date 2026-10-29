package com.srpPayouts.domain;

import java.util.Objects;

public class ModuleObject {

    private String moduleName;
    private long unitPrice;
    private int quantity;
    private long totalPrice;

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }
    public long getUnitPrice() { return unitPrice; }
    public void setUnitPrice(long unitPrice) { this.unitPrice = unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getTotalPrice() { return totalPrice; }
    public void setTotalPrice(long totalPrice) { this.totalPrice = totalPrice; }

    protected boolean canEqual(Object other) {
        return other instanceof ModuleObject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ModuleObject moduleObject) || !moduleObject.canEqual(this)) {
            return false;
        }
        return unitPrice == moduleObject.unitPrice
                && quantity == moduleObject.quantity
                && totalPrice == moduleObject.totalPrice
                && Objects.equals(moduleName, moduleObject.moduleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, unitPrice, quantity, totalPrice);
    }

    @Override
    public String toString() {
        return "ModuleObject(moduleName=" + moduleName + ", unitPrice=" + unitPrice
                + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ")";
    }
}
