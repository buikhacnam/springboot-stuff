package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.model.Attachment;
import com.buinam.schedulemanger.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }
//            attachment.setFileName(fileName);
//            attachment.setFileType(file.getContentType());
//            attachment.setData(file.getBytes());

            log.info("File name: " + fileName);
            log.info("File type: " + file.getContentType());
            log.info("File size: " + file.getSize());
            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public Attachment saveAttachmentToDir(MultipartFile file, String dir) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }
//            attachment.setFileName(fileName);
//            attachment.setFileType(file.getContentType());
//            attachment.setData(file.getBytes());

            log.info("File name: " + fileName);
            log.info("File type: " + file.getContentType());
            log.info("File size: " + file.getSize());
            Attachment attachment
                    = new Attachment(fileName,
                    file.getContentType(),
                    file.getBytes());

            file.transferTo(new File(dir + fileName));



            return attachment;
        } catch (Exception e) {
            System.out.println("EEE" + e.getMessage());
            throw new Exception("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
}
