package com.fulldevcode.franchiseslist.technicaltest.Domain.Services;

import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ApiResponseDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ProductDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ProductListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface.IBranch;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface.IProduct;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.BranchEntity;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.ProductEntity;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {

    private final IProduct productRepository;
    private final IBranch branchRepository;

    public ProductService (IProduct product, IBranch branch)
    {
        this.productRepository = product;
        this.branchRepository = branch;
    }

    /**
     * Obtiene la lista de productos asociados a una sucursal específica.
     *
     * @param idBranch Identificador único de la sucursal.
     * @return ApiResponseDTO que contiene:
     *         - Una lista de productos (ProductListDTO) si la operación es exitosa.
     *         - Un mensaje de error en caso de que ocurra una excepción.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<List<ProductListDTO>> IndexProducts(Integer idBranch)
    {
        try
        {
            List<ProductListDTO> products = this.productRepository.IndexProduct(idBranch);
            return ApiResponseDTO.Success("Se ha obtenido la lista de productos con exito", products);
        }
        catch (PersistenceException | IllegalArgumentException ex)
        {
            String message = "Ha ocurrido un error" + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
        catch (Exception ex)
        {
            String message = "Ha ocurrido un error " + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
    }

    /**
     * Obtiene la información detallada de un producto a partir de su identificador.
     *
     * @param id Identificador único del producto.
     * @return ApiResponseDTO que contiene:
     *         - El detalle del producto (ProductDTO) si existe.
     *         - Un mensaje de error si el producto no se encuentra registrado.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<ProductDTO> SearchById(int id)
    {
        try
        {
            Optional<ProductEntity> product = this.productRepository.findById(id);

            if (product.isEmpty())
            {
                String message = "El producto no se encuentra registrado en el sistema";
                return ApiResponseDTO.Error(message);
            }

            return ApiResponseDTO.Success("Producto encontrado con exito", ProductResponse(product.get()));
        }
        catch (PersistenceException | IllegalArgumentException ex)
        {
            String message = "Ha ocurrido un error" + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
        catch (Exception ex)
        {
            String message = "Ha ocurrido un error " + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
    }

    /**
     * Crea un nuevo producto asociado a una sucursal existente.
     * Realiza las siguientes validaciones:
     * - Verifica que no exista un producto con el mismo nombre en la sucursal.
     * - Verifica que la sucursal asociada exista.
     *
     * @param productDTO Objeto DTO que contiene los datos del producto a crear.
     * @return ApiResponseDTO que contiene:
     *         - El producto creado (ProductDTO) si la operación es exitosa.
     *         - Un mensaje de error si el producto ya existe o la sucursal no es válida.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si los datos recibidos son inválidos.
     */
    public ApiResponseDTO<ProductDTO> CreateProduct(ProductDTO productDTO)
    {
        try
        {
            Optional<ProductEntity> productSearch = this.productRepository.findByName(productDTO.getBranchId(), productDTO.getName());

            if (productSearch.isPresent())
            {
                String message = "Ya hay un producto registrado en esta sucursal con este nombre";
                return ApiResponseDTO.Error(message);
            }

            Optional<BranchEntity> branch = this.branchRepository.findById(productDTO.getBranchId());

            if (branch.isEmpty())
            {
                String message = "La sucursal seleccionada no se encuentra registrada en el sistema";
                return  ApiResponseDTO.Error(message);
            }

            ProductEntity product = new ProductEntity();
            product.setName(productDTO.getName());
            product.setStock(productDTO.getStock());
            product.setBranch(branch.get());

            this.productRepository.save(product);

            return  ApiResponseDTO.Success("El producto ha sido agregado con exito a la sucursal", ProductResponse(product));

        }
        catch (PersistenceException | IllegalArgumentException ex)
        {
            String message = "Ha ocurrido un error" + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
        catch (Exception ex)
        {
            String message = "Ha ocurrido un error " + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
    }

    /**
     * Actualiza la información de un producto existente.
     * Realiza las siguientes validaciones:
     * - Verifica que el producto exista.
     * - Verifica que no exista otro producto con el mismo nombre dentro de la misma sucursal.
     * - Verifica que la sucursal asociada exista.
     *
     * @param id Identificador único del producto a actualizar.
     * @param productDTO Objeto DTO con la información actualizada del producto.
     * @return ApiResponseDTO que contiene:
     *         - El producto actualizado (ProductDTO) si la operación es exitosa.
     *         - Un mensaje de error si el producto o la sucursal no existen.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si los datos recibidos son inválidos.
     */
    public ApiResponseDTO<ProductDTO> UpdateProduct(int id, ProductDTO productDTO)
    {
        try
        {
            Optional<ProductEntity> ProductSearch = this.productRepository.findById(id);

            if (ProductSearch.isEmpty())
            {
                String message = "El producto no se encuentra registrado en el sistema";
                return ApiResponseDTO.Error(message);
            }

            Optional<ProductEntity> ProductSearchByName = this.productRepository.findByNameId(productDTO.getBranchId(), productDTO.getName(), id);

            if (ProductSearchByName.isPresent())
            {
                String message = "Ya hay un producto registrado en esta sucursal con ese nombre";
                return ApiResponseDTO.Error(message);
            }

            Optional<BranchEntity> Branch = this.branchRepository.findById(productDTO.getBranchId());

            if (Branch.isEmpty())
            {
                String message = "La sucursal seleccionada no ha sido registrada en el sistema";
                return  ApiResponseDTO.Error(message);
            }

            ProductEntity product = ProductSearch.get();
            product.setName(productDTO.getName());
            product.setStock(productDTO.getStock());
            product.setBranch(Branch.get());
            this.productRepository.save(product);

            return ApiResponseDTO.Success("Se ha actualizado con exito el producto", ProductResponse(product));
        }
        catch (PersistenceException | IllegalArgumentException ex)
        {
            String message = "Ha ocurrido un error" + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
        catch (Exception ex)
        {
            String message = "Ha ocurrido un error " + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
    }

    /**
     * Elimina un producto del sistema a partir de su identificador.
     *
     * @param id Identificador único del producto a eliminar.
     * @return ApiResponseDTO que contiene:
     *         - La información del producto eliminado (ProductDTO) si la operación es exitosa.
     *         - Un mensaje de error si el producto no se encuentra registrado.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<ProductDTO> DeleteProduct(Integer id)
    {
        try
        {
            Optional<ProductEntity> Product = this.productRepository.findById(id);

            if (Product.isEmpty())
            {
                String message = "El producto seleccionado no se encuentra registrado en el sistema";
                return ApiResponseDTO.Error(message);
            }

            this.productRepository.delete(Product.get());
            return ApiResponseDTO.Success("Se ha eliminado con exito el producto", ProductResponse(Product.get()));
        }
        catch (PersistenceException | IllegalArgumentException ex)
        {
            String message = "Ha ocurrido un error" + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }
        catch (Exception ex)
        {
            String message = "Ha ocurrido un error " + ex.getMessage();
            return  ApiResponseDTO.Error(message);
        }

    }

    /**
     * Mapea una entidad ProductEntity a su correspondiente DTO (ProductDTO).
     * Este método se utiliza para estandarizar la información de salida
     * y evitar exponer directamente la entidad de persistencia.
     *
     * @param product Entidad ProductEntity a convertir.
     * @return Objeto ProductDTO con la información mapeada.
     */
    private ProductDTO ProductResponse(ProductEntity product)
    {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setStock(product.getStock());
        productDTO.setName(product.getName());
        productDTO.setBranchId(product.getBranch().getId());
        productDTO.setId(product.getId());
        return productDTO;
    }

}
