package org.skylark.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.skylark.model.Drone;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DroneSheetRepository {

    private final GoogleSheetServices sheetsService;

    public DroneSheetRepository(GoogleSheetServices sheetsService) {
        this.sheetsService = sheetsService;
    }

    private static final String SPREADSHEET_ID = "1iZ_uUa2p1nvpWxWTV7xqNRM7GbC9U0dT-GneiADxskg";
    private static final String RANGE = "drone_fleet!A2:G";

    public List<Drone> getAllDrones() throws Exception {

        Sheets sheets = sheetsService.getSheetsService();

        ValueRange response = sheets.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> rows = response.getValues();
        List<Drone> drones = new ArrayList<>();

        if (rows == null) return drones;

        for (List<Object> row : rows) {
            Drone d = new Drone();
            d.setDroneId(getValue(row, 0));        // A
            d.setModel(getValue(row, 1));          // B
            d.setCapabilities(getValue(row, 2));   // C
            d.setStatus(getValue(row, 3));         // D
            d.setLocation(getValue(row, 4));       // E
            d.setCurrentAssignment(getValue(row, 5)); // F
            d.setMaintenanceDue(getValue(row, 6)); // G
            drones.add(d);
        }

        return drones;
    }

    private String getValue(List<Object> row, int index) {
        return index < row.size() ? row.get(index).toString() : "";
    }

    public void updateDroneAssignment(String droneId, String missionId) throws Exception {
        Sheets sheets = sheetsService.getSheetsService();

        List<List<Object>> rows = sheets.spreadsheets().values()
                .get(SPREADSHEET_ID, "Drone_Fleet!A2:G")
                .execute()
                .getValues();

        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).get(0).toString().equals(droneId)) {

                // Column E = Status, Column F = Assignment
                sheets.spreadsheets().values().update(
                        SPREADSHEET_ID,
                        "Drone_Fleet!D" + (i + 2),
                        new ValueRange().setValues(List.of(List.of("Deployed")))
                ).setValueInputOption("RAW").execute();

                sheets.spreadsheets().values().update(
                        SPREADSHEET_ID,
                        "Drone_Fleet!F" + (i + 2),
                        new ValueRange().setValues(List.of(List.of(missionId)))
                ).setValueInputOption("RAW").execute();

                break;
            }
        }
    }

}
