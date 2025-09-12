package com.mingles.web.service.impl;

import com.mingles.web.exception.FileUploadException;
import com.mingles.web.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;


    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String key = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
            return "https://" + bucketName + ".s3.ap-southeast-1.amazonaws.com/" + key;
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload file to S3", e);
        }

    }
}
