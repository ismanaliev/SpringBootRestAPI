package com.peaksoft.controller;

import com.peaksoft.dto.*;
import com.peaksoft.repository.UserRepository;
import com.peaksoft.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teachers")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    @Operation(summary = "get all teachers", description = "get all teachers from database")
    public List<TeacherResponse> teachers(){
        return  teacherService.getAllTeachers();
    }

    @PostMapping()
    @Operation(description = "add new teachers to lms")
    public TeacherResponse save( @RequestBody TeacherRequest teacherRequest){
        return teacherService.save(teacherRequest);
    }
    @PutMapping("{id}")
    @Operation(description = "update an existing teacher by id")
    public TeacherResponse update(@PathVariable("id") Long id, @RequestBody TeacherRequest request){
        return teacherService.updateTeacher(id, request);
    }

    @DeleteMapping("{id}")
    @Operation(description = "delete an existing teacher by id")
    public String delete(@PathVariable("id") Long id){
         teacherService.deleteTeacher(id);
         return "User with this id : " + id + " was deleted";
    }

    @GetMapping("/sizeOfStudents/{id}")
    public List<StudentResponse> students(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size){
        return teacherService.quantityOfStudents(id,page,size);
    }

    @GetMapping("/courses/{id}")
    public List<CourseResponse> courses (@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page", required = false)
    int page, @RequestParam(name = "size", required = false) int size ){
        return teacherService.getCoursesByTeacherId(id,page,size);
    }

}
