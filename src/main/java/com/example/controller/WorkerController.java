package com.example.controller;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class WorkerController {
    @Autowired
    private WorkerRepository workerRepository;

    @PostMapping(path = "/addWorker")
    @ResponseBody
    public String addWorker(@RequestParam String name,
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
            return "Saved!";
        }

        return "Fail";
    }

    @GetMapping(path = "/getWorker/{id}")
    @ResponseBody
    public Worker getWorker(@PathVariable Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        if(worker.isEmpty()) {
            return null;
        }

        return worker.get();

    }
    @GetMapping(path = "/allWorkers")
    @ResponseBody
    public Iterable<Worker> getAllWorkers(){
        return workerRepository.findAll();
    }

    @DeleteMapping(path = "/deleteWorker/{id}")
    @ResponseBody
    public String removeWorker(@PathVariable(value = "id") Long id){
        Optional<Worker> workerSearched = workerRepository.findById(id);
        if(workerSearched.isEmpty()) {
            return "The worker with the given id doesn't exists or was already deleted";
        }

        workerRepository.delete(workerSearched.get());
        return "Worker successfully deleted from database!";
    }
}
