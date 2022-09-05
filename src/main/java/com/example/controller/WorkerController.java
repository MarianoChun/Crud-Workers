package com.example.controller;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import com.example.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class WorkerController {

    private WorkerService workerService;
    @PostMapping(path = "/workers")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addWorker(@RequestBody Worker worker){
        if(workerHasExistingEmail(worker)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email given by the worker already exists, try another");
        }
        if(workerService.saveWorker(worker) != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The creation of the worker has failed");
    }

    private boolean workerHasExistingEmail(Worker worker) {
        Iterator<Worker> workersIterator = workerService.getAllWorkers().iterator();
        String actualWorkerEmail;
        boolean existingEmail = false;

        while(workersIterator.hasNext()){
            actualWorkerEmail = workersIterator.next().getEmail();
            existingEmail = existingEmail || actualWorkerEmail.equals(worker.getEmail());
        }

        return  existingEmail;
    }

    @PutMapping(path = "/workers/{id}")
    @ResponseBody
    public ResponseEntity<String> modifyWorker(@PathVariable Long id,
                               @RequestBody Worker worker){
        Worker selectedWorker = workerService.getWorker(id);

        try {
            workerService.modifyWorker(id, worker);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("The requested worker to modify doesn't exists");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Worker with id:" + selectedWorker.getId() + " modified!");
    }

    @GetMapping(path = "/workers/{id}")
    @ResponseBody
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Worker worker = workerService.getWorker(id);

        if(worker == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(worker);

    }
    @GetMapping(path = "/workers")
    @ResponseBody
    public ResponseEntity<Iterable<Worker>> getAllWorkers(){
        if(workerService.getAllWorkers() == null){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(workerService.getAllWorkers());
    }

    @DeleteMapping(path = "/workers/{id}")
    @ResponseBody
    public ResponseEntity<String> removeWorker(@PathVariable Long id){
        Worker workerSearched = workerService.getWorker(id);
        if(workerSearched == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The worker with the given id doesn't exists or was already deleted");
        }

        workerService.deleteWorker(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Worker successfully deleted from database!");
    }
}
