package com.institut.institut.controllers;

import com.institut.institut.builderDto.JwtResponseDTOBuilder;
import com.institut.institut.dto.AdminDto;
import com.institut.institut.dto.JwtResponseDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.services.AdminSeriveImpl;
import com.institut.institut.services.Impl.JwtServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin")
public class AdminController {

    @Qualifier("adminService")
    private final AdminSeriveImpl adminService;

    private final JwtServiceImpl jwtService;

    private final AuthenticationManager authenticationManager;


    private static final Logger log = LoggerFactory.getLogger(AdminController.class);


    public AdminController (AdminSeriveImpl adminService , AuthenticationManager authenticationManager, JwtServiceImpl jwtService){
        this.adminService = adminService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Méthode qui retourne les infos du user authentifié
     */
    @GetMapping("/userdetails")
    public ResponseEntity<?> findUserByEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        try {
            String token = authorizationHeader.substring(7);

            // Récupère les infos de l'utilisateur à partir du token
            String email = jwtService.extractUsername(token);

            // Charge les détails du user en se basant sur l'email
            AdminDto foundUser = adminService.findUserByUsername(email);
            return new ResponseEntity<>(foundUser, HttpStatus.OK);

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
                //if (user.isUStatus()) {
                log.info("Utilisateur authentifié avec un compte actif");
                //int userId = service.findUserByEmail(authRequest.getEmail()).getUId();
                // on supprime la clé de refresh token associée à l'utilisateur s'il y en a déjà une en bdd avant d'en créer une
                //refreshTokenService.deleteTokenByUserId(userId);

                //RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getEmail());
                JwtResponseDto jwtResponseDTO = new JwtResponseDTOBuilder()
                        .withAccessToken(jwtService.generateToken(authRequest.getUsername()))
                        .build();
                return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
//                } else {
//                    log.error("L'utilisateur " + user.getUEmail() + " est inactif");
//                    // Return a specific response when the user is inactive
//                    return new ResponseEntity<>("L'utilisateur est inactif", HttpStatus.UNAUTHORIZED);
//                }
            } else {
                log.error("Requête utilisateur invalide");
                throw new UsernameNotFoundException("Requête utilisateur invalide");
            }
        }
            catch (AuthenticationException e) {
            log.error("Requête utilisateur invalide pour l'identifiant '" + authRequest.getUsername());
            throw new BadCredentialsException("Identifiants invalides");
        }
    }
    /**
     * Méthode qui gère les exceptions d'authentification
     */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
