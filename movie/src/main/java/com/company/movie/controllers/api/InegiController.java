package com.company.movie.controllers.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
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
    
    public List<Map<String, Object>> getMunicipality() {
        List<Map<String, Object>> states = inegiService.getStates();
        int count = stateService.save(states);
        return states;
    }
}
