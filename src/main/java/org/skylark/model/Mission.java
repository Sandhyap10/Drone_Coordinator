package org.skylark.model;

import lombok.Data;

@Data
public class Mission {

    private String missionId;
    private String clientName;
    private String location;
    private String requiredSkills;
    private String requiredCertifications;
    private String startDate;
    private String endDate;
    private String priority;   // High, Medium, Low
}
