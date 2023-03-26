package com.peaksoft.dto;

import com.peaksoft.entity.Company;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter@Setter
public class CourseRequest {
    private String courseName;
    private String durationMonth;
    private Long companyId;

//    private User user;
    private List<Group> groups;
}
