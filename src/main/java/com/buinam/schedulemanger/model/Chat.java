package com.buinam.schedulemanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userOne;
    private String userTwo;

}
