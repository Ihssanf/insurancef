package com.gestion.assurance.contrôleurs;

import com.gestion.assurance.entities.Client;
import com.gestion.assurance.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public String viewClients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/add-client")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "add-client";
    }

    @PostMapping("/add-client")
    public String addClient(@ModelAttribute Client client) {
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit-client/{id}")
    public String showEditClientForm(@PathVariable Long id, Model model) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            model.addAttribute("client", client);
            return "edit-client";
        } else {
            return "redirect:/clients";
        }
    }

    @PostMapping("/edit-client/{id}")
    public String editClient(@PathVariable Long id, @ModelAttribute Client client) {
        client.setId(id);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/delete-client/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
}
