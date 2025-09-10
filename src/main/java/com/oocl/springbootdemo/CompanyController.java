package com.oocl.springbootdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final List<Company> companies = new ArrayList<>();
    private static long id = 0;

    public static void setId(long id) {
        CompanyController.id = id;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createCompanies(@RequestBody Company company) {
        company.setId(++id);
        companies.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", company.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanies(@RequestBody Company updateCompany, @PathVariable long id) {
        Company target = companies.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .orElse(null);

        if (target != null) {
            target.setName(updateCompany.getName());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(target);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanies(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companies.stream().filter(company -> company.getId()==id).findFirst().orElse(null));
    }

    @GetMapping("")
    public ResponseEntity<List<Company>> queryCompanies(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page != null && size != null) {
            return ResponseEntity.status(HttpStatus.OK).body(companies.subList(page*size, page*size+size));
        }
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> deleteCompanies(@PathVariable long id) {
        companies.remove(companies.stream().filter(company -> company.getId()==id).findFirst().orElse(null));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
