package org.skylark.model;

import lombok.Data;

@Data
public class Pilot {

    private String pilotId;
    private String name;
    private String skills;              // e.g., "Thermal, Surveillance"
    private String certifications;      // e.g., "Night Ops, BVLOS"
    private String droneExperience;     // e.g., "DJI M300"
    private String location;            // Current city
    private String status;              // Available, On Leave, Assigned
    private String currentAssignment;   // Mission ID
    private String availableFrom;       // Date string for simplicity
}