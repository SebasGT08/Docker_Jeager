package ec.edu.ups.productoservice.repository;

import ec.edu.ups.productoservice.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    Producto findByCategoria(String categoria);
}