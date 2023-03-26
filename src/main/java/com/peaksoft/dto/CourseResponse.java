package com.peaksoft.dto;

import com.peaksoft.entity.Company;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class CourseResponse {
    private Long id;
    private String courseName;
    private String durationMonth;
}
