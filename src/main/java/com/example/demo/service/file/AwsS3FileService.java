package com.example.demo.service.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.global.exception.customexception.CommonCustomException;
import com.example.demo.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3FileService {

    private final AmazonS3Client amazonS3Client;
    private final FileUtil fileUtil;
    private static final String FILE_PREFIX = "profile/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 클라이언트로부터 MultipartFile 타입의 이미지 파일을 전달받아 File 객체로 전환한 후 S3 업로드
    public String uploadImage(MultipartFile file) throws IOException {
        // 이미지 파일 검증
        fileUtil.validationImageType(file);

        // MultipartFile -> File 변환 및 새로운 파일명 생성
        File uploadFile = fileUtil.convert(file)
                .orElseThrow(() -> CommonCustomException.INTERNAL_SERVER_ERROR);

        return upload(uploadFile);
    }

    private String upload(File uploadFile) {
        String storedFileName = FILE_PREFIX + fileUtil.makeNewFileName(uploadFile.getName());
        String uploadUrl = putS3(uploadFile, storedFileName);

        // 로컬에 생성된 임시 File 객체 삭제
        fileUtil.removeNewFile(uploadFile);

        // 업로드된 파일의 S3 URL 반환
        return uploadUrl;
    }

    // AWS S3 버킷에 파일 저장
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 이미지 삭제
    public void deleteImage(String fileName) {
        String key = FILE_PREFIX + fileName.substring(fileName.lastIndexOf('/') + 1);
        deleteS3(key);
    }

    // AWS S3 버킷에서 파일 삭제
    private void deleteS3(String key) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    public String generatePreSignedUrl(String storedFileUrl) {
        String encodedFileName = storedFileUrl.substring(storedFileUrl.lastIndexOf('/') + 1);

        String decodedFileName = URLDecoder.decode(encodedFileName, StandardCharsets.UTF_8);

        String s3Key = FILE_PREFIX + decodedFileName;

        Date expiration = new Date();
        long expTimeMillis = System.currentTimeMillis();
        expTimeMillis += 1000 * 60 * 5;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, s3Key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
