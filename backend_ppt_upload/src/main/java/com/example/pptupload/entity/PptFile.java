package com.example.pptupload.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PptFile {
    private Long id;
    private String filename;
    private String path;
    private LocalDateTime uploadTime;

}
