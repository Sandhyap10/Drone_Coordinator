package org.skylark.model;

import lombok.Data;

@Data
public class Drone {
    private String droneId;
    private String model;
    private String capabilities;
    private String location;
    private String status;
    private String currentAssignment;
    private String maintenanceDue;
}

