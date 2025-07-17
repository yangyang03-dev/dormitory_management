package com.dorm.service;

import com.dorm.model.Contract;
import com.dorm.repository.ContractRepository;
import com.dorm.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Contract with ID " + id + " does not exist.");
        }
        repo.deleteById(id);
    }
    public void createContract(UUID studentId, String roomNumber) {
        // No room availability logic here
        Contract contract = new Contract();
        contract.setId(UUID.randomUUID());
        contract.setStudentId(studentId);
        contract.setRoomNumber(roomNumber);
        contract.setSignedAt(new Timestamp(System.currentTimeMillis()));
        contract.setContractUrl(""); // optional for now

        repo.save(contract);
    }
}