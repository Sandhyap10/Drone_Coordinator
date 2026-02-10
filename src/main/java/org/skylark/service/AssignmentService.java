package org.skylark.service;

import org.skylark.model.*;
import org.skylark.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    private final PilotRepo pilotRepo;
    private final DroneSheetRepository droneRepo;
    private final MissionSheetRepository missionRepo;
    private final ConflictService conflictService;

    public AssignmentService(PilotRepo pilotRepo,
                             DroneSheetRepository droneRepo,
                             MissionSheetRepository missionRepo,
                             ConflictService conflictService) {
        this.pilotRepo = pilotRepo;
        this.droneRepo = droneRepo;
        this.missionRepo = missionRepo;
        this.conflictService = conflictService;
    }

    public AssignmentResult assignMission(String missionId) throws Exception {

        Mission mission = missionRepo.getAllMissions().stream()
                .filter(m -> m.getMissionId().equalsIgnoreCase(missionId))
                .findFirst()
                .orElse(null);

        AssignmentResult result = new AssignmentResult();

        if (mission == null) {
            result.setSuccess(false);
            result.setMessage("Mission not found");
            return result;
        }

        boolean isUrgent = mission.getPriority().equalsIgnoreCase("High")
                || mission.getPriority().equalsIgnoreCase("Urgent");

        List<String> allConflicts = new ArrayList<>();

        // 🔹 NORMAL ASSIGNMENT FIRST
        for (Pilot pilot : pilotRepo.getAllPilots()) {
            for (Drone drone : droneRepo.getAllDrones()) {

                List<String> conflicts = conflictService.checkConflicts(pilot, drone, mission);

                if (conflicts.isEmpty()) {

                    pilotRepo.updatePilotAssignment(pilot.getPilotId(), missionId);
                    droneRepo.updateDroneAssignment(drone.getDroneId(), missionId);

                    result.setSuccess(true);
                    result.setAssignedPilot(pilot);
                    result.setAssignedDrone(drone);
                    result.setMessage("Assignment successful and system updated");
                    return result;
                } else {
                    allConflicts.add(
                            "Pilot " + pilot.getName() +
                                    " + Drone " + drone.getDroneId() +
                                    " → " + conflicts
                    );
                }
            }
        }

        // 🚨 URGENT REASSIGNMENT LOGIC
        if (isUrgent) {
            for (Pilot pilot : pilotRepo.getAllPilots()) {

                if (!pilot.getStatus().equalsIgnoreCase("Assigned")) continue;

                Mission currentMission = missionRepo.getAllMissions().stream()
                        .filter(m -> m.getMissionId().equalsIgnoreCase(pilot.getCurrentAssignment()))
                        .findFirst()
                        .orElse(null);

                if (currentMission != null &&
                        !currentMission.getPriority().equalsIgnoreCase("High") &&
                        !currentMission.getPriority().equalsIgnoreCase("Urgent")) {

                    for (Drone drone : droneRepo.getAllDrones()) {

                        if (!drone.getStatus().equalsIgnoreCase("Available")) continue;
                        if (!drone.getLocation().equalsIgnoreCase(mission.getLocation())) continue;

                        pilotRepo.updatePilotAssignment(pilot.getPilotId(), missionId);
                        droneRepo.updateDroneAssignment(drone.getDroneId(), missionId);

                        result.setSuccess(true);
                        result.setAssignedPilot(pilot);
                        result.setAssignedDrone(drone);
                        result.setMessage("Urgent reassignment done from lower priority mission");
                        return result;
                    }
                }
            }
        }

        result.setSuccess(false);
        result.setConflicts(allConflicts);
        result.setMessage("No valid pilot-drone pair found after checking all combinations");
        return result;
    }
}
