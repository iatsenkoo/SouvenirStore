package com.task.souvenirstore.controllers;

import com.task.souvenirstore.entities.Factory;
import com.task.souvenirstore.entities.Souvenir;
import com.task.souvenirstore.repository.FactoryRepository;
import com.task.souvenirstore.repository.SouvenirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreController {
    private final FactoryRepository factoryRepository;
    private final SouvenirRepository souvenirRepository;

    @Autowired
    public StoreController(FactoryRepository factoryRepository, SouvenirRepository souvenirRepository) {
        this.factoryRepository = factoryRepository;
        this.souvenirRepository = souvenirRepository;
    }

    @PostMapping("/add")
    public String addFactory(String factoryName, String factoryCountry,Model model){
        Factory factory = new Factory(0, factoryName, factoryCountry,new ArrayList<>());
        factoryRepository.save(factory);
        return "redirect:/listFactories";

    }

    @GetMapping("/listFactories")
    public String showAllFactories(Model model){
        model.addAttribute("factories",factoryRepository.findAll());
        return "factories";
    }

    @PostMapping("/listFactoriesByPrice")
    public String showAllFactoriesByPrice(Model model,double price){
        List<Souvenir> souvenirs = souvenirRepository.findByPriceLessThan(price);
        model.addAttribute("factories",factoryRepository.findBySouvenirsIn(souvenirs));
        return "factories";
    }

    @PostMapping("/listFactoryBySouvenirNameAndYear")
    public String showAllFactoriesBySouvnirNameAndYear(Model model,int year,String nameSouvenir){
        List<Souvenir> souvenirs = souvenirRepository.findAll().stream()
                .filter(n-> n.getName().equals(nameSouvenir))
                .filter(y-> y.getProductDate().getYear()==year)
                .toList();

        model.addAttribute("factories",factoryRepository.findBySouvenirsIn(souvenirs));
        return "factories";
    }

    @GetMapping("/deleteFactory/{id}")
    public String deleteFactory(@PathVariable("id") int id){
        Factory factory = factoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid factory id"));
        factoryRepository.delete(factory);
        return "redirect:/listFactories";
    }

    @GetMapping("/editFactory/{id}")
    public String showUpdatePage(@PathVariable("id") int id, Model model) {
        Factory factory = factoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid expression id"));
        model.addAttribute("factory", factory);
        return "editFactory";
    }

    @GetMapping("/addSouvenir/{id}")
    public String showAddSouvenirPage(@PathVariable("id") int id, Model model){
        Factory factory = factoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid factory id"));
        model.addAttribute("factory", factory);
        return "addSouvenir";
    }

    @PostMapping("/updateFactory/{id}")
    public String updateFactory(@PathVariable("id") int id, Factory factory){
        factoryRepository.save(factory);
        return "redirect:/listFactories";
    }

    @PostMapping("/saveSouvenir/{id}")
    public String saveSouvenir(@PathVariable("id") int id, String nameSouvenir,double priceSouvenir, Factory factory, String dateProduct){
        souvenirRepository.save(new Souvenir(0,nameSouvenir,priceSouvenir,factory, LocalDate.parse(dateProduct, DateTimeFormatter.ofPattern("yyyy.MM.dd"))));
        return "redirect:/listSouvenirs";
    }

    @GetMapping("/listSouvenirs")
    public String showAllSouvenirs(Model model){
        List<Souvenir> souvenirs = souvenirRepository.findAll();
        model.addAttribute("souvenirs",souvenirs);
        return "souvenirs";
    }

    @PostMapping("/listSouvenirsByCountry")
    public String showSouvenirsByCountry(Model model, String nameCountry){
        List<Souvenir> souvenirs = souvenirRepository.findByFactoryCountry(nameCountry);
        model.addAttribute("souvenirs", souvenirs);
        return "souvenirs";
    }

    @PostMapping("/listSouvenirsByName")
    public String showSouvenirsByFactoryName (Model model,String nameFactory){
        List<Souvenir> souvenirs = souvenirRepository.findByFactoryName(nameFactory);
        model.addAttribute("souvenirs",souvenirs);
        return "souvenirs";
    }



    @GetMapping("/listSouvenirs/{id}")
    public String showAllSouvenirsByFactoryId(@PathVariable("id") int id,Model model){
        List<Souvenir> souvenirs = souvenirRepository.findByFactoryId(id);
        model.addAttribute("souvenirs",souvenirs);
        return "souvenirs";
    }

    @GetMapping("/deleteSouvenir/{id}")
    public String deleteSouvenir(@PathVariable("id") int id){
        Souvenir souvenir = souvenirRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid souvenir id"));
        souvenirRepository.delete(souvenir);
        return "redirect:/listSouvenirs";
    }

    @GetMapping("/editSouvenir/{id}")
    public String showUpdateSouvenirPage(@PathVariable("id") int id, Model model) {
        Souvenir souvenir = souvenirRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("invalid souvenir id"));
        model.addAttribute("souvenir", souvenir);
        return "editSouvenir";
    }
    @PostMapping("/updateSouvenir/{id}")
    public String updateSouvenir(@PathVariable("id") int id, Souvenir souvenir){
        souvenirRepository.save(souvenir);
        return "redirect:/listSouvenirs";
    }

}
