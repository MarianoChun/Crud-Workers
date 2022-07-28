package com.example.controller;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/workers")
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;

    @PostMapping
    @ResponseBody
    @Transactional
    public ResponseEntity<String> addWorker(@RequestParam String name,
                            @RequestParam String surname,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String occupation){

        Worker newWorker = new Worker();
        newWorker.setName(name);
        newWorker.setSurname(surname);
        newWorker.setEmail(email);
        newWorker.setPassword(password);
        newWorker.setOccupation(occupation);

        if(workerRepository.save(newWorker) != null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The creation of the worker has failed");
    }

    @PutMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<String> modifyWorker(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String occupation){

        Worker selectedWorker = getWorker(id).getBody();

        if(selectedWorker == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested worker to modify doesn't exists");
        }

        selectedWorker.setName(name);
        selectedWorker.setSurname(surname);
        selectedWorker.setEmail(email);
        selectedWorker.setPassword(password);
        selectedWorker.setOccupation(occupation);

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
    public ResponseEntity<String> removeWorker(@PathVariable(value = "id") Long id){
        Optional<Worker> workerSearched = workerRepository.findById(id);
        if(workerSearched.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The worker with the given id doesn't exists or was already deleted");
        }

        workerRepository.delete(workerSearched.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Worker successfully deleted from database!");
    }
}
