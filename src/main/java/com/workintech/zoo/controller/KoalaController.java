package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
@Slf4j
public class KoalaController {

    private Map<Integer, Koala> koalas;

    public KoalaController() {
        this.koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAllKoalas() {
        log.info("Get all koalas request received");
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala getKoala(@PathVariable Integer id) {
        log.info("Get koala with id: {} request received", id);
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala createKoala(@RequestBody Koala koala) {
        log.info("Create koala request received");
        if (koala.getId() == null || koala.getId() <= 0) {
            throw new ZooException("Koala id should be valid", HttpStatus.BAD_REQUEST);
        }
        if (koalas.containsKey(koala.getId())) {
            throw new ZooException("Koala with given id already exists: " + koala.getId(), HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable Integer id, @RequestBody Koala koala) {
        log.info("Update koala with id: {} request received", id);
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala deleteKoala(@PathVariable Integer id) {
        log.info("Delete koala with id: {} request received", id);
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id is not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
} 