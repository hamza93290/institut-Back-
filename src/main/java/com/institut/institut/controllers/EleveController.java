package com.institut.institut.controllers;

import com.institut.institut.dto.EleveDto;
import com.institut.institut.models.Eleve;
import com.institut.institut.services.EleveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eleve")
public class EleveController {

    private final EleveService eleveService ;

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
}
