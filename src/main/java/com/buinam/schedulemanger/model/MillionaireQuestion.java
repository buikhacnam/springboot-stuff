package com.buinam.schedulemanger.model;

import com.buinam.schedulemanger.dto.MillionaireCorrectAnswerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class MillionaireQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private String correctAnswer;
    private Integer point;

    @JsonIgnore
    @ManyToMany(mappedBy = "millionaireQuestions")
    private Set<MillionaireGroup> millionaireGroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Set<MillionaireGroup> getMillionaireGroups() {
        return millionaireGroups;
    }

    public void setMillionaireGroups(Set<MillionaireGroup> millionaireGroups) {
        this.millionaireGroups = millionaireGroups;
    }
}
