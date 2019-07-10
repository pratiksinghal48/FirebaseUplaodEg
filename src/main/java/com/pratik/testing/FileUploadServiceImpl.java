package com.pratik.testing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;

@Service
public class FileUploadServiceImpl implements FileService {

	private Storage gcStorage;
	private String bucketName = "aboutstays-b349f.appspot.com";

	@PostConstruct
	public void initializeSDK() throws IOException {
		FileInputStream serviceAccount = new FileInputStream("Path/to/firebase/credentials.json");

		gcStorage = StorageOptions.newBuilder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build()
				.getService();
	}

	@Override
	public Optional<String> uploadFile(byte[] bytes, String fileName) {
		BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, "Images/" + fileName)
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build();
		BlobInfo responseBlobInfo = null;
		try {
			responseBlobInfo = gcStorage.create(blobInfo, bytes);
		} catch (StorageException e) {
			// Log error and throw or handle
			e.printStackTrace();
			return Optional.empty();
		}
		// return the public download link
		return Optional.ofNullable(responseBlobInfo.getMediaLink());
	}

}
