package com.example.demo.Services;

import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Entities.EmployeeEntity;
import com.example.demo.Repository.EmployeeRepository;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    private  ModelMapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }



    public EmployeeDTO getEmployee(Long empId) {
        EmployeeEntity employee =  this.employeeRepository.findById(empId).orElse(null);
//        modelMapper Maven dependency is used to convert from entities to dto and from dtos to entities
//        instead of defined toDto() method and toEntity in Entity and DTO classes
        ModelMapper modelMapper = new ModelMapper(); // bad practice in spring boot creating managing objecrs creation must be managed by spring
//        so we shift this to a config file having @configuration and @Bean to tell spring to allow dependency injection of this
        return modelMapper.map(employee , EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
         List<EmployeeEntity> allEmployeeEntities = this.employeeRepository.findAll();
         return allEmployeeEntities.stream().map(entity -> mapper.map(entity , EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO e) {
        EmployeeEntity toSaveEntity = mapper.map(e , EmployeeEntity.class);
        EmployeeEntity savedEntity = this.employeeRepository.save(toSaveEntity);
        return mapper.map(savedEntity , EmployeeDTO.class);
    }
}
