package com.buinam.schedulemanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MillionaireGroupDTO {
    private Long id;
    private String name;
    private String imgUrl;
    private String description;
}
