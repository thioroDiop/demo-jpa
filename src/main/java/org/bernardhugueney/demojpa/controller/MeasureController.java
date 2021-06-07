package org.bernardhugueney.demojpa.controller;

import org.bernardhugueney.demojpa.model.City;
import org.bernardhugueney.demojpa.model.Measure;
import org.bernardhugueney.demojpa.repository.MeasureRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(method = RequestMethod.POST)
    public Measure storeMeasure(@RequestBody Measure measure) {
        return measureRepository.save(measure);
    }
}