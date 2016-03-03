package de.jonashackt.weatherbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.codecentric.soap.businesslogic.IncredibleLogic;
import de.codecentric.soap.internalmodel.GeneralOutlook;
import de.codecentric.soap.internalmodel.Weather;

@RestController
@RequestMapping("/weatherbackend")
public class Controller {
    
    @RequestMapping(path="/general/outlook", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather) throws JsonProcessingException {
        System.out.println("Called Backend");
        /*
         * Some incredible Businesslogic...
         */
        return IncredibleLogic.generateGeneralOutlook();
    }
}
