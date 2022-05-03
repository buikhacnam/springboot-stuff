package com.buinam.schedulemanger.model;

import com.buinam.schedulemanger.dto.StudentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
// a subject is going to have many students and
// a student is going to have many subjects
@NamedNativeQuery(name = "getAllEnrolledStudent",
        query = "SELECT * FROM student_enrolled",
        resultSetMapping = "student_enrolled_kkk"
)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "student_enrolled_kkk",
                classes = {
                        @ConstructorResult(
                                targetClass = StudentDTO.class,
                                columns = {
                                        @ColumnResult(name = "subject_id", type = Long.class),
                                        @ColumnResult(name = "student_id", type = Long.class)
                                })
                }
        )
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledStudents")
    private Set<Subject> subjects = new HashSet<>();

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

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
