package com.buinam.schedulemanger.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserDevice  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "fcm_token")
    private String fcmToken;
    @Column(name = "device_type")
    private String deviceType;
}
