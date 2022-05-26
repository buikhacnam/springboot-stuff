package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.MillionaireGroupDTO;
import com.buinam.schedulemanger.dto.MillionaireQuestionListDTO;
import com.buinam.schedulemanger.dto.QuestionInGroupDTO;
import com.buinam.schedulemanger.model.MillionaireGroup;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MillionaireService {
    void importQuestions(MultipartFile file) throws IOException;

    MillionaireGroup saveGroup(MillionaireGroupDTO groupDTO);

    MillionaireGroup addQuestion(Long groupId, MillionaireQuestionListDTO questionListDTO);

    List<QuestionInGroupDTO> getQuestionsFromGroupId(Long groupId);
}
