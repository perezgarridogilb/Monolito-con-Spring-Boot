package com.company.movie.service;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.company.movie.models.Municipality;
import com.company.movie.repositories.MunicipalityRepository;
import com.company.movie.repositories.StateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MunicipalityService {
    private final MunicipalityRepository municipalityRepository;
        private final TransactionTemplate transactionTemplate;

public int save(List<Map<String, Object>> municipalities) {
    int count = 0;

    for (Map<String, Object> munMap : municipalities) {
        try {
            Boolean success = transactionTemplate.execute(status -> {
                String cveEnt = (String) munMap.get("cve_ent");
                String cveMun = (String) munMap.get("cve_mun");

                Municipality municipality = municipalityRepository.findByCveEntAndCveMunWithTrashed(cveEnt, cveMun)
                        .orElse(new Municipality());

                // 2. Asignar valores (equivalente al updateOrCreate de Laravel)
                municipality.setCveEnt(cveEnt);
                municipality.setCveMun(cveMun);
                municipality.setNomgeo((String) munMap.get("nomgeo"));

                Object pobTotal = munMap.get("pob_total");
                if (pobTotal != null) {
                    municipality.setPobTotal(Integer.parseInt(pobTotal.toString()));
                } else {
                    municipality.setPobTotal(null);
                }
                
                if (municipality.getDeletedAt() != null) {
                    municipality.setDeletedAt(null); // $municipality->restore()
                }

                // 4. Guardar (crea o actualiza)
                municipalityRepository.save(municipality);
                return true;
            });

            if (Boolean.TRUE.equals(success)) {
                count++;
            }

        } catch (Exception e) {
            log.error("MunicipalityService@save error para cve_ent {} y cve_mun {}: {}", 
                munMap.get("cve_ent"), munMap.get("cve_mun"), e.getMessage());
        }
    }

    return count;
}
}
