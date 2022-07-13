package com.example.crudWorkers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        workerRepository.save(newWorker);
       return "Saved!";
    }

    @GetMapping(path = "/allWorkers")
    @ResponseBody
    public Iterable<Worker> getAllWorkers(){
        return workerRepository.findAll();
    }
}
