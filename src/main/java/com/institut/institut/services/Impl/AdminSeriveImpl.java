package com.institut.institut.services.Impl;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.models.Admin;
import com.institut.institut.repository.AdminRepo;
import com.institut.institut.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AdminSeriveImpl implements AdminService {

    private AdminRepo adminRepo ;

    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AdminSeriveImpl.class);


    public AdminSeriveImpl( AdminRepo adminRepo , PasswordEncoder passwordEncoder){
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Admin addAdmin(AdminDto adminDto) throws GeneralException {

        Optional<Admin> existingAdmin = adminRepo.findByUsernameAdmin(adminDto.getUsername());
        if (existingAdmin.isPresent()){
            log.warn("Utilisateur avec le username '" + adminDto.getUsername() + "' existe déjà");
            throw new GeneralException("Un utilisateur avec cet username existe déjà");
        }


        return null;
    }

    @Override
    public AdminDto getAdminById(int adminId) {
        return null;
    }
}
