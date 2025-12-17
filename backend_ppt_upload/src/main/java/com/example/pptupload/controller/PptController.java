package com.example.pptupload.controller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.ContentDisposition;
import java.nio.charset.StandardCharsets;

import com.example.pptupload.entity.PptFile;
import com.example.pptupload.mapper.PptMapper;
import com.example.pptupload.service.PptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ppt")
public class PptController {
     @Autowired
     PptMapper PptMapper;
    private final PptService pptService;

    @Value("${app.upload-dir}")
    private String uploadDir;

    public PptController(PptService pptService) {
        this.pptService = pptService;
    }

    @PostMapping("/upload")
    public PptFile uploadPpt(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }
        // 确保上传文件存在
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String original = file.getOriginalFilename();
        String filename = System.currentTimeMillis() + "_" + original;
        Path dest = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), dest);

        PptFile ppt = new PptFile();
        ppt.setFilename(original);
        ppt.setPath(dest.toString());
        pptService.save(ppt);
        return ppt;
    }
    @GetMapping("/preview/{id}")
    public Map<String, Object> preview(@PathVariable("id") Long id) {
        PptFile ppt = PptMapper.findById(id);
        Map<String, Object> result = new HashMap<>();
        if (ppt != null) {
            // 本地测试用的访问地址，确保公网能访问
            String fileUrl = "http://localhost:8080/api/ppt/view/" + ppt.getId();
            String previewUrl = "https://docs.google.com/gview?url=" + fileUrl + "&embedded=true";
            result.put("url", previewUrl);
        } else {
            result.put("url", null);
        }
        return result;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> view(@PathVariable("id") Long id) {
        PptFile ppt = PptMapper.findById(id);
        if (ppt == null) {
            return ResponseEntity.notFound().build();
        }
        File file = new File(ppt.getPath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        // 这里不要再设置 Content-Disposition，避免浏览器下载
        MediaType mediaType = ppt.getFilename().endsWith(".ppt") ?
                MediaType.parseMediaType("application/vnd.ms-powerpoint") :
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.presentationml.presentation");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }





    @GetMapping("/list")
    public List<PptFile> list() {
        return pptService.findAll();
    }



}
