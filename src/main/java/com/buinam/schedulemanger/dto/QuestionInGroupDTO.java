package com.buinam.schedulemanger.dto;

import com.buinam.schedulemanger.model.MillionaireAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionInGroupDTO {
    private Long id;
    private String content;
    private String correctAnswer;
    private int point;
    private List<MillionaireAnswer> answers = new ArrayList<>();

}

