package com.koreait.spring_boot_study.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Getter     @Setter     @ToString

public class StudyRequestDto {
    private String data1 , data2;

}
