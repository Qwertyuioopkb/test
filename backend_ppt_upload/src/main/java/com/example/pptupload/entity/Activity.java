package com.example.pptupload.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class Activity {

    private String name;              // 活动名称
    private String region;            // 活动区域（shanghai/beijing）

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate date1;          // 选择的日期（前端是 date 类型）

    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime date2;          // 选择的时间（前端是 time 类型）

    private Boolean delivery;         // 是否即时配送
    private List<String> type;        // 活动性质（多选数组）
    private String resource;          // 特殊资源
    private String desc;              // 活动形式（文本）

}