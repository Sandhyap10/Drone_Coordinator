package org.skylark.repository;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
public class GoogleSheetServices {

    private Sheets sheetsService;

    public GoogleSheetServices() throws Exception {

        // 🔥 READ FROM RAILWAY ENV VARIABLE
        String credentialsJson = System.getenv("Google_Credentials_JSON");

        if (credentialsJson == null || credentialsJson.isEmpty()) {
            throw new RuntimeException("Google credentials not found in environment variable!");
        }

        InputStream credentialsStream = new ByteArrayInputStream(
                credentialsJson.getBytes(StandardCharsets.UTF_8)
        );

        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));

        sheetsService = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Drone Coordinator").build();
    }

    public Sheets getSheetsService() {
        return sheetsService;
    }
}
