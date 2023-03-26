package com.peaksoft.dto;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter@Setter
public class GroupRequest {
    private String groupName;
    private String dateOfStart;
    private String dateOfFinish;
    private Long courseId;
    private List<Course>courses;
}
