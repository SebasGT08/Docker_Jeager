package ec.edu.ups.clienteservice.repository;

import ec.edu.ups.clienteservice.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
    // Aquí puedes agregar métodos personalizados si es necesario
    // Ejemplo: Encontrar un cliente por email
    Cliente findByEmail(String email);
}