package de.jonashackt.weatherbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.codecentric.soap.businesslogic.IncredibleLogic;
import de.codecentric.soap.internalmodel.GeneralOutlook;
import de.codecentric.soap.internalmodel.Weather;

@RestController
@RequestMapping("/weatherbackend")
public class WeatherBackendController {
    
    @RequestMapping(path = "/general/outlook", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather) throws JsonProcessingException {
        System.out.println("Called Backend");
        /*
         * Some incredible Businesslogic...
         */
        return IncredibleLogic.generateGeneralOutlook();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
    public String whatsTheSenseInThat(@PathVariable("name") String name) {
        return "Hello " + name + "! This is a RESTful HttpService written in Spring. Try to use some other HTTP verbs (don´t say 'methods' :P ) :)";
    }
}
