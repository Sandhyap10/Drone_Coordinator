package org.skylark.model;

import lombok.Data;
import java.util.List;

@Data
public class AssignmentResult {
    private boolean success;
    private String message;
    private Pilot assignedPilot;
    private Drone assignedDrone;
    private List<String> conflicts;
}
