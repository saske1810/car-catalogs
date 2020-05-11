package com.example.diplom1;

public class CarSpec {

    private String name;

    private String batteryDensity;

    private double sparkPlugResistance;

    private String powerSteeringFluid;

    private String ignitionTiming;

    private String brakeFluid;

    private double knockSensor;

    private String coolantFluid;

    private double speakerImpedance;

    private String image;

    public CarSpec(
            String name,
            String batteryDensity,
            double sparkPlugResistance,
            String powerSteeringFluid,
            String ignitionTiming,
            String brakeFluid,
            double knockSensor,
            String coolantFluid,
            double speakerImpedance,
            String image
    ) {
        this.name                   = name;
        this.batteryDensity         = batteryDensity;
        this.sparkPlugResistance    = sparkPlugResistance;
        this.powerSteeringFluid     = powerSteeringFluid;
        this.ignitionTiming         = ignitionTiming;
        this.brakeFluid             = brakeFluid;
        this.knockSensor            = knockSensor;
        this.coolantFluid           = coolantFluid;
        this.speakerImpedance       = speakerImpedance;
        this.image                  = image;
    }



    public String getName() {
        return name;
    }

    public String getBatteryDensity() {
        return batteryDensity;
    }

    public double getSparkPlugResistance() {
        return sparkPlugResistance;
    }

    public String getPowerSteeringFluid() {
        return powerSteeringFluid;
    }

    public String getIgnitionTiming() {
        return ignitionTiming;
    }

    public String getBrakeFluid() {
        return brakeFluid;
    }

    public double getKnockSensor() {
        return knockSensor;
    }

    public String getCoolantFluid() {
        return coolantFluid;
    }

    public double getSpeakerImpedance() {
        return speakerImpedance;
    }

    public String getImage() {
        return image;
    }
}


