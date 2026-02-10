package org.skylark.controller;

import org.skylark.model.Mission;
import org.skylark.repository.MissionSheetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MissionController {

    private final MissionSheetRepository missionRepo;

    public MissionController(MissionSheetRepository missionRepo) {
        this.missionRepo = missionRepo;
    }

    @GetMapping("/missions")
    public List<Mission> getMissions() throws Exception {
        return missionRepo.getAllMissions();
    }
}
