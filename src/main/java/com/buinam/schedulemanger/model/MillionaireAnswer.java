package com.buinam.schedulemanger.model;

import com.buinam.schedulemanger.dto.MillionaireCorrectAnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MillionaireAnswer {
    private Long id;
    private Long questionId;
    private MillionaireCorrectAnswerDTO name;
    private String content;
}
