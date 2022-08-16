package com.example.services;

import com.example.models.Worker;

public interface WorkerService {

    Worker getWorker(Long id);

    Iterable<Worker> getAllWorkers();

    Worker saveWorker(Worker worker);

    void modifyWorker(Long id, Worker worker);

    void deleteWorker(Long id);


}
