package com.institut.institut.mapper;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.models.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDto toDto(Admin admin){
        return new AdminDto(admin.getId(), admin.getUsername(), admin.getPassword());
    }

    public Admin toAdmin(AdminDto adminDto){
        return new Admin(adminDto.getId(), adminDto.getUsername(), adminDto.getPassword());
    }

}
