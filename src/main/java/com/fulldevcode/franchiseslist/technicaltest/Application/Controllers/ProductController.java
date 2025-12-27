package com.fulldevcode.franchiseslist.technicaltest.Application.Controllers;

import com.fulldevcode.franchiseslist.technicaltest.Domain.Services.ProductService;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ApiResponseDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ProductDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ProductListDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas
 * con los productos del sistema.
 * Expone endpoints para:
 * - Listar productos por sucursal.
 * - Consultar un producto por su identificador.
 * - Crear, actualizar y eliminar productos.
 * Todas las respuestas se entregan bajo el formato estándar ApiResponseDTO.
 */
@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService product)
    {
        this.productService = product;
    }

    /**
     * Obtiene la lista de productos asociados a una sucursal específica.
     *
     * @param idBranch Identificador único de la sucursal.
     * @return ApiResponseDTO que contiene:
     *         - Una lista de productos (ProductListDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal no existe o ocurre una excepción.
     * Método HTTP: GET
     * Endpoint: /api/products/getAll/{idBranch}
     */
    @GetMapping("getAll/{idBranch}")
    public ApiResponseDTO<List<ProductListDTO>> IndexProduct(@PathVariable Integer idBranch)
    {
        ApiResponseDTO<List<ProductListDTO>> products = this.productService.IndexProducts(idBranch);
        return products;
    }

    /**
     * Obtiene la información detallada de un producto a partir de su identificador.
     *
     * @param id Identificador único del producto.
     * @return ApiResponseDTO que contiene:
     *         - El detalle del producto (ProductDTO) si existe.
     *         - Un mensaje de error si el producto no se encuentra registrado.
     * Método HTTP: GET
     * Endpoint: /api/products/{id}
     */
    @GetMapping("/{id}")
    public ApiResponseDTO<ProductDTO> SearchById(@PathVariable Integer id)
    {
        ApiResponseDTO<ProductDTO> product = this.productService.SearchById(id);
        return product;
    }

    /**
     * Crea un nuevo producto y lo asocia a una sucursal existente.
     *
     * @param productDTO Objeto DTO que contiene los datos del producto a crear.
     * @return ApiResponseDTO que contiene:
     *         - El producto creado (ProductDTO) si la operación es exitosa.
     *         - Un mensaje de error si el producto ya existe o la sucursal no es válida.
     * Método HTTP: POST
     * Endpoint: /api/products
     */
    @PostMapping()
    public ApiResponseDTO<ProductDTO> Create(@RequestBody ProductDTO productDTO)
    {
        ApiResponseDTO<ProductDTO> product = this.productService.CreateProduct(productDTO);
        return product;
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param id Identificador único del producto a actualizar.
     * @param productDTO Objeto DTO con la información actualizada del producto.
     * @return ApiResponseDTO que contiene:
     *         - El producto actualizado (ProductDTO) si la operación es exitosa.
     *         - Un mensaje de error si el producto no existe o los datos son inválidos.
     * Método HTTP: PUT
     * Endpoint: /api/products/{id}
     */
    @PutMapping("/{id}")
    public ApiResponseDTO<ProductDTO> Edit(@PathVariable Integer id, @RequestBody ProductDTO productDTO)
    {
        ApiResponseDTO<ProductDTO> product = this.productService.UpdateProduct(id, productDTO);
        return product;
    }

    /**
     * Elimina un producto del sistema a partir de su identificador.
     *
     * @param id Identificador único del producto a eliminar.
     * @return ApiResponseDTO que contiene:
     *         - La información del producto eliminado (ProductDTO) si la operación es exitosa.
     *         - Un mensaje de error si el producto no se encuentra registrado.
     * Método HTTP: DELETE
     * Endpoint: /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public ApiResponseDTO<ProductDTO> Delete(@PathVariable Integer id)
    {
        ApiResponseDTO<ProductDTO> product = this.productService.DeleteProduct(id);
        return product;
    }
}
