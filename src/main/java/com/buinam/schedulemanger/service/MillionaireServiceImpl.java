package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.MillionaireGroupDTO;
import com.buinam.schedulemanger.dto.MillionaireQuestionListDTO;
import com.buinam.schedulemanger.dto.QuestionInGroupDTO;
import com.buinam.schedulemanger.model.MillionaireAnswer;
import com.buinam.schedulemanger.model.MillionaireGroup;
import com.buinam.schedulemanger.model.MillionaireQuestion;
import com.buinam.schedulemanger.repository.MillionaireAnswerRepository;
import com.buinam.schedulemanger.repository.MillionaireGroupRepository;
import com.buinam.schedulemanger.repository.MillionaireQuestionRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MillionaireServiceImpl implements MillionaireService{
    @Autowired
    MillionaireQuestionRepository millionaireQuestionRepository;

    @Autowired
    MillionaireAnswerRepository millionaireAnswerRepository;

    @Autowired
    MillionaireGroupRepository millionaireGroupRepository;

    @Override
    public void importQuestions(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            if (worksheet.getRow(i) != null && worksheet.getRow(i).getCell(0) != null && worksheet.getRow(i).getCell(1) != null) {
                String question = worksheet.getRow(i).getCell(0).getStringCellValue();
                String correctAnswer = worksheet.getRow(i).getCell(5).getStringCellValue();
                int point = (int) worksheet.getRow(i).getCell(6).getNumericCellValue();

                String answerA = worksheet.getRow(i).getCell(1).getStringCellValue();
                String answerB = worksheet.getRow(i).getCell(2).getStringCellValue();
                String answerC = worksheet.getRow(i).getCell(3).getStringCellValue();
                String answerD = worksheet.getRow(i).getCell(4).getStringCellValue();

                MillionaireQuestion millionaireQuestion = new MillionaireQuestion();
                millionaireQuestion.setContent(question);
                millionaireQuestion.setCorrectAnswer(correctAnswer);
                millionaireQuestion.setPoint(point);
                MillionaireQuestion savedQuestion = millionaireQuestionRepository.save(millionaireQuestion);

                saveAnswer(answerA, savedQuestion.getId(), "A");
                saveAnswer(answerB, savedQuestion.getId(), "B");
                saveAnswer(answerC, savedQuestion.getId(), "C");
                saveAnswer(answerD, savedQuestion.getId(), "D");
            }
        }


    }

    @Override
    public MillionaireGroup saveGroup(MillionaireGroupDTO groupDTO) {
        MillionaireGroup millionaireGroup = new MillionaireGroup();
        millionaireGroup.setName(groupDTO.getName());
        millionaireGroup.setDescription(groupDTO.getDescription());
        millionaireGroup.setImgUrl(groupDTO.getImgUrl());
        return millionaireGroupRepository.save(millionaireGroup);
    }

    @Override
    public MillionaireGroup addQuestion(Long groupId, MillionaireQuestionListDTO questionListDTO) {
        Optional<MillionaireGroup> optionalMillionaireGroup = millionaireGroupRepository.findById(groupId);
        if(optionalMillionaireGroup.isPresent()){
            MillionaireGroup millionaireGroup = optionalMillionaireGroup.get();
            for (Long questionId : questionListDTO.getQuestionList()) {
                Optional<MillionaireQuestion> optionalMillionaireQuestion = millionaireQuestionRepository.findById(questionId);
                if(optionalMillionaireQuestion.isPresent()){
                    MillionaireQuestion millionaireQuestion = optionalMillionaireQuestion.get();
                    millionaireGroup.getMillionaireQuestions().add(millionaireQuestion);
                }
            }
            return millionaireGroup;
        }
        return null;
    }

    @Override
    public List<QuestionInGroupDTO> getQuestionsFromGroupId(Long groupId) {
        Optional<MillionaireGroup> optionalMillionaireGroup = millionaireGroupRepository.findById(groupId);
        if(optionalMillionaireGroup.isPresent()){
            MillionaireGroup millionaireGroup = optionalMillionaireGroup.get();
            List<QuestionInGroupDTO> listReturn = new ArrayList<>();

            millionaireGroup.getMillionaireQuestions().forEach(question -> {
                QuestionInGroupDTO questionInGroupDTO = new QuestionInGroupDTO();
                List<MillionaireAnswer> answers = millionaireAnswerRepository.findByQuestionId(question.getId());
                if(answers.size() == 4){
                    questionInGroupDTO.setAnswers(answers);
                    questionInGroupDTO.setId(question.getId());
                    questionInGroupDTO.setContent(question.getContent());
                    questionInGroupDTO.setCorrectAnswer(question.getCorrectAnswer());
                    questionInGroupDTO.setPoint(question.getPoint());
                    listReturn.add(questionInGroupDTO);
                } else {
                    throw new RuntimeException("Invalid question found!");
                }
            });
            return listReturn;
        }
        return null;
    }

    private void saveAnswer(String answer, Long questionId, String name) {
        MillionaireAnswer millionaireAnswer = new MillionaireAnswer();
        millionaireAnswer.setContent(answer);
        millionaireAnswer.setQuestionId(questionId);
        millionaireAnswer.setName(name);
        millionaireAnswerRepository.save(millionaireAnswer);
    }
}
