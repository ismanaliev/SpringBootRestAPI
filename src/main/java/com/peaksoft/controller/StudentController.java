package com.peaksoft.controller;
import com.peaksoft.dto.StudentRequest;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
@Tag(name = "StudentController",description = "User with role Admin can create, update, get and delete students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "get all students", description = "get all students from database")
    public List<StudentResponse>getAll(){
        return studentService.getAllStudents();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "add new students to lms")
    public StudentResponse save(@RequestBody StudentRequest studentRequest){
        return studentService.save(studentRequest);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "find students by id")
    public StudentResponse getById (@PathVariable("id") Long id){
        return  studentService.getById(id);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "update an existing student by id")
    public StudentResponse update(@PathVariable("id") Long id,@RequestBody StudentRequest request){
        return studentService.update(id,request);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "delete an existing student by id")
    public String delete(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return "User with this id : " + id + " was deleted";
    }
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<StudentResponse>search (@RequestParam(name = "text", required = false) String text, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size) {
        return studentService.search(text,page,size);
    }

}
