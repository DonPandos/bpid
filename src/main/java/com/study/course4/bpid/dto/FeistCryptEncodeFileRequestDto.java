package com.study.course4.bpid.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class FeistCryptEncodeFileRequestDto {
    MultipartFile file;
}
