package com.example.demo.global.util;

import com.example.demo.global.exception.customexception.FileCustomException;
import com.example.demo.global.log.PMLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.example.demo.global.log.PMLog.USER_PROFILE;

@Slf4j
@Component
public class FileUtil {

    // MultipartFile 객체를 File 객체로 변환
    public Optional<File> convert(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        try{
            convertFile.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }catch (IOException e){
            PMLog.e(USER_PROFILE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // 새로운 파일명 생성 (랜덤 UUID + 원본 파일명)
    public String makeNewFileName(String originFileName) {
        String newFileName = UUID.randomUUID() + originFileName;
        return newFileName;
    }

    // 임시 저장된 파일 삭제
    public void removeNewFile(File file) {
        if (file.delete()) {
            log.info("Success File Remove.");
            return;
        }
        log.info("Failed File Remove.");
    }

    // 이미지 파일 검증
    public void validationImageType(MultipartFile multipartFile) {
        String fileType = multipartFile.getContentType();

        if (!fileType.equals(MediaType.IMAGE_JPEG_VALUE)
                && !fileType.equals(MediaType.IMAGE_PNG_VALUE)
                && !fileType.equals("image/jpg")) {
            throw FileCustomException.INVALID_IMAGE_TYPE;
        }
    }
}
