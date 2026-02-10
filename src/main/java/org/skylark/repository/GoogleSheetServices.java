package org.skylark.repository;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;

@Service
public class GoogleSheetServices {

    private Sheets sheetsService;

    public GoogleSheetServices() throws Exception {

        InputStream in = getClass().getResourceAsStream("/credentials.json");

        GoogleCredential credential = GoogleCredential.fromStream(in)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        sheetsService = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential
        ).setApplicationName("Drone Coordinator").build();
    }

    public Sheets getSheetsService() {
        return sheetsService;
    }
}
