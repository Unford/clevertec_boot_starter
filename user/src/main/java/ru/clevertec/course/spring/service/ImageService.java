package ru.clevertec.course.spring.service;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.course.spring.config.MinioProperties;
import ru.clevertec.course.spring.exception.ImageUploadException;

import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    private static final List<String> imageTypes = List.of(MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);

    public String upload(MultipartFile file, String filename) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }
        if (!imageTypes.contains(file.getContentType())) {
            throw new ImageUploadException("File isn't an image, please provide png, jpeg or gif file");
        }
        filename = "%s.%s".formatted(filename, getExtension(file));

        createBucket();
        saveImage(file, filename);
        return filename;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    private void createBucket() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .build());

            }
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: " + e.getMessage());
        }
    }

    private void saveImage(MultipartFile file, String fileName) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: " + e.getMessage());
        }
    }

    public byte[] getImage(String fileName) {
        if (fileName == null) return new byte[0];
        try {

            GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(fileName)
                    .build());
            return objectResponse.readAllBytes();

        } catch (ErrorResponseException e) {
            if (e.response().code() == HttpStatus.NOT_FOUND.value()) {
                return new byte[0];
            }
            throw new ImageUploadException("Image get failed: " + e.getMessage());
        } catch (Exception e) {
            throw new ImageUploadException("Image get failed: " + e.getMessage());
        }
    }

    public String getEncodedImage(String fileName) {
        byte[] bytes = getImage(fileName);
        return Base64.getEncoder().encodeToString(bytes);
    }

}
