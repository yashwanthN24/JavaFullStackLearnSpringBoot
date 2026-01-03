package com.example.demo.Controllers;

import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Entities.EmployeeEntity;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//if use RestController @ResponseBody included so directly objecfts converted to json


@Controller()
@RequestMapping(path = "employees")
public class EmployeeController {

    private EmployeeRepository empRepo;

    public EmployeeController(EmployeeRepository e){
        this.empRepo = e;
    }



    @GetMapping(path = "/{empId}")
    @ResponseBody
    public EmployeeEntity getEmployee(@PathVariable() Long empId){
//        return new EmployeeDTO(empId , "Yash" , "yash@gmail.com" , 24 , LocalDate.of(2026 , 1 , 1)  , true);
        return this.empRepo.findById(empId).orElse(null);
    }

    @GetMapping(path = "/{empId}/{tempid}")
    @ResponseBody
    public EmployeeDTO getEmployee(@PathVariable("empId") Long Id , @PathVariable("tempid") Long tmpId){
        return new EmployeeDTO(Id , "Yash" , "yash@gmail.com" , 24 , LocalDate.of(2026 , 1 , 1)  , true);
    }

    @GetMapping()
    @ResponseBody
    public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false) Integer age  , @RequestParam(required = false) String sortBy ){
//        return " Hi Age : %d , %s".formatted(age , sortBy);
        return this.empRepo.findAll();
    }

    @GetMapping("/secret")
    @ResponseBody
    public String getSecret(){
        return "Hello world";
    }

    @PostMapping()
    @ResponseBody
    public EmployeeEntity addEmployee(@RequestBody() EmployeeEntity e ){
//        return e;
        return this.empRepo.save(e);
    }

}
