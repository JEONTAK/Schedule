package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return scheduleRepository.findScheduleByIdOrElseThrow(id);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String contents, String name, String password) {
        if (contents == null && name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content must be either of them.");
        }

        if (!isMatch(id, password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Does not match password");
        }

        int updatedRow = 0;

        if (contents != null && name != null) {
            updatedRow = scheduleRepository.updateSchedule(id, contents, name);
        } else if (name != null) {
            updatedRow = scheduleRepository.updateName(id, name);
        } else {
            updatedRow = scheduleRepository.updateContents(id, contents);
        }

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }

    private boolean isMatch(Long id, String password) {
        ScheduleResponseDto schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return Objects.equals(schedule.getPassword(), password);
    }

    @Override
    public void deleteSchedule(Long id) {
        int deleteRow = scheduleRepository.deleteSchedule(id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
