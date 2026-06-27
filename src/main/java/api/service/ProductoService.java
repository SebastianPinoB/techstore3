package api.service;

import api.dto.ProductoDTO;
import api.model.Producto;
import api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
    
    public Producto crear(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return productoRepository.save(producto);
    }

    public Producto modificar(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        if (dto.getActivo() != null) {
            producto.setActivo(dto.getActivo());
        }
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

}
