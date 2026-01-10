package com.example.demo.Controllers;

import com.example.demo.DTOs.DepartmentDTO;
import com.example.demo.Services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "departments")
public class DepartmentController {

    private final DepartmentService deptService;

    public DepartmentController(DepartmentService deptService) {
        this.deptService = deptService;
    }


    @GetMapping()
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        return new ResponseEntity<>(this.deptService.getAllDepartments() , HttpStatus.OK);
    }


    @GetMapping(path = "/{deptid}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long deptid){
        return new ResponseEntity<>(this.deptService.getDepartment(deptid) , HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody(required = true) @Valid DepartmentDTO deptDto){
        return  new ResponseEntity<>(this.deptService.createNewDepartment(deptDto) , HttpStatus.CREATED);
    }

    @PutMapping(path = "/{deptId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long deptId , @RequestBody @Valid DepartmentDTO dto){
        return new ResponseEntity<>(this.deptService.updateDepartment(deptId , dto) , HttpStatus.OK);
    }
//
    @DeleteMapping(path = "/{deptId}")
    public ResponseEntity<DepartmentDTO> deleteDepartment(@PathVariable Long deptId){
        return new ResponseEntity<>(this.deptService.deleteDepartment(deptId) , HttpStatus.OK);
    }




}
