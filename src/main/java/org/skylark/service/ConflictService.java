package org.skylark.service;

import org.skylark.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictService {

    public List<String> checkConflicts(Pilot pilot, Drone drone, Mission mission) {
        List<String> issues = new ArrayList<>();

        if (!pilot.getSkills().contains(mission.getRequiredSkills()))
            issues.add("Pilot missing required skill");

        if (!pilot.getCertifications().contains(mission.getRequiredCertifications()))
            issues.add("Pilot missing certification");

        if (drone.getStatus().equalsIgnoreCase("Maintenance"))
            issues.add("Drone is under maintenance");

        if (!pilot.getLocation().equalsIgnoreCase(mission.getLocation()))
            issues.add("Pilot not in mission location");

        if (!drone.getLocation().equalsIgnoreCase(mission.getLocation()))
            issues.add("Drone not in mission location");

        return issues;
    }
}
