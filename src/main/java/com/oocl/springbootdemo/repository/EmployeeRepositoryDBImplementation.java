//package com.oocl.springbootdemo.repository;
//
//import com.oocl.springbootdemo.object.Employee;
//import com.oocl.springbootdemo.repository.dao.EmployeeJpaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class EmployeeRepositoryDBImplementation implements EmployeeRepository{
//
//    @Autowired
//    private EmployeeJpaRepository employeeJpaRepository;
//
//    public void clearAll() {
//        employeeJpaRepository.deleteAll();
//    }
//
//    public Employee save(Employee employee) {
//        employee.setActiveStatus(true);
//        return employeeJpaRepository.save(employee);
//    }
//
//    public Employee findById(long id) {
//        return employeeJpaRepository.findById(id).orElse(null);
//    }
//
//    public Employee update(Employee target, Employee updateEmployee) {
//        updateEmployee.setId(target.getId());
//        return employeeJpaRepository.save(updateEmployee);
//    }
//
//    public List<Employee> query(String gender, Integer page, Integer size) {
//        if (gender != null) {
//            return employeeJpaRepository.findAllByGender(gender);
//        }
//        if (page != null && size != null) {
//            return employeeJpaRepository.findAll(PageRequest.of(page-1, size)).toList();
//        }
//        return employeeJpaRepository.findAll();
//    }
//
//    public Employee delete(Employee target) {
//        target.setActiveStatus(false);
//        return employeeJpaRepository.save(target);
//    }
//
//    public boolean hasDuplicatedNameAndGender(Employee target) {
//        return employeeJpaRepository.findAll()
//                .stream()
//                .anyMatch(employee -> employee.getName().equals(target.getName()) && employee.getGender().equals(target.getGender()));
//    }
//}
