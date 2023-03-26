package com.peaksoft.controller;

import com.peaksoft.dto.*;
import com.peaksoft.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(summary = "get all groups", description = "get all groups from database")
    public List<GroupResponse>getAll(){
        return groupService.getAllGroups();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "add new groups to lms")
    public GroupResponse save( @RequestBody GroupRequest groupRequest){
        return groupService.addGroup(groupRequest);
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(description = "find groups by id")
    public GroupResponse getById (@PathVariable("id") Long id){
        return  groupService.getById(id);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @Operation(description = "update an existing groups by id")
    public GroupResponse update(@PathVariable("id") Long id,@RequestBody GroupRequest groupRequest){
        return groupService.updateGroup(id,groupRequest);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(description = "delete an existing group by id")
    public String delete(@PathVariable("id") Long id){
        groupService.deleteGroup(id);
        return "Group with this id : " + id + " was deleted";
    }
    @GetMapping("/studentsOfGroup/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public List<StudentResponse> students(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size){
        return groupService.getStudentsByGroupId(id,page,size);
    }

    @GetMapping("/coursesOfGroup/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public List<CourseResponse> courses(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size){
        return groupService.getCoursesByGroupId(id, page, size);
    }
}
