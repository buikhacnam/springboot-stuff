package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.service.MillionaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/millionaire")
public class MillionaireController {

    @Autowired
    private MillionaireService millionaireService;

    @PostMapping("/import-questions")
    public void importQuestions(@RequestParam("file") MultipartFile file) throws IOException {
        millionaireService.importQuestions(file);
    }
}
