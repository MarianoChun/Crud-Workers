package com.example.controller;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/workers")
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addWorker(@RequestBody Worker worker){
        if(workerHasExistingEmail(worker)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email given by the worker already exists, try another");
        }
        if(workerRepository.save(worker) != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The creation of the worker has failed");
    }

    private boolean workerHasExistingEmail(Worker worker) {
        Iterator<Worker> workersIterator = getAllWorkers().getBody().iterator();
        String actualWorkerEmail;
        boolean existingEmail = false;

        while(workersIterator.hasNext()){
            actualWorkerEmail = workersIterator.next().getEmail();
            existingEmail = existingEmail || actualWorkerEmail.equals(worker.getEmail());
        }

        return  existingEmail;
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<String> modifyWorker(@PathVariable Long id,
                               @RequestBody Worker worker){

        Worker selectedWorker = getWorker(id).getBody();
        Worker newWorker = worker;

        if(selectedWorker == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested worker to modify doesn't exists");
        }

        selectedWorker.setName(newWorker.getName());
        selectedWorker.setSurname(newWorker.getSurname());
        selectedWorker.setEmail(newWorker.getEmail());
        selectedWorker.setPassword(newWorker.getPassword());
        selectedWorker.setOccupation(newWorker.getOccupation());

        workerRepository.save(selectedWorker);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Worker with id:" + selectedWorker.getId() + " modified!");
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        if(worker.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(worker.get());

    }
    @GetMapping
    @ResponseBody
    public ResponseEntity<Iterable<Worker>> getAllWorkers(){
        if(workerRepository.findAll() == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(workerRepository.findAll());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<String> removeWorker(@PathVariable Long id){
        Optional<Worker> workerSearched = workerRepository.findById(id);
        if(workerSearched.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The worker with the given id doesn't exists or was already deleted");
        }

        workerRepository.delete(workerSearched.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Worker successfully deleted from database!");
    }
}
