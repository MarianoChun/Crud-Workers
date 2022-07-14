package com.example.controller;

import com.example.models.Worker;
import com.example.repository.WorkerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkerControllerTest {

    @Mock
    private WorkerRepository workerRepository;

    @InjectMocks
    private WorkerController workerController;

    private List<Worker> workers;

    @BeforeEach
    void setUp() {
        workers = new ArrayList<>();

        Worker worker1 = new Worker();
        worker1.setId(20L);
        worker1.setName("Hugo");
        worker1.setSurname("Mendez");
        worker1.setEmail("hugo@gmail.com");
        worker1.setPassword("123");
        worker1.setOccupation("Medic");

        Worker worker2 = new Worker();
        worker2.setId(5L);
        worker2.setName("Pepe");
        worker2.setSurname("Garcia");
        worker2.setEmail("garciaPepe@gmail.com");
        worker2.setPassword("444411111");
        worker2.setOccupation("Waiter");

        Worker worker3 = new Worker();
        worker3.setId(9L);
        worker3.setName("John");
        worker3.setSurname("Doe");
        worker3.setEmail("John@hotmail.com");
        worker3.setPassword("0988766");
        worker3.setOccupation("Librarian");

        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void save(){
        Worker testWorker = new Worker();
        testWorker.setId(Long.getLong("99"));
        testWorker.setName("Sara");
        testWorker.setSurname("Torres");
        testWorker.setEmail("torresSara@gmail.com");
        testWorker.setPassword("44444");
        testWorker.setOccupation("Firefighter");

        when(workerRepository.save(any(Worker.class))).thenReturn(testWorker);
        assertNotNull(workerRepository.save(testWorker));
    }
    @Test
    void addWorker() {
        when(workerRepository.save(any(Worker.class))).thenReturn(workers.get(0));

        String response = workerController.addWorker("Hugo", "Mendez", "hugo@gmail.com", "123", "Medic");

        assertTrue(response.equals("Saved!"));
    }

    @Test
    void getWorker() {
        when(workerRepository.findById(9L)).thenReturn(Optional.of(workers.get(2)));

        assertNotNull(workerController.getWorker(9L));
    }

    @Test
    void getAllWorkers() {
        when(workerRepository.findAll()).thenReturn(workers);

        assertTrue(((Collection<?>)workerController.getAllWorkers()).size() == 3);
    }

    @Test
    void modifyWorker(){
        when(workerRepository.findById(5L)).thenReturn(Optional.of(workers.get(1)));

        Worker expectedWorkerModified = new Worker();

        expectedWorkerModified.setId(5L);
        expectedWorkerModified.setName("Pedro");
        expectedWorkerModified.setSurname("Torres");
        expectedWorkerModified.setEmail("pedro@hotmail.com");
        expectedWorkerModified.setPassword("111111");
        expectedWorkerModified.setOccupation("Salesman");

        workerController.modifyWorker(5L, "Pedro", "Torres", "pedro@hotmail.com", "111111", "Salesman");

        assertEquals(expectedWorkerModified, workerController.getWorker(5L));

    }
}