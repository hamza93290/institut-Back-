package com.institut.institut.services;

import com.institut.institut.dto.EleveDto;
import com.institut.institut.models.Eleve;

import java.util.List;

public interface EleveService {

    Eleve save(EleveDto eleve);
    List<Eleve> getEleve();

}
