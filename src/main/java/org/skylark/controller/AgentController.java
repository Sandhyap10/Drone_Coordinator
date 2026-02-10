package org.skylark.controller;

import org.skylark.model.AssignmentResult;
import org.skylark.service.AssignmentService;
import org.skylark.repository.PilotRepo;
import org.skylark.repository.DroneSheetRepository;
import org.skylark.repository.MissionSheetRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private final AssignmentService assignmentService;
    private final PilotRepo pilotRepo;
    private final DroneSheetRepository droneRepo;
    private final MissionSheetRepository missionRepo;

    public AgentController(AssignmentService assignmentService,
                           PilotRepo pilotRepo,
                           DroneSheetRepository droneRepo,
                           MissionSheetRepository missionRepo) {
        this.assignmentService = assignmentService;
        this.pilotRepo = pilotRepo;
        this.droneRepo = droneRepo;
        this.missionRepo = missionRepo;
    }

    @PostMapping
    public Map<String, Object> handleRequest(@RequestBody Map<String, String> body) throws Exception {

        String message = body.get("message").toLowerCase();
        Map<String, Object> response = new HashMap<>();

        // 🔹 Show pilots
        if (message.contains("show pilots")) {
            response.put("type", "pilot_list");
            response.put("data", pilotRepo.getAllPilots());
            return response;
        }

        // 🔹 Show drones
        if (message.contains("show drones")) {
            response.put("type", "drone_list");
            response.put("data", droneRepo.getAllDrones());
            return response;
        }

        // 🔹 Show missions
        if (message.contains("show missions")) {
            response.put("type", "mission_list");
            response.put("data", missionRepo.getAllMissions());
            return response;
        }

        // 🔹 Assign mission
        if (message.startsWith("assign mission")) {
            String[] parts = message.split(" ");
            String missionId = parts[parts.length - 1].toUpperCase();

            AssignmentResult result = assignmentService.assignMission(missionId);
            response.put("type", "assignment_result");
            response.put("data", result);
            return response;
        }


        response.put("type", "unknown");
        response.put("message", "Sorry, I didn’t understand. Try 'show pilots' or 'assign mission PRJ001'");
        return response;
    }
}
