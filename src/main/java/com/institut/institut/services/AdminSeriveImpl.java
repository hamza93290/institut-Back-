package com.institut.institut.services;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.mapper.AdminMapper;
import com.institut.institut.models.Admin;
import com.institut.institut.repository.AdminRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminSeriveImpl implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo ;

    @Autowired
    private AdminMapper adminMapper ;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final Logger log = LoggerFactory.getLogger(AdminSeriveImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminDetail = adminRepo.findByUsername(username);

        return adminDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + username));
    }

    public AdminDto findUserByUsername(String username) {
        try {
            if (username != null && !username.isBlank()) {
                Optional<Admin> foundUser = adminRepo.findByUsername(username);
                Admin userInfo = foundUser.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + username));
                AdminDto userInfoDTO = adminMapper.toDto(userInfo);

                return userInfoDTO;
            } else {
                log.error(String.format("Email invalide %s", username));
                throw new IllegalArgumentException("Email invalide");
            }
        } catch (UsernameNotFoundException e) {
            log.error(String.format("Utilisateur non trouvé : %s", e.getMessage()), e);
            e.printStackTrace();
            return null;
        }
    }

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

    public AdminDto getAdminById(int adminId) {
        return null;
    }

}
