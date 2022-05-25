package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.MillionaireCorrectAnswerDTO;
import com.buinam.schedulemanger.model.MillionaireAnswer;
import com.buinam.schedulemanger.model.MillionaireQuestion;
import com.buinam.schedulemanger.repository.MillionaireAnswerRepository;
import com.buinam.schedulemanger.repository.MillionaireQuestionRepository;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class MillionaireServiceImpl implements MillionaireService{
    @Autowired
    MillionaireQuestionRepository millionaireQuestionRepository;

    @Autowired
    MillionaireAnswerRepository millionaireAnswerRepository;

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

    private void saveAnswer(String answer, Long questionId, String name) {
        MillionaireAnswer millionaireAnswer = new MillionaireAnswer();
        millionaireAnswer.setContent(answer);
        millionaireAnswer.setQuestionId(questionId);
        millionaireAnswer.setName(name);
        millionaireAnswerRepository.save(millionaireAnswer);
    }
}
