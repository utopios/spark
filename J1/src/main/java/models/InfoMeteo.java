package models;

import java.io.Serializable;

public class InfoMeteo implements Serializable {
    private float temperature;
    private String stationId;
    private String typeInfo;

    public InfoMeteo(float temperature, String stationId, String typeInfo) {
        this.temperature = temperature;
        this.stationId = stationId;
        this.typeInfo = typeInfo;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(String typeInfo) {
        this.typeInfo = typeInfo;
    }
}
