package com.buinam.schedulemanger.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MillionaireGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imgUrl;
    private String description;

    @ManyToMany
    @JoinTable(
        name="millionaire_question_in_group",
        joinColumns = @JoinColumn(name="millionaire_group_id"),
        inverseJoinColumns = @JoinColumn(name="millionaire_question_id")
    )
    private Set<MillionaireQuestion> millionaireQuestions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MillionaireQuestion> getMillionaireQuestions() {
        return millionaireQuestions;
    }

    public void setMillionaireQuestions(Set<MillionaireQuestion> millionaireQuestions) {
        this.millionaireQuestions = millionaireQuestions;
    }
}
