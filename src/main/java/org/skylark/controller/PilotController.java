package org.skylark.controller;

import org.skylark.model.Pilot;
import org.skylark.repository.PilotRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PilotController {

    private final PilotRepo pilotRepo;

    public PilotController(PilotRepo pilotRepo) {
        this.pilotRepo = pilotRepo;
    }

    @GetMapping("/pilots")
    public List<Pilot> getPilots() throws Exception {
        return pilotRepo.getAllPilots();
    }
}
