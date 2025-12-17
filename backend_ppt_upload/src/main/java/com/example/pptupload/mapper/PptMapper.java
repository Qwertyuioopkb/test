package com.example.pptupload.mapper;

import com.example.pptupload.entity.PptFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PptMapper {
    @Insert("INSERT INTO ppt_files(filename, path, upload_time) VALUES(#{filename}, #{path}, #{uploadTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(PptFile ppt);

    @Select("SELECT id, filename, path, upload_time as uploadTime FROM ppt_files ORDER BY upload_time DESC")
    List<PptFile> findAll();

    @Select("SELECT * FROM ppt_files WHERE id=#{id}")
    PptFile findById(Long id);

}


