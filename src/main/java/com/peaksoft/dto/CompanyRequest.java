package com.peaksoft.dto;

import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class CompanyRequest {
    private String companyName;

    private String locatedCountry;
    private List<Course> courses;
}
