package com.institut.institut.controllers;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.services.AdminService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("admin")
public class AdminController {

    @Qualifier("adminService")
    private final AdminService adminService;

    public AdminController (AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/addNewAdmin")
    public ResponseEntity<String> addNewUser( @RequestBody AdminDto adminDto) {
        try {
            String result = adminService.addAdmin(adminDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (GeneralException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
