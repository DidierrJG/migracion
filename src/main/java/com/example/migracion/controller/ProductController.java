package com.example.migracion.controller;

import com.example.migracion.model.Product;
import com.example.migracion.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Tag(name = "Products", description = "Operaciones para consultar y crear productos")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Listar productos",
            description = "Devuelve la lista completa de productos registrados."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Product.class))
            )
    )
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @Operation(
            summary = "Obtener un producto por ID",
            description = "Busca un producto por su identificador único."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Product.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un producto con ese ID",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(
            @Parameter(description = "ID del producto", example = "1", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @Schema(description = "Datos necesarios para crear un producto")
    public record CreateProductRequest(
            @Schema(description = "Nombre del producto", example = "Teclado mecánico")
            String name,

            @Schema(description = "Precio unitario", example = "249900.00")
            BigDecimal price,

            @Schema(description = "Unidades disponibles en inventario", example = "25")
            Integer stock,

            @Schema(description = "ID de la categoría a la que pertenece", example = "3")
            Long categoryId,

            @Schema(description = "Etiquetas asociadas al producto", example = "[\"gaming\", \"oferta\"]")
            Set<String> tags
    ) {}

    @Operation(
            summary = "Crear un producto",
            description = "Registra un nuevo producto y devuelve el recurso creado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado correctamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Product.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "La categoría indicada no existe",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest req) {
        Product created = productService.create(req.name(), req.price(), req.stock(), req.categoryId(), req.tags());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}