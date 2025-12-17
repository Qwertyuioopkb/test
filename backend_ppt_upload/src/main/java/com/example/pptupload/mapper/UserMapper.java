package com.example.pptupload.mapper;

import com.example.pptupload.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    void insertUser(User user);
    void updatePhone(@Param("id") Long id, @Param("phone") String phone);
}
