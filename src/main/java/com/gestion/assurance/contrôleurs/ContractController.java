package com.gestion.assurance.contrôleurs;

import com.gestion.assurance.entities.Assurance;
import com.gestion.assurance.entities.Client;
import com.gestion.assurance.entities.Contrat;
import com.gestion.assurance.repositories.AssuranceRepository;
import com.gestion.assurance.repositories.ClientRepository;
import com.gestion.assurance.repositories.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Controller
public class ContractController {

    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AssuranceRepository assuranceRepository;
    @GetMapping("/add-contract")
    public String showAddContractForm(Model model) {
        model.addAttribute("contract", new Contrat());
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        List<Assurance> assurances = assuranceRepository.findAll();
        model.addAttribute("assurances", assurances);
        return "add-contract";
    }

    @PostMapping("/add-contract")
    public String addContract(@Validated @ModelAttribute Contrat contrat, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Client> clients = clientRepository.findAll();
            model.addAttribute("clients", clients);
            List<Assurance> assurances = assuranceRepository.findAll();
            model.addAttribute("assurances", assurances);
            return "add-contract";
        }

        Long clientId = contrat.getClient().getId();
        Client client = clientRepository.findById(clientId).orElse(null);
        Long assuranceId = contrat.getAssurance().getId();
       Assurance assurance = assuranceRepository.findById(assuranceId).orElse(null);
        if (client != null) {
            contrat.setClient(client);
            contrat.setAssurance(assurance);
            contratRepository.save(contrat);
            return "redirect:/contracts";
        } else {

            return "add-contract";
        }
    }

    @GetMapping("/contracts")
    public String viewContracts(Model model) {
        List<Contrat> contrats = contratRepository.findAll();
        model.addAttribute("contrats", contrats);
        return "contracts";
    }

    @GetMapping("/edit-contract/{id}")
    public String showEditContractForm(@PathVariable Long id, Model model) {
        Contrat contrat = contratRepository.findById(id).orElse(null);
        if (contrat != null) {
            model.addAttribute("contract", contrat);
            List<Client> clients = clientRepository.findAll();
            model.addAttribute("clients", clients);

            List<Assurance> assurances = assuranceRepository.findAll();
            model.addAttribute("assurances", assurances);
            return "edit-contract";
        } else {
            return "redirect:/contracts";
        }
    }

    @PostMapping("/edit-contract/{id}")
    public String editContract(@PathVariable Long id, @Validated @ModelAttribute Contrat contrat,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            List<Client> clients = clientRepository.findAll();
            model.addAttribute("clients", clients);
            List<Assurance> assurances = assuranceRepository.findAll();
            model.addAttribute("assurances", assurances);
            return "edit-contract";
        }

        Long clientId = contrat.getClient().getId();
        Client client = clientRepository.findById(clientId).orElse(null);
        Long assuranceId = contrat.getAssurance().getId();
        Assurance assurance = assuranceRepository.findById(assuranceId).orElse(null);

        if (client != null) {
            contrat.setClient(client);
            contrat.setId(id);
            contrat.setAssurance(assurance);
            contratRepository.save(contrat);
            return "redirect:/contracts";
        } else {

            return "edit-contract";
        }
    }

    @GetMapping("/delete-contract/{id}")
    public String deleteContract(@PathVariable Long id) {
        contratRepository.deleteById(id);
        return "redirect:/contracts";
    }
}