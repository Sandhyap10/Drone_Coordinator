package org.skylark.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.skylark.model.Mission;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MissionSheetRepository {

    private final GoogleSheetServices sheetsService;

    public MissionSheetRepository(GoogleSheetServices sheetsService) {
        this.sheetsService = sheetsService;
    }

    private static final String SPREADSHEET_ID = "1iZ_uUa2p1nvpWxWTV7xqNRM7GbC9U0dT-GneiADxskg";
    private static final String RANGE = "missions!A2:H";

    public List<Mission> getAllMissions() throws Exception {

        Sheets sheets = sheetsService.getSheetsService();

        ValueRange response = sheets.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> rows = response.getValues();
        List<Mission> missions = new ArrayList<>();

        if (rows == null) return missions;

        for (List<Object> row : rows) {
            Mission m = new Mission();
            m.setMissionId(getValue(row, 0));
            m.setClientName(getValue(row, 1));
            m.setLocation(getValue(row, 2));
            m.setRequiredSkills(getValue(row, 3));
            m.setRequiredCertifications(getValue(row, 4));
            m.setStartDate(getValue(row, 5));
            m.setEndDate(getValue(row, 6));
            m.setPriority(getValue(row, 7));
            missions.add(m);
        }

        return missions;
    }

    private String getValue(List<Object> row, int index) {
        return index < row.size() ? row.get(index).toString() : "";
    }
}
