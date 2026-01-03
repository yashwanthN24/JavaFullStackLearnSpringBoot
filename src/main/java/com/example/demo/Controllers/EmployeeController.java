package com.example.demo.Controllers;

import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Entities.EmployeeEntity;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    public EmployeeDTO getEmployee(@PathVariable() Long empId){
//        return new EmployeeDTO(empId , "Yash" , "yash@gmail.com" , 24 , LocalDate.of(2026 , 1 , 1)  , true);
        return this.employeeService.getEmployee(empId);
    }

    @GetMapping(path = "/{empId}/{tempid}")
    @ResponseBody
    public EmployeeDTO getEmployee(@PathVariable("empId") Long Id , @PathVariable("tempid") Long tmpId){
        return new EmployeeDTO(Id , "Yash" , "yash@gmail.com" , 24 , LocalDate.of(2026 , 1 , 1)  , true);
    }

    @GetMapping()
    @ResponseBody
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age  , @RequestParam(required = false) String sortBy ){
//        return " Hi Age : %d , %s".formatted(age , sortBy);
        return this.employeeService.getAllEmployees();
    }

    @GetMapping("/secret")
    @ResponseBody
    public String getSecret(){
        return "Hello world";
    }

    @PostMapping()
    @ResponseBody
    public EmployeeDTO addEmployee(@RequestBody() EmployeeDTO e ){
//        return e;
        return this.employeeService.createNewEmployee(e);
    }

}
