package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Attachment;
import com.buinam.schedulemanger.model.AttachmentResponse;
import com.buinam.schedulemanger.model.FileUploadResponse;
import com.buinam.schedulemanger.service.AttachmentService;
import com.buinam.schedulemanger.utils.CommonResponse;
import com.buinam.schedulemanger.utils.FileDownloadUtil;
import com.buinam.schedulemanger.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

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

    @PostMapping("/upload/dir")
    public ResponseEntity<CommonResponse> uploadAttachment(@RequestParam("file") MultipartFile file,  @RequestParam(required = false, defaultValue = "") String toDir) throws Exception {
        Attachment attachment = attachmentService.saveAttachmentToDir(file, "C:\\Host\\schedule-manger\\downloads\\");

        String downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloads/")
                .path(Objects.requireNonNull(file.getOriginalFilename()))
                .toUriString();

        return new ResponseEntity<>(
                new CommonResponse(
                        "save attachment to dir successfully",
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

    @PostMapping("/uploadFile")
    public ResponseEntity<CommonResponse> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long Size = file.getSize();
        String fileCode = FileUploadUtil.saveFile(fileName, file);
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        fileUploadResponse.setFileName(fileName);
        fileUploadResponse.setSize(Size);
        fileUploadResponse.setDownloadUri("/download/file/" + fileCode);

        return new ResponseEntity<>(
                new CommonResponse(
                        "save attachment to Files-upload folder successfully",
                        true,
                        fileUploadResponse,
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

    @GetMapping("/download/file/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") String id) {
        FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
        Resource resource = null;
        try {
            resource = fileDownloadUtil.getFileAsResource(id);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}



