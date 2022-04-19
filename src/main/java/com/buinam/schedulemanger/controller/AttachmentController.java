package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Attachment;
import com.buinam.schedulemanger.model.AttachmentResponse;
import com.buinam.schedulemanger.service.AttachmentService;
import com.buinam.schedulemanger.utils.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadAttachment(@RequestParam("file") MultipartFile file) throws Exception {

        Attachment attachment = attachmentService.saveAttachment(file);
        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();

        return new ResponseEntity<>(
                new CommonResponse(
                        "save attachment successfully",
                        true,
                        new AttachmentResponse(
                                attachment.getFileName(),
                                downloadURl,
                                file.getContentType(),
                                file.getSize()
                        ),
                        HttpStatus.OK.value()
                ),
                HttpStatus.OK)
                ;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable String id) throws Exception {
        Attachment attachment = attachmentService.getAttachment(id);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));

    }
}



