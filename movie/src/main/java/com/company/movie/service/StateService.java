package com.company.movie.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.company.movie.models.State;
import com.company.movie.repositories.StateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateService {

    private final StateRepository stateRepository;
    private final TransactionTemplate transactionTemplate;

    public int save(List<Map<String, Object>> states) {
        int count = 0;

        for (Map<String, Object> stateMap : states) {
            try {
                // Ejecuta cada iteración en su propia transacción independiente
                Boolean success = transactionTemplate.execute(status -> {
                    String cveEnt = (String) stateMap.get("cve_ent");

                    // 1. Buscar si existe (incluso si está soft-deleted)
                    State state = stateRepository.findByCveEntWithTrashed(cveEnt)
                            .orElse(new State());

                    state.setCveEnt(cveEnt);
                    state.setCvegeo((String) stateMap.get("cvegeo"));
                    state.setNomgeo((String) stateMap.get("nomgeo"));
                    state.setNomAbrev((String) stateMap.get("nom_abrev")); // Equivalente a ?? null

                    Object pobTotal = stateMap.get("pob_total");
                    if (pobTotal != null) {
                        state.setPobTotal(Integer.parseInt(pobTotal.toString()));
                    }

                    // 3. Si estaba eliminado lógicamente, restaurarlo
                    if (state.getDeletedAt() != null) {
                        System.out.println("entra" );
                        state.setDeletedAt(null); // Equivalente a $state->restore()
                    }

                    // 4. Guardar (crea o actualiza)
                    stateRepository.save(state);
                    return true;
                });

                if (Boolean.TRUE.equals(success)) {
                    count++;
                }

            } catch (Exception e) {
                // Equivalente al catch con DB::rollBack() y Log::error
                log.error("StateService@save error para cve_ent {}: {}", stateMap.get("cve_ent"), e.getMessage());
            }
        }

        return count;
    }
    
}
