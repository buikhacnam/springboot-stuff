package com.buinam.schedulemanger.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MillionaireService {
    void importQuestions(MultipartFile file) throws IOException;
}
