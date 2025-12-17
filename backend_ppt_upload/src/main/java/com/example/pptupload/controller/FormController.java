package com.example.pptupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/api/form")
public class FormController {

    @PostMapping("/submit")
    public String handleForm(@RequestParam Map<String, String> formParams) {
        // 处理所有表单参数
        System.out.println(formParams);
        return "";
    }




}
