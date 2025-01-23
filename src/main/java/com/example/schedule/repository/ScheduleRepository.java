package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(String name, LocalDateTime editDate);

    List<ScheduleResponseDto> findAllSchedules();

    List<ScheduleResponseDto> findByName(String name);

    List<ScheduleResponseDto> findByEditDate(LocalDateTime date);

    Optional<Schedule> findScheduleById(Long id);

    ScheduleResponseDto findScheduleByIdOrElseThrow(Long id);

    int updateContents(Long id, String contents);

    int updateName(Long id, String name);

    int updateSchedule(Long id, String contents, String name);

    int deleteSchedule(Long id);
}
