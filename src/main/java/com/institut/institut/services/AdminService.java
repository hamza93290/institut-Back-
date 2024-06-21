package com.institut.institut.services;

import com.institut.institut.dto.AdminDto;
import com.institut.institut.exception.GeneralException;
import com.institut.institut.models.Admin;

import java.util.List;

public interface AdminService {

     String addAdmin(AdminDto adminDto) throws GeneralException;
     AdminDto getAdminById(int adminId);
}
