package com.buinam.schedulemanger.model;

import com.buinam.schedulemanger.dto.MillionaireCorrectAnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class MillionaireQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private MillionaireCorrectAnswerDTO correctAnswer;
    private Integer point;
}
