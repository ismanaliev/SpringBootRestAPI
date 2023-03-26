package com.peaksoft.controller;

import com.peaksoft.dto.*;
import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;
import com.peaksoft.repository.CompanyRepository;
import com.peaksoft.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "get all companies", description = "get all companies from database")
    public List<CompanyResponse>getAll(){
        return companyService.getAllCompany();
    }

    @PostMapping
    @Operation(description = "add new companies to lms")
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest){
        return companyService.save(companyRequest);
    }
    @GetMapping("{id}")
    @Operation(description = "find companies by id")
    public CompanyResponse getById (@PathVariable("id") Long id){
        return  companyService.getById(id);
    }
    @PutMapping("{id}")
    @Operation(description = "update an existing company by id")
    public CompanyResponse update(@PathVariable("id") Long id,@RequestBody CompanyRequest companyRequest){
        return companyService.updateCompany(id,companyRequest);
    }
    @DeleteMapping("{id}")
    @Operation(description = "delete an existing student by id")
    public String delete(@PathVariable("id") Long id){
        companyService.deleteCompany(id);
        return "Company with this id : " + id + " was deleted";
    }
    @GetMapping("/studentsOfCompany/{id}")
    public List<StudentResponse> students(@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page"
            , required = false) int page, @RequestParam(name = "size", required = false) int size){
        return companyService.getStudentsByCompanyId(id,page,size);
    }

   @GetMapping("/coursesOfCompany/{id}")
    public List<CourseResponse> courses (@PathVariable(name = "id", required = false) Long id, @RequestParam(value = "page",required = false)
                                         int page, @RequestParam(name = "size", required = false) int size){
        return companyService.getCoursesByCompanyId(id,page,size);
   }
}
