package com.example.demo.Services;

import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Entities.EmployeeEntity;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    private  ModelMapper mapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }



    public Optional<EmployeeDTO> getEmployee(Long empId) {
//        Optional<EmployeeEntity> employee =  this.employeeRepository.findById(empId);
////        modelMapper Maven dependency is used to convert from entities to dto and from dtos to entities
////        instead of defined toDto() method and toEntity in Entity and DTO classes
//        ModelMapper modelMapper = new ModelMapper(); // bad practice in spring boot creating managing objecrs creation must be managed by spring
////        so we shift this to a config file having @configuration and @Bean to tell spring to allow dependency injection of this
//        return employee.map(emp -> modelMapper.map(emp , EmployeeDTO.class));

        return this.employeeRepository.findById(empId).map(emp -> mapper.map(emp , EmployeeDTO.class));
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

    public EmployeeDTO updateEmployeeById(Long empId, EmployeeDTO updatedEmployee) {
//        find the employee by id first
        boolean isExists = this.employeeRepository.existsById(empId);
        if(!isExists) throw new ResourceNotFoundException("Employee with id %d is not found".formatted(empId));
        EmployeeEntity employeeEntity = mapper.map(updatedEmployee , EmployeeEntity.class);
        employeeEntity.setId(empId);
        EmployeeEntity savedEmployeeEntity = this.employeeRepository.save(employeeEntity);
        return mapper.map(savedEmployeeEntity , EmployeeDTO.class);

    }

    public boolean deleteEmployeeById(Long empId) {
//        check if the employee exists
       isExistByEmployeeId(empId);
        this.employeeRepository.deleteById(empId);
        return true;
    }

    public void isExistByEmployeeId(Long empId){
        boolean exists =  this.employeeRepository.existsById(empId);
        if(!exists) throw new ResourceNotFoundException("Employee with id %d is not found".formatted(empId));

    }

    public EmployeeDTO updateEmployeePartiallyById(Long empId , Map<String , Object> updates) {
         isExistByEmployeeId(empId);
        EmployeeEntity employeeEntity = employeeRepository.findById(empId).get();
        updates.forEach((field , value)-> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class , field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated , employeeEntity , value);
        });
        return mapper.map(employeeRepository.save(employeeEntity) , EmployeeDTO.class);
    }
}
