package com.globallogic.xlstodatabase.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.xlstodatabase.dto.MeetingDto;
import com.globallogic.xlstodatabase.service.ParticipantsOfMeetingService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

@Service
public class GoogleApiUtil {
	
	
	@Autowired
	ParticipantsOfMeetingService participantsOfMeetingService;

	

	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = GoogleApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	/**
	 * Prints the names and majors of students in a sample spreadsheet:
	 * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
	 */

	/*
	 * public static void main(String... args) throws IOException,
	 * GeneralSecurityException { final NetHttpTransport HTTP_TRANSPORT =
	 * GoogleNetHttpTransport.newTrustedTransport(); final String spreadsheetId =
	 * "1QNAE6eympfNkC4KsQ7oU68Dya5fqpQnS5RUuPafePAg"; final String range =
	 * "A1:F93"; Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	 * getCredentials(HTTP_TRANSPORT))
	 * .setApplicationName(APPLICATION_NAME).build(); ValueRange response =
	 * service.spreadsheets().values().get(spreadsheetId, range).execute();
	 * List<List<Object>> values = response.getValues(); if (values == null ||
	 * values.isEmpty()) { System.out.println("No data found."); } else {
	 * System.out.println("Name, Major"); for (List row : values) { // Print columns
	 * A and E, which correspond to indices 0 and 4. System.out.println(row.get(0)
	 * +"  "+row.get(1) + "  " + row.get(2) + " " + row.get(3) + " " + row.get(4));
	 * } } }
	 */

	public List<List<MeetingDto>> getSpreadSheetData(List<String> idsList, List<String> spreedSheetName)
			throws IOException, GeneralSecurityException {
		int count = 0;
		List<List<MeetingDto>> dataListToBeSaved= new ArrayList<>();
		//MeetingService meetingService = new MeetingService();
		for (String id : idsList) {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport(); // final
			// String spreadsheetId = "1QNAE6eympfNkC4KsQ7oU68Dya5fqpQnS5RUuPafePAg"; final
			String spreadsheetId = id;
			final String range = "Attendees!A2:F";
			Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME).build();
			ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
			List<List<Object>> values = response.getValues();
			List<MeetingDto> paticipantsList = new ArrayList<>();
			if (values == null || values.isEmpty()) {
				System.out.println("No data found.");
			} else {
				for (List row : values) {
					MeetingDto meeting = new MeetingDto();
					meeting.setDuration(String.valueOf(row.get(3)));
					meeting.setEmail(String.valueOf(row.get(2)));
					meeting.setFirstName(String.valueOf(row.get(0)));
					meeting.setLastName(String.valueOf(row.get(1)));
					meeting.setMeetingId((spreedSheetName.get(count)).substring(16,28).trim());
					//meeting.setMeetingId((spreedSheetName.get(count)));
					meeting.setTimeExited(String.valueOf(row.get(5)));
					meeting.setTimeJoined(String.valueOf(row.get(4)));
					meeting.setMeetingDate((spreedSheetName.get(count)).substring(0,10).trim());
					paticipantsList.add(meeting);
				}
				dataListToBeSaved.add(paticipantsList);
				count++;
			}
		}
		return dataListToBeSaved;
	}

}