package org.bernardhugueney.demojpa.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.bernardhugueney.demojpa.model.City;
import org.bernardhugueney.demojpa.repository.CityRepository;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/villes")
@CrossOrigin("*")
public class CityController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(method = RequestMethod.GET)
    @PageableAsQueryParam
    public Page<City> findAll(@PageableDefault(size=8) @Parameter(hidden=true) Pageable pageable){
        return cityRepository.findAll(pageable);
    }
    // requête localhost:8090/villes/sub?measure-type=MaMesure
    @RequestMapping(value = "/sub"
            ,method = RequestMethod.GET)
    public String testSub(@RequestParam("measure-type") String measureType,
                          @RequestParam(value = "start-date", required = false) LocalDateTime begin,
                          @RequestParam(value = "end-date", required = false) LocalDateTime end){
        String res=  "measure-type: "+measureType+"\n"+"begin: "+ begin+"\n"+"end: "+ end;
        System.out.println(res);
        return "<H1> Test sub</H1>\n"+res;
    }

    @RequestMapping(method = RequestMethod.POST, consumes="application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createForm(City resource) {
        City res=cityRepository.save(resource);
        return res.getId();

    }
    @Transactional
    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createJson(@RequestBody City resource) {
        City res=cityRepository.save(resource);
        res.setName(res.getName()+" modified after save !");
        System.err.println("managed ?:"+entityManager.contains(res));
        if(res.getName().contains("rollback")){
            throw new IllegalArgumentException("triggering rollback");
        }
        return res.getId();
    }

    @RequestMapping(value = "/{identifiant}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("identifiant") Long id){
        cityRepository.deleteById(id);
        System.out.println("Suppression de l'entité avec l'id :" +id);
    }
    @RequestMapping(value = "/{identifiant}", method = RequestMethod.GET)
    public Optional<City> readOne(@PathVariable("identifiant") Long id){
        return cityRepository.findById(id);
    }

}
