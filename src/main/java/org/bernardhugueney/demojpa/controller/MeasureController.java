package org.bernardhugueney.demojpa.controller;

import org.bernardhugueney.demojpa.model.City;
import org.bernardhugueney.demojpa.model.Measure;
import org.bernardhugueney.demojpa.repository.MeasureRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/measures")
@CrossOrigin("*")
public class MeasureController {

    private final MeasureRepository measureRepository;

    public MeasureController(MeasureRepository measureRepository) {
        this.measureRepository = measureRepository;
    }


    @RequestMapping(value = "/last", method = RequestMethod.GET)
    public Optional<Measure> getLastMeasureByType(@RequestParam("measure-type") String measureType) {
        Optional<Measure> lastMeasure = measureRepository.findFirstByTypeOrderByMeasureDateDesc(measureType);
        return lastMeasure;
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public Optional<Measure> getTopMeasureByType(@RequestParam("measure-type") String measureType) {
        Optional<Measure> lastMeasure = measureRepository.findFirstByTypeOrderByValueDesc(measureType);
        return lastMeasure;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Measure> getMeasuresByType(@RequestParam("measure-type") String measureType,
                                           @RequestParam("start-date")  LocalDateTime startDate,
                                           @RequestParam("end-date")  LocalDateTime endDate) {
        List<Measure> autoRes=measureRepository.findByTypeAndMeasureDateBetween(measureType, startDate, endDate);
        List<Measure> myRes= measureRepository.myFind(measureType, startDate, endDate);
        System.err.println("autoRes.equals(myRes)="+autoRes.equals(myRes));
        System.err.println("myRes.size()="+myRes.size());
        return measureRepository.myFind(measureType, startDate, endDate);
    }
/*
var xmlhttp = new XMLHttpRequest();
xmlhttp.open("DELETE", "/api/measures/3000");
xmlhttp.send();
 */
    @RequestMapping(value = "/{identifiant}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("identifiant") Long id){
        measureRepository.deleteById(id);
        System.out.println("Suppression de l'entité avec l'id :" +id);
    }
    @RequestMapping(value = "/{identifiant}", method = RequestMethod.GET)
    public Optional<Measure> readOne(@PathVariable("identifiant") Long id){
        return measureRepository.findById(id);
    }

    /*
    var xmlhttp = new XMLHttpRequest();
xmlhttp.open("PUT", "/api/measures/3000");
xmlhttp.setRequestHeader("Content-Type", "application/json");
xmlhttp.send(JSON.stringify({id : 3000, type: "temp", unit:"c", value : 55.55, measureDate: "2021-06-08T10:28:13.59"}));

     */
    @RequestMapping(value = "/{identifiant}", method = RequestMethod.PUT)
    public ResponseEntity<Measure> update(@PathVariable("identifiant") Long id,
                                    @RequestBody Measure measure){
        if(!measure.getId().equals(id)){
            return new ResponseEntity<>(measure
                    ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(measureRepository.save(measure)
                ,HttpStatus.OK);
    }
    /*
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", "/api/measures/3000/value");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify(555.555));

         */
    @Transactional
    @RequestMapping(value = "/{identifiant}/value", method = RequestMethod.PUT)
    public ResponseEntity updateValue(@PathVariable("identifiant") Long id,
                                          @RequestBody double value){
        return new ResponseEntity(measureRepository.setValueById(value, id)
        ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /*
var xmlhttp = new XMLHttpRequest();
xmlhttp.open("POST", "/api/measures/");
xmlhttp.setRequestHeader("Content-Type", "application/json");
xmlhttp.send(JSON.stringify({ type: "testingPOST", unit:"c", value : 55.55, measureDate: "2021-06-08T10:28:13.59"}));

     */
    @RequestMapping(method = RequestMethod.POST)
    public Measure storeMeasure(@RequestBody Measure measure) {
        return measureRepository.save(measure);
    }
}