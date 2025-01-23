package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String contents;
    private String name;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public ScheduleResponseDto(Long id, Schedule schedule) {
        this.id = id;
        this.contents = schedule.getContents();
        this.name = schedule.getName();
        this.password = schedule.getPassword();
        this.createDate = schedule.getCreateDate();
        this.editDate = schedule.getEditDate();
    }

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.contents = schedule.getContents();
        this.name = schedule.getName();
        this.password = schedule.getPassword();
        this.createDate = schedule.getCreateDate();
        this.editDate = schedule.getEditDate();
    }

}
