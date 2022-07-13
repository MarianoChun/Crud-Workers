package com.example.repository;

import com.example.models.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
}
