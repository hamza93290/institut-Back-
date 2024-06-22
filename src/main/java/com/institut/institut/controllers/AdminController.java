package com.institut.institut.controllers;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.services.AdminSeriveImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("admin")
public class AdminController {

    @Qualifier("adminService")
    private final AdminSeriveImpl adminService;

    private final AuthenticationManager authenticationManager;


    private static final Logger log = LoggerFactory.getLogger(AdminController.class);


    public AdminController (AdminSeriveImpl adminService , AuthenticationManager authenticationManager){
        this.adminService = adminService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/addNewAdmin")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody AdminDto adminDto) {

        try {
            System.out.println(adminDto.getUsername() + " et " + adminDto.getPassword());
            String result = adminService.addAdmin(adminDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (GeneralException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AdminDto authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));



            if (authentication.isAuthenticated()) {
                if (user.isUStatus()) {
                    log.info("Utilisateur authentifié avec un compte actif");
                    int userId = service.findUserByEmail(authRequest.getEmail()).getUId();

                    // on supprime la clé de refresh token associée à l'utilisateur s'il y en a déjà une en bdd avant d'en créer une
                    refreshTokenService.deleteTokenByUserId(userId);

                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
                    JwtResponseDTO jwtResponseDTO = new JwtResponseDTOBuilder()
                            .withAccessToken(jwtService.generateToken(authRequest.getEmail()))
                            .withToken(refreshToken.getRtToken())
                            .build();
                    return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
                } else {
                    log.error("L'utilisateur " + user.getUEmail() + " est inactif");
                    // Return a specific response when the user is inactive
                    return new ResponseEntity<>("L'utilisateur est inactif", HttpStatus.UNAUTHORIZED);
                }
            } else {
                log.error("Requête utilisateur invalide");
                throw new UsernameNotFoundException("Requête utilisateur invalide");
            }
        } catch (DisabledException e) {
            // Handle DisabledException separately to return a specific message
            log.error("L'utilisateur est inactif");
            return new ResponseEntity<>("L'utilisateur est inactif", HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            log.error("Requête utilisateur invalide pour l'identifiant '" + authRequest.getEmail());
            throw new BadCredentialsException("Identifiants invalides");
        }
    }

}
