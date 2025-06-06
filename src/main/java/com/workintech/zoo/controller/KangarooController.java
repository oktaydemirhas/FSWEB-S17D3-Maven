package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
@Slf4j
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    public KangarooController() {
        this.kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getAllKangaroos() {
        log.info("Get all kangaroos request received");
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo getKangaroo(@PathVariable Integer id) {
        log.info("Get kangaroo with id: {} request received", id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo createKangaroo(@RequestBody Kangaroo kangaroo) {
        log.info("Create kangaroo request received");
        if (kangaroo.getId() == null || kangaroo.getId() <= 0) {
            throw new ZooException("Kangaroo id should be valid", HttpStatus.BAD_REQUEST);
        }
        if (kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException("Kangaroo with given id already exists: " + kangaroo.getId(), HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable Integer id, @RequestBody Kangaroo kangaroo) {
        log.info("Update kangaroo with id: {} request received", id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable Integer id) {
        log.info("Delete kangaroo with id: {} request received", id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
} 