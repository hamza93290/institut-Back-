package com.institut.institut.services.Impl;

import com.institut.institut.dto.EleveDto;
import com.institut.institut.models.Eleve;
import com.institut.institut.repository.EleveRepo;
import com.institut.institut.services.EleveService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleveServiceImpl implements EleveService {


    private final EleveRepo eleveRepo;
    public EleveServiceImpl(EleveRepo eleveRepo){
        this.eleveRepo = eleveRepo;
    }


    @Override
    public Eleve save(EleveDto eleve) {

        Eleve newEleve = new Eleve();
        newEleve.setName(eleve.getName());
        newEleve.setAge(eleve.getAge());
        newEleve.setLastname(eleve.getLastname());
        newEleve.setEmail(eleve.getEmail());
        newEleve.setCursus(eleve.getCursus());
        newEleve.setTelephone(eleve.getTelephone());

        return eleveRepo.saveAndFlush(newEleve);
    }

    @Override
    public List<Eleve> getEleve() {
        return eleveRepo.findAll();
    }
}
