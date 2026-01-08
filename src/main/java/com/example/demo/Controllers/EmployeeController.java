package com.example.demo.Controllers;

import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Entities.EmployeeEntity;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

//if use RestController @ResponseBody included so directly objecfts converted to json


@Controller()
@RequestMapping(path = "employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{empId}")
    @ResponseBody
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable() Long empId){
//        return new EmployeeDTO(empId , "Yash" , "yash@gmail.com" , 24 , LocalDate.of(2026 , 1 , 1)  , true);
        Optional<EmployeeDTO> e =  this.employeeService.getEmployee(empId);
        return e.map(employeeDTO -> ResponseEntity.ok(employeeDTO)).orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping(path = "/{empId}/{tempid}")
//    @ResponseBody
//    public EmployeeDTO getEmployee(@PathVariable("empId") Long Id , @PathVariable("tempid") Long tmpId){
//        return new EmployeeDTO(Id , "Yash" , "yash@gmail.com" , 24 , LocalDate.of(2026 , 1 , 1)  , true);
//    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age  , @RequestParam(required = false) String sortBy ){
//        return " Hi Age : %d , %s".formatted(age , sortBy);
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @GetMapping("/secret")
    @ResponseBody
    public String getSecret(){
        return "Hello world";
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody() @Valid EmployeeDTO e ){
//        return e;
        EmployeeDTO createdEmployee =  this.employeeService.createNewEmployee(e);
        return new ResponseEntity<>(createdEmployee , HttpStatus.CREATED);
    }

    @PutMapping(path = "/{empId}")
    @ResponseBody
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long empId , @RequestBody @Valid EmployeeDTO updatedEmployee){
        return ResponseEntity.ok(this.employeeService.updateEmployeeById(empId , updatedEmployee));
    }

    @DeleteMapping(path = "/{empId}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long empId){
        boolean isDeleted = this.employeeService.deleteEmployeeById(empId);
        if(isDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();

    }

    @PatchMapping(path = "/{empId}")
    @ResponseBody
    public ResponseEntity<EmployeeDTO> updateEmployeePartially(@PathVariable Long empId , @RequestBody Map<String , Object> updates){
        EmployeeDTO employeeDto =  this.employeeService.updateEmployeePartiallyById(empId , updates);
        if(employeeDto == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDto);
    }

}
