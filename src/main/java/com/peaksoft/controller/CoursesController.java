package com.peaksoft.controller;

import com.peaksoft.dto.*;
import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.service.CompanyService;
import com.peaksoft.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CoursesController {
    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "get all courses", description = "get all courses from database")
    public List<CourseResponse>getAll(){
        return courseService.getAllCourse();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "add new courses to lms")
    public CourseResponse save(@RequestBody CourseRequest courseRequest){
        return courseService.addCourse(courseRequest);
    }
    @GetMapping("{id}")
    @Operation(description = "find courses by id")
    public CourseResponse getById (@PathVariable("id") Long id){
        return  courseService.getById(id);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(description = "update an existing courses by id")
    public CourseResponse update(@PathVariable("id") Long id,@RequestBody CourseRequest courseRequest){
        return courseService.updateCourse(id,courseRequest);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "delete an existing course by id")
    public String delete(@PathVariable("id") Long id){
        courseService.deleteCourse(id);
        return "Course with this id : " + id + " was deleted";
    }
    @GetMapping("/teachersOfCourse/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public List<TeacherResponse> students(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size){
        return courseService.getTeachersByCourseId(id,page,size);
    }

    @GetMapping("/groupsOfCourse/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public List<GroupResponse> courses(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size){
        return courseService.getGroupsByCourseId(id, page, size);
    }
}
