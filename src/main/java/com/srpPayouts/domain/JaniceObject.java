package com.srpPayouts.domain;

import java.util.Objects;

public class JaniceObject {

    private String moduleName;
    private String modulePrice;

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }
    public String getModulePrice() { return modulePrice; }
    public void setModulePrice(String modulePrice) { this.modulePrice = modulePrice; }

    protected boolean canEqual(Object other) {
        return other instanceof JaniceObject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof JaniceObject janiceObject) || !janiceObject.canEqual(this)) {
            return false;
        }
        return Objects.equals(moduleName, janiceObject.moduleName)
                && Objects.equals(modulePrice, janiceObject.modulePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, modulePrice);
    }

    @Override
    public String toString() {
        return "JaniceObject(moduleName=" + moduleName + ", modulePrice=" + modulePrice + ")";
    }
}
