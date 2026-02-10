package org.skylark.controller;

import org.skylark.model.AssignmentResult;
import org.skylark.service.AssignmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assign")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/{missionId}")
    public AssignmentResult assignMission(@PathVariable String missionId) throws Exception {
        return assignmentService.assignMission(missionId);
    }
}
