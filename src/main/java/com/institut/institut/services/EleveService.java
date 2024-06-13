package com.institut.institut.services;

import com.institut.institut.dto.EleveDto;
import com.institut.institut.models.Eleve;

public interface EleveService {

    Eleve save(EleveDto eleve);
}
