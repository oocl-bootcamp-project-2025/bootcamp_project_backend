package com.oocl.springbootdemo.service;

import com.oocl.springbootdemo.exception.*;
import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void should_create_when_post_given_a_valid_body() {
        Company company = new Company();
        company.setName("tom");

        when(companyRepository.save(company)).thenReturn(company);
        Company createdCompany = companyService.create(company);

        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void should_return_company_when_get_given_exist_company_id() {
        Company company = new Company();
        company.setId(1);
        company.setName("tom");

        when(companyRepository.findById(1)).thenReturn(company);
        Company foundCompany = companyService.get(1);

        assertEquals(company, foundCompany);
        verify(companyRepository, times(1)).findById(1);
    }

    @Test
    void should_return_company_when_get_given_not_exist_company_id() {
        when(companyRepository.findById(10)).thenReturn(null);

        assertThrows(CompanyNotFoundException.class,
                () -> companyService.get(10)
        );
    }

    @Test
    void should_return_company_list_when_get_given_page_and_size() {
        when(companyRepository.query(0, 2)).thenReturn(List.of(new Company(), new Company()));
        List<Company> result = companyService.query(0, 2);

        assertEquals(2, result.size());
        verify(companyRepository, times(1)).query(0, 2);
    }
}