package com.dorm.controller;

import com.dorm.model.Contract;
import com.dorm.service.ContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService service;

    public ContractController(ContractService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contract> getAllContracts() {
        return service.getAll();
    }

    @PostMapping
    public Contract createContract(@RequestBody Contract contract) {
        return service.create(contract);
    }
}