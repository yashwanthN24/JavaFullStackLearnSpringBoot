package com.example.demo.Services;


import com.example.demo.DTOs.DepartmentDTO;
import com.example.demo.DTOs.EmployeeDTO;
import com.example.demo.Entities.DepartmentEntity;
import com.example.demo.Entities.EmployeeEntity;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository deptRepo ;
    private ModelMapper mapper;


    public DepartmentService(DepartmentRepository deptRepo ,  ModelMapper mapper) {
        this.deptRepo = deptRepo;
        this.mapper = mapper;
    }


    public DepartmentDTO createNewDepartment(DepartmentDTO deptDto) {
        DepartmentEntity deptEntity = mapper.map(deptDto , DepartmentEntity.class);
        DepartmentEntity savedDeptEntity = this.deptRepo.save(deptEntity);
        return this.mapper.map(savedDeptEntity , DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> deptEntities = this.deptRepo.findAll();
        return deptEntities.stream().map(deptEntity -> mapper.map(deptEntity , DepartmentDTO.class)).collect(Collectors.toList());
    }

    public DepartmentDTO getDepartment(Long deptid) {
        DepartmentEntity dept =  this.deptRepo.findById(deptid).orElseThrow(()-> new ResourceNotFoundException("Department with id %d not found".formatted(deptid)));
        return this.mapper.map(dept , DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long deptId, DepartmentDTO dto) {
        isExists(deptId);
        DepartmentEntity deptEntity = this.deptRepo.findById(deptId).get();
        deptEntity.setId(deptId);
        deptEntity.setActive(dto.getActive());
        deptEntity.setTitle(dto.getTitle());
        DepartmentEntity savedDeptEntity = this.deptRepo.save(deptEntity);
        return mapper.map(savedDeptEntity , DepartmentDTO.class);
    }

    private void isExists(Long id){
        boolean exist = this.deptRepo.existsById(id);
        if(!exist) throw new ResourceNotFoundException("Department with Id %d not found".formatted(id));
    }

    public DepartmentDTO deleteDepartment(Long deptId) {
        isExists(deptId);
        DepartmentEntity deptEntity = this.deptRepo.findById(deptId).get();
        this.deptRepo.deleteById(deptId);
        return this.mapper.map(deptEntity , DepartmentDTO.class);
    }
}
