package org.skylark.controller;

import org.skylark.model.Drone;
import org.skylark.repository.DroneSheetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DroneController {

    private final DroneSheetRepository droneRepo;

    public DroneController(DroneSheetRepository droneRepo) {
        this.droneRepo = droneRepo;
    }

    @GetMapping("/drones")
    public List<Drone> getDrones() throws Exception {
        return droneRepo.getAllDrones();
    }
}
