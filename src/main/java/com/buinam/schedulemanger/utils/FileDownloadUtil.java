package com.buinam.schedulemanger.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileDownloadUtil {
    private Path foundFile;
    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("Files-upload");
        Files.list(dirPath).forEach(filePath -> {
            if (filePath.getFileName().toString().equals(fileCode)) {
                foundFile = filePath;
            }
        });

        if(foundFile == null) {
            return null;
        }

        return new UrlResource(foundFile.toUri());
    }
}
