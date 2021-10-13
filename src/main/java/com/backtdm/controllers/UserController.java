package com.backtdm.controllers;

import com.backtdm.models.Cliente;
import com.backtdm.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired ClienteRepository clienteRepository;

    private List<Cliente> clientes = new ArrayList<>();


    @PostMapping("/clientes")
    public Cliente setcliente(@RequestBody Cliente cliente){
        clientes.add(cliente);
        return clienteRepository.save(cliente);
    }


    @GetMapping("/{id}")
    public Cliente cliente(@PathVariable("id") Long id) {
        System.out.println("O id é " + id);

        Optional<Cliente> userFind = clientes.stream().filter(user -> user.getId() == id).findFirst();

        if (userFind.isPresent()) {
            return userFind.get();
        }

        return null;
    }

    @GetMapping("/list")
    public List<Cliente> list() {
        return clientes;
    }
}
