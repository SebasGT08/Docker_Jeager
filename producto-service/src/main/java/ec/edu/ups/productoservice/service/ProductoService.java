package ec.edu.ups.productoservice.service;

import ec.edu.ups.productoservice.model.Producto;
import ec.edu.ups.productoservice.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findProductoById(String id) {
        return productoRepository.findById(id);
    }

    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(String id) {
        productoRepository.deleteById(id);
    }
}
