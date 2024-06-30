package com.institut.institut.controllers;

import com.institut.institut.dto.EleveDto;
import com.institut.institut.models.Eleve;
import com.institut.institut.services.EleveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("eleve")
public class EleveController {

    private final EleveService eleveService ;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    public EleveController(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    @PostMapping("save")
    public ResponseEntity<Eleve> postClient(@RequestBody EleveDto eleveDto){
        try {
            return new ResponseEntity<>(eleveService.save(eleveDto), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("get")
    public ResponseEntity<List<Eleve>> getClient(){
        try {
            log.info("get eleve succes");
            return new ResponseEntity<>(eleveService.getEleve(), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
