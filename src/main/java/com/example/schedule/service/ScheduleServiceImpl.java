package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        LocalDateTime createDate = LocalDateTime.now();
        Schedule schedule = new Schedule(requestDto.getContents(), requestDto.getName(), requestDto.getPassword(),
                createDate, createDate);

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String name, String editDate) {
        if (name == null && editDate == null) {
            return scheduleRepository.findAllSchedules();
        }

        if (name != null && editDate == null) {
            return scheduleRepository.findByName(name);
        }

        LocalDateTime date = LocalDateTime.parse(editDate);
        if (name == null) {
            return scheduleRepository.findByEditDate(date);
        }

        return scheduleRepository.findAllSchedules(name, date);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository.findScheduleById(id);
    }
}
