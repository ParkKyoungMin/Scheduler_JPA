package com.example.scheduler_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {
    private Long userId;
    private String title;
    private String content;
}
