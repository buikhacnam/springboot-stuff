package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.MillionaireGroupDTO;
import com.buinam.schedulemanger.dto.MillionaireQuestionListDTO;
import com.buinam.schedulemanger.dto.QuestionInGroupDTO;
import com.buinam.schedulemanger.model.MillionaireGroup;
import com.buinam.schedulemanger.service.MillionaireService;
import com.buinam.schedulemanger.utils.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/millionaire")
public class MillionaireController {

    @Autowired
    private MillionaireService millionaireService;


    @PostMapping("/import-questions")
    public void importQuestions(@RequestParam("file") MultipartFile file) throws IOException {
        millionaireService.importQuestions(file);
    }


    @GetMapping("group/play/{groupId}")
    public ResponseEntity<CommonResponse> getQuestionsFromGroupId(@PathVariable("groupId") Long groupId) {
        try {
            List<QuestionInGroupDTO> questionInGroupDTOS = millionaireService.getQuestionsFromGroupId(groupId);
            return new ResponseEntity<>(
                    new CommonResponse(
                            "get data from group successfully",
                            true,
                            questionInGroupDTOS,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new CommonResponse(
                            "Failed to load data",
                            false,
                            null,
                            HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @PostMapping("/group/save")
    public ResponseEntity<CommonResponse> saveGroup(@RequestBody MillionaireGroupDTO groupDTO) {
        try {
            MillionaireGroup savedGroup = millionaireService.saveGroup(groupDTO);
            return new ResponseEntity<>(
                new CommonResponse(
                    "Save millionaire group successfully",
                    true,
                    savedGroup,
                    HttpStatus.OK.value()
                ),
                HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    "Save group failed",
                    false,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/group/add-question/{groupId}")
    public ResponseEntity<CommonResponse> addQuestion(@RequestBody MillionaireQuestionListDTO questionListDTO, @PathVariable(name="groupId") Long groupId) {
        try {
            MillionaireGroup savedGroup = millionaireService.addQuestion(groupId, questionListDTO);
            return new ResponseEntity<>(
                new CommonResponse(
                    "Add question(s) successfully",
                    true,
                    savedGroup,
                    HttpStatus.OK.value()
                ),
                HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    "Add question(s) failed",
                    false,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}

/*
            "id": 4,
            "content": "Đi đâu không?",
            "rightAnswer": "C",
            "point": 100,
            "answers": [
                {
                    "id": 13,
                    "questionId": 4,
                    "name": "A",
                    "content": "Không"
                },
                {
                    "id": 14,
                    "questionId": 4,
                    "name": "B",
                    "content": "Có"
                },
                {
                    "id": 15,
                    "questionId": 4,
                    "name": "C",
                    "content": "Chắc là có"
                },
                {
                    "id": 16,
                    "questionId": 4,
                    "name": "D",
                    "content": "Chắc là không"
                }
            ],
 */
