package ru.crm.report_service.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService {

    private final MinioClient minioClient;

    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String objectName, byte[] fileBytes) {
        try {
            ensureBucketExists();

            try (InputStream is = new ByteArrayInputStream(fileBytes)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(is, (long) fileBytes.length, -1L)
                                .contentType("application/octet-stream")
                                .build()
                );
            }

            log.info("Файл '{}' ({} байт) загружен в бакет '{}'",
                    objectName, fileBytes.length, bucketName);

        } catch (MinioException | IOException | GeneralSecurityException e) {
            log.error("Ошибка загрузки файла в '{}' бакет '{}'",
                    objectName, bucketName, e);
            throw new IllegalStateException("Не удалось загрузить объект в S3: "
                    + objectName, e);
        }
    }

    private void ensureBucketExists()
            throws MinioException, IOException, GeneralSecurityException {
        boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );

        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
            );
            log.info("Создан бакет '{}'", bucketName);
        }
    }

}
