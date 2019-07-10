package com.pratik.testing;

import java.util.Optional;

public interface FileService {

	Optional<String> uploadFile(byte[] bytes, String fileName);

}
