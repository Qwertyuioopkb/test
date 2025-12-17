package com.example.pptupload.service;

import com.example.pptupload.mapper.PptMapper;
import com.example.pptupload.entity.PptFile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PptService {
    private final PptMapper mapper;

    public PptService(PptMapper mapper) {
        this.mapper = mapper;
    }

    public void save(PptFile ppt) {
        ppt.setUploadTime(LocalDateTime.now());
        mapper.insert(ppt);
    }

    public List<PptFile> findAll() {
        return mapper.findAll();
    }



}
