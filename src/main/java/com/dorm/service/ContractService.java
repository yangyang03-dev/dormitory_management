package com.dorm.service;

import com.dorm.model.Contract;
import com.dorm.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository repo;

    public ContractService(ContractRepository repo) {
        this.repo = repo;
    }

    public List<Contract> getAll() {
        return repo.findAll();
    }

    public Contract create(Contract contract) {
        contract.setId(UUID.randomUUID());
        contract.setSignedAt(new Timestamp(System.currentTimeMillis()));
        return repo.save(contract);
    }
}