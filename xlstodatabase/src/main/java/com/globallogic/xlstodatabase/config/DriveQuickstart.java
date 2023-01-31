package com.globallogic.xlstodatabase.config;

import com.globallogic.xlstodatabase.dto.MeetingDto;
import com.globallogic.xlstodatabase.exception.ExcelReadingException;
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
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DriveQuickstart {

	private static Logger logger = LoggerFactory.getLogger(DriveQuickstart.class);

	/**
	 * Application name.
	 */
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	/**
	 * Global instance of the JSON factory.
	 */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	/**
	 * Directory to store authorization tokens for this application.
	 */
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
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
		InputStream in = DriveQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(
						new java.io.File(System.getProperty("user.home"), TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		// returns an authorized Credential object.
		return credential;
	}

	public List<List<MeetingDto>> getFilesFromDrive() throws IOException, GeneralSecurityException {
		try {
			logger.info("Inside getFilesFromDrive of DriveQuickStart");
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME).build();
			FileList result = service.files().list().setQ("mimeType='application/vnd.google-apps.folder'")
					.setPageSize(100).setFields("nextPageToken, files(id, name)").execute();
			List<File> files = result.getFiles();
			String id = null;
			for (File f : files) {
				if (f.getName().equals("Meeting Reports")) {
					id = f.getId();
				}
			}
			if (id == null) {
				throw new ExcelReadingException("SpreadSheet Folder Does Not Exist");
			}
			List<String> idsOfSpreadSheet = new ArrayList<>();
			List<String> namesOfSpreadSheet = new ArrayList<>();
			String query = "'" + id + "' in parents";
			FileList result1 = service.files().list().setQ(query).setPageSize(100)
					.setFields("nextPageToken, files(id, name)").execute();
			List<File> filesInFolderMeetingReports = result1.getFiles();
			List<String> allFoldersIdInsideMeetingReports = new ArrayList<>();
			if (filesInFolderMeetingReports == null || filesInFolderMeetingReports.isEmpty()) {
				System.out.println("No Files Found");
			} else {
				for (File f1 : filesInFolderMeetingReports) {
					System.out.println("folder is" + f1.getName());
					allFoldersIdInsideMeetingReports.add(f1.getId());
				}
			}
			for (String folderInsideMr : allFoldersIdInsideMeetingReports) {
				String queryForInsideFolder = "'" + folderInsideMr + "' in parents";
				FileList spreadSheets = service.files().list().setQ(queryForInsideFolder).setPageSize(100)
						.setFields("nextPageToken, files(id, name)").execute();
				List<File> allSpreadSheets = spreadSheets.getFiles();
				for (File file : allSpreadSheets) {
					idsOfSpreadSheet.add(file.getId());
					namesOfSpreadSheet.add(file.getName());
					System.out.println("file is" + file.getName());
				}
			}
			logger.info("Calling get Spread Sheet Data from Google Api Utils");
			GoogleApiUtil googleApiUtil = new GoogleApiUtil();
			return googleApiUtil.getSpreadSheetData(idsOfSpreadSheet, namesOfSpreadSheet);
		} catch (IOException io) {
			io.printStackTrace();
			String errorMsg = MessageFormat.format("Exception caught in getFilesFromDrive of DriverQuickStart :{0}",
					io);
			throw new ExcelReadingException(errorMsg);
		} catch (GeneralSecurityException ge) {
			ge.printStackTrace();
			String errorMsg = MessageFormat.format("Exception caught in getFilesFromDrive of DriverQuickStart :{0}",
					ge);
			throw new ExcelReadingException(errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			String errorMsg = MessageFormat.format("Exception caught in getFilesFromDrive of DriverQuickStart :{0}", e);
			throw new ExcelReadingException(errorMsg);
		}
	}
}