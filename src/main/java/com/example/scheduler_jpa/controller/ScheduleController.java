package com.example.scheduler_jpa.controller;

import com.example.scheduler_jpa.entity.User;
import com.example.scheduler_jpa.dto.ScheduleRequestDto;
import com.example.scheduler_jpa.entity.Schedule;
import com.example.scheduler_jpa.service.ScheduleService;
import com.example.scheduler_jpa.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    public ScheduleController(ScheduleService scheduleService, UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        User user = userService.getUserById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Schedule schedule = new Schedule();
        schedule.setUser(user);
        schedule.setTitle(requestDto.getTitle());
        schedule.setContent(requestDto.getContent());

        return scheduleService.createSchedule(schedule);
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다."));
    }

    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        Schedule existingSchedule = scheduleService.getScheduleById(id)
                .orElseThrow(() -> new RuntimeException("일정을 찾을 수 없습니다."));

        existingSchedule.setTitle(schedule.getTitle());
        existingSchedule.setContent(schedule.getContent());

        return scheduleService.updateSchedule(existingSchedule);
    }


    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
