package com.company.movie.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.movie.models.Vendor;
import com.company.movie.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    public List<Vendor> findVendor() {
        return vendorRepository.findAll();
    }


}
