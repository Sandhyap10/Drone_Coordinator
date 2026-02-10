package org.skylark.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.skylark.model.Pilot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PilotRepo {

    private final GoogleSheetServices sheetsService;

    private static final String SPREADSHEET_ID = "1iZ_uUa2p1nvpWxWTV7xqNRM7GbC9U0dT-GneiADxskg";
    private static final String RANGE = "pilot_roster!A2:H";

    public List<Pilot> getAllPilots() throws Exception {

        Sheets sheets = sheetsService.getSheetsService();

        ValueRange response = sheets.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> rows = response.getValues();
        List<Pilot> pilots = new ArrayList<>();

        if (rows == null) return pilots;

        for (List<Object> row : rows) {
            Pilot p = new Pilot();
            p.setPilotId(getValue(row, 0));        // A
            p.setName(getValue(row, 1));           // B
            p.setSkills(getValue(row, 2));         // C
            p.setCertifications(getValue(row, 3)); // D
            p.setLocation(getValue(row, 4));       // E
            p.setStatus(getValue(row, 5));         // F
            p.setCurrentAssignment(getValue(row, 6)); // G
            p.setAvailableFrom(getValue(row, 7));  // H
            pilots.add(p);
        }

        return pilots;
    }

    private String getValue(List<Object> row, int index) {
        return index < row.size() ? row.get(index).toString() : "";
    }

    public void updatePilotAssignment(String pilotId, String missionId) throws Exception {
        Sheets sheets = sheetsService.getSheetsService();

        List<List<Object>> rows = sheets.spreadsheets().values()
                .get(SPREADSHEET_ID, "pilot_roster!A2:H")
                .execute()
                .getValues();

        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).get(0).toString().equals(pilotId)) {

                // Column F = Status
                sheets.spreadsheets().values().update(
                        SPREADSHEET_ID,
                        "pilot_roster!F" + (i + 2),
                        new ValueRange().setValues(List.of(List.of("Assigned")))
                ).setValueInputOption("RAW").execute();

                // Column G = Current Assignment
                sheets.spreadsheets().values().update(
                        SPREADSHEET_ID,
                        "pilot_roster!G" + (i + 2),
                        new ValueRange().setValues(List.of(List.of(missionId)))
                ).setValueInputOption("RAW").execute();

                break;
            }
        }
    }
}
