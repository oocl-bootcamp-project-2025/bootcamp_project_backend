package com.oocl.springbootdemo.controller;

import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.UpdateCompanyRequest;
import com.oocl.springbootdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("")
    public ResponseEntity<Company> createCompanies(@RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(company));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Company>> queryCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.query(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanies(@PathVariable long id, @RequestBody UpdateCompanyRequest updateCompanyRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(companyService.update(id, updateCompanyRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompanies(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
