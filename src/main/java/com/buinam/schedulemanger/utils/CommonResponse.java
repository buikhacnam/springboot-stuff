package com.buinam.schedulemanger.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommonResponse {
    private String message;
    private boolean isValidRequest;
    private Object responseData;
    private Integer status;    
}
