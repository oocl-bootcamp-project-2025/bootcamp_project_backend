package com.oocl.springbootdemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companys")
public class CompanyController {
    private List<Company> companys = new ArrayList<>();

    @PostMapping("")
    public ResponseEntity<Map<String, Long>> createcompanys(@RequestBody Company company) {
        company.setId(companys.size() + 1);
        companys.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", company.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompanys(@RequestBody Company updateCompany, @PathVariable long id) {
        Company target = companys.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .orElse(null);

        if (target != null) {
            target.setName(updateCompany.getName());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(target);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(companys.stream().filter(company -> company.getId()==id).findFirst().orElse(null));
    }

    @GetMapping("")
    public ResponseEntity<List<Company>> queryCompanys(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page != null && size != null) {
            return ResponseEntity.status(HttpStatus.OK).body(companys.subList(page*size, page*size+size));
        }
        return ResponseEntity.status(HttpStatus.OK).body(companys);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> deleteCompanys(@PathVariable long id) {
        companys.remove(companys.stream().filter(company -> company.getId()==id).findFirst().orElse(null));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    static class Company {
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
