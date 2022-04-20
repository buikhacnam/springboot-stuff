package com.buinam.schedulemanger.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get("Files-upload");
        //if the folder "Files-upload" not exist, create it
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileCode = System.currentTimeMillis() + '-' + fileName;
        try {
            Path filePath = uploadPath.resolve(fileCode);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("File upload failed " + fileName + " " + e.getMessage());
        }
        return fileCode;
    }
}
