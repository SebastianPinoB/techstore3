package api.service;

import api.dto.ProductoDTO;
import api.model.Producto;
import api.repository.ProductoRepository;
import software.amazon.awssdk.services.sqs.SqsClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private SqsClient sqsClient;

    // uRL desde application.properties
    @Value("${aws.sqs.queue.url}")
    private String queueUrl;

    public void registrarAuditoria(String accion, Long id, String usuario) {
        String mensaje = String.format("{\"accion\": \"%s\", \"productoId\": %d, \"usuario\": \"%s\"}",
                accion, id, usuario);

        sqsClient.sendMessage(m -> m.queueUrl(queueUrl).messageBody(mensaje));
    }

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

        Producto guardado = productoRepository.save(producto); // primero guarda

        registrarAuditoria("CREAR", guardado.getId(), "usuario_actual"); // después audita, ya con ID real

        return guardado;

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
        Producto actualizado = productoRepository.save(producto);

        registrarAuditoria("ACTUALIZAR", actualizado.getId(), "usuario_actual");

        return actualizado;
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setActivo(false);
        productoRepository.save(producto);
        registrarAuditoria("ELIMINAR", id, "usuario_actual");
    }

}
