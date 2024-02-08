package ec.edu.ups.clienteservice.controller;

import ec.edu.ups.clienteservice.model.Cliente;
import ec.edu.ups.clienteservice.service.ClienteService;
import ec.edu.ups.clienteservice.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getAllClientes() {

       
        return clienteService.findAllClientes();
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String id) {


            Optional<Cliente> cliente = clienteService.findClienteById(id);
            return cliente.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
        
    }

    @Autowired
    private EmailService emailService;

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        Cliente clienteGuardado = clienteService.saveCliente(cliente);
        emailService.sendSimpleMessage(
            clienteGuardado.getEmail(), // Asegúrate de que el cliente tenga un campo de correo electrónico
            "Bienvenido al sistema", 
            "Gracias por registrarte, " + clienteGuardado.getNombre()
        );
        return clienteGuardado;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String id, @RequestBody Cliente cliente) {
            return clienteService.findClienteById(id)
            .map(clienteExistente -> {
                cliente.setId(id);
                return ResponseEntity.ok(clienteService.saveCliente(cliente));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable String id) {
            if (clienteService.findClienteById(id).isPresent()) {
                clienteService.deleteCliente(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        
    }

    // Otros métodos del controlador si son necesarios
}
