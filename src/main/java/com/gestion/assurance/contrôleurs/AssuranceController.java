package com.gestion.assurance.contrôleurs;

import com.gestion.assurance.entities.Assurance;
import com.gestion.assurance.repositories.AssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AssuranceController {

    @Autowired
    private AssuranceRepository assuranceRepository;

    @GetMapping("/add-insurance")
    public String showAddInsuranceForm(Model model) {
        model.addAttribute("insurance", new Assurance());
        return "add-insurance";
    }

    @PostMapping("/add-insurance")
    public String addInsurance(@ModelAttribute Assurance assurance) {
        assuranceRepository.save(assurance);
        return "redirect:/assurances";
    }

    @GetMapping("/assurances")
    public String viewAssurances(Model model) {
        List<Assurance> assurances = assuranceRepository.findAll();
        model.addAttribute("assurances", assurances);
        return "assurances";
    }

    @GetMapping("/edit-insurance/{id}")
    public String showEditInsuranceForm(@PathVariable Long id, Model model) {
        Assurance assurance = assuranceRepository.findById(id).orElse(null);
        if (assurance != null) {
            model.addAttribute("insurance", assurance);
            return "edit-insurance";
        } else {
            return "redirect:/assurances";
        }
    }

    @PostMapping("/edit-insurance/{id}")
    public String editInsurance(@PathVariable Long id, @ModelAttribute Assurance assurance) {
        assuranceRepository.save(assurance);
        return "redirect:/assurances";
    }

    @GetMapping("/delete-insurance/{id}")
    public String deleteInsurance(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            assuranceRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Assurance supprimée avec succès.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Assurance ne peut pas être supprimée car elle contient des contrats.");
        }
        return "redirect:/assurances";
    }

}


