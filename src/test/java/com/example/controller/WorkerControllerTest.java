package com.example.controller;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import com.example.services.WorkerService;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerControllerTest {

    @Mock
    private WorkerRepository workerRepository;
    @Mock
    private WorkerService workerService;

    @InjectMocks
    private WorkerController workerController;

    private List<Worker> workers;


    @BeforeEach
    void setUp() {
        workers = new ArrayList<>();

        Worker worker1 = new Worker("Hugo", "Mendez", "hugo@gmail.com", "123", "Medic");
        worker1.setId(20L);

        Worker worker2 = new Worker("Pepe", "Garcia", "garcia@gmail.com", "444411111", "Waiter");
        worker2.setId(5L);

        Worker worker3 = new Worker("John", "Doe", "John@hotmail.com", "0988766", "Librarian");
        worker3.setId(9L);

        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void save(){
        Worker testWorker = new Worker("Sara", "Torres", "torresSara@gmail.com", "44444", "Firefighter");
        testWorker.setId(99L);

        when(workerRepository.save(any(Worker.class))).thenReturn(testWorker);
        assertNotNull(workerRepository.save(testWorker));
    }
    @Test
    void addWorker() {
        when(workerService.saveWorker(any(Worker.class))).thenReturn(workers.get(0));

        String response = workerController.addWorker(new Worker("Hugo", "Mendez", "hugo@gmail.com", "123", "Medic")).getBody();

        assertTrue(response.equals("Saved!"));
    }

    @Test
    void addWorkerWithExistingEmail(){
        Iterable<Worker> iterator = new ArrayIterator<>(new Worker[]{workers.get(1)});
        when(workerService.getAllWorkers()).thenReturn(iterator);

        String response = workerController.addWorker(new Worker("Juliana","Garcia", "garcia@gmail.com", "222", "Nurse")).getBody();

        assertEquals("The email given by the worker already exists, try another", response);
    }
    @Test
    void getWorker() {
        when(workerService.getWorker(9L)).thenReturn(workers.get(2));

        assertNotNull(workerController.getWorker(9L));
    }

    @Test
    void getAllWorkers() {
        when(workerService.getAllWorkers()).thenReturn(workers);

        assertTrue(((Collection<?>)workerController.getAllWorkers().getBody()).size() == 3);
    }

    @Test
    void modifyWorker(){
        when(workerService.getWorker(5L)).thenReturn(workers.get(1));

        Worker expectedWorkerModified = new Worker("Pedro", "Torres", "pedro@hotmail.com", "111111", "Salesman");
        expectedWorkerModified.setId(5L);

        when(workerService.getWorker(5L)).thenReturn(expectedWorkerModified);

        workerController.modifyWorker(5L, new Worker("Pedro", "Torres", "pedro@hotmail.com", "111111", "Salesman"));


        assertEquals(expectedWorkerModified, workerController.getWorker(5L).getBody());

    }

    @Test
    void removeWorker() {
        when(workerService.getWorker(9L)).thenReturn((workers.get(2)));
        assertEquals("Worker successfully deleted from database!", workerController.removeWorker(9L).getBody());
    }
}