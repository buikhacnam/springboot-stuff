package com.buinam.schedulemanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}

