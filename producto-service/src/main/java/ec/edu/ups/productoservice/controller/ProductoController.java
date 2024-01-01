package ec.edu.ups.productoservice.controller;

import ec.edu.ups.productoservice.model.Producto;
import ec.edu.ups.productoservice.service.ProductoService;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final Tracer tracer;

    @Autowired
    public ProductoController(ProductoService productoService, Tracer tracer) {
        this.productoService = productoService;
        this.tracer = tracer;
    }

    @GetMapping
    public List<Producto> getAllProductos() {
        var span = tracer.buildSpan("getAllProductos").start();
        try {
            return productoService.findAllProductos();
        } finally {
            span.finish();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable String id) {
        var span = tracer.buildSpan("getProductoById").start();
        try {
            Optional<Producto> Producto = productoService.findProductoById(id);
            return Producto.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
        } finally {
            span.finish();
        }
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto Producto) {
        var span = tracer.buildSpan("createProducto").start();
        try {
            return productoService.saveProducto(Producto);
        } finally {
            span.finish();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable String id, @RequestBody Producto Producto) {
        var span = tracer.buildSpan("updateProducto").start();
        try {
            return productoService.findProductoById(id)
            .map(ProductoExistente -> {
                Producto.setId(id);
                return ResponseEntity.ok(productoService.saveProducto(Producto));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
        } finally {
            span.finish();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable String id) {
        var span = tracer.buildSpan("deleteProducto").start();
        try {
            if (productoService.findProductoById(id).isPresent()) {
                productoService.deleteProducto(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } finally {
            span.finish();
        }
    }

    // Otros métodos del controlador si son necesarios
}
