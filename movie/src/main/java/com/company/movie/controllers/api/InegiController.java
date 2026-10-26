package com.company.movie.controllers.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.movie.service.InegiService;
import com.company.movie.service.MunicipalityService;
import com.company.movie.service.StateService;

@RestController
@RequestMapping("/api/inegi")
public class InegiController {

    private final InegiService inegiService;
    private final StateService stateService;
    private final MunicipalityService municipalityService;

    public InegiController(InegiService inegiService, StateService stateService,
            MunicipalityService municipalityService) {
        this.inegiService = inegiService;
        this.stateService = stateService;
        this.municipalityService = municipalityService;
    }

    @GetMapping("/states")
    public List<Map<String, Object>> getStates() {
        List<Map<String, Object>> states = inegiService.getStates();
        int count = stateService.save(states);
        return states;
    }
    
    @PostMapping("/municipalities")
    public ResponseEntity<Map<String, Object>> getMunicipality(@RequestBody Map<String, Object> body) {
        String keyState = (String) body.get("cve_ent");
        List<Map<String, Object>> municipalities = inegiService.getMunicipalities(keyState);
        int count = municipalityService.save(municipalities);

        Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("saved_count", count);
    response.put("municipalities", municipalities);

    return ResponseEntity.ok(response);
       
    }

    @DeleteMapping("/states/{cveEnt}")
    public ResponseEntity<Void> softDeleteState(@PathVariable String cveEnt) {
        stateService.softDeleteState(cveEnt);
        return ResponseEntity.noContent().build();
    }
}
