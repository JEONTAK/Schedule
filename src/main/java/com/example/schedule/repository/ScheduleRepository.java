package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(String name, LocalDateTime editDate);

    List<ScheduleResponseDto> findAllSchedules();

    List<ScheduleResponseDto> findByName(String name);

    List<ScheduleResponseDto> findByEditDate(LocalDateTime date);

    ScheduleResponseDto findScheduleById(Long id);
}
