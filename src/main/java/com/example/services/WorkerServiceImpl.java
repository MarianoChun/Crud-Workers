package com.example.services;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkerServiceImpl implements WorkerService{

    @Autowired
    private WorkerRepository workerRepository;


    @Override
    public Worker getWorker(Long id) {
        return workerRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public void modifyWorker(Long id, Worker worker) {
        Worker selectedWorker = getWorker(id);

        if(selectedWorker == null){
            throw new IllegalArgumentException("The requested worker to modify doesn't exists");
        }

        selectedWorker.setName(worker.getName());
        selectedWorker.setSurname(worker.getSurname());
        selectedWorker.setEmail(worker.getEmail());
        selectedWorker.setPassword(worker.getPassword());
        selectedWorker.setOccupation(worker.getOccupation());

        saveWorker(selectedWorker);
    }

    @Override
    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }
}
