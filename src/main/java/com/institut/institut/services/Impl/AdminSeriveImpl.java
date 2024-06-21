package com.institut.institut.services.Impl;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.mapper.AdminMapper;
import com.institut.institut.models.Admin;
import com.institut.institut.repository.AdminRepo;
import com.institut.institut.services.AdminService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminSeriveImpl implements AdminService {

    private AdminRepo adminRepo ;

    private AdminMapper adminMapper ;

    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AdminSeriveImpl.class);


    public AdminSeriveImpl( AdminRepo adminRepo , PasswordEncoder passwordEncoder, AdminMapper adminMapper){
        this.adminRepo = adminRepo;
        this.adminMapper = adminMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public String addAdmin(AdminDto adminDto) throws GeneralException {

        Optional<Admin> existingAdmin = adminRepo.findByUsername(adminDto.getUsername());
        if (existingAdmin.isPresent()){
            log.warn("Utilisateur avec le username '" + adminDto.getUsername() + "' existe déjà");
            throw new GeneralException("Un utilisateur avec cet username existe déjà");
        }
        Admin admin = adminMapper.toAdmin(adminDto);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        try {
            adminRepo.saveAndFlush(admin);
            log.info("Utilisateur '" + admin.getUsername() + "' ajouté avec succès");
            return "Utilisateur '" + admin.getUsername() + "' ajouté avec succès";
        } catch (Exception ex) {
            log.error("Erreur d'ajout d'utilisateur: " + ex.getMessage());
            throw new GeneralException("Erreur d'ajout d'utilisateur. Veuillez réessayer plus tard");
        }
    }

    @Override
    public AdminDto getAdminById(int adminId) {
        return null;
    }
}
