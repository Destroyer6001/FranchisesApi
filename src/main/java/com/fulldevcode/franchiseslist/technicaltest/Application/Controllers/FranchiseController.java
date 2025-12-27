package com.fulldevcode.franchiseslist.technicaltest.Application.Controllers;

import com.fulldevcode.franchiseslist.technicaltest.Domain.Services.FranchiseService;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ApiResponseDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchMaxProductsListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.FranchiseDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.FranchiseListDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas
 * con las franquicias del sistema.
 * Expone endpoints para:
 * - Consultar todas las franquicias.
 * - Obtener el detalle de una franquicia.
 * - Crear, actualizar y eliminar franquicias.
 * - Consultar las sucursales con mayor número de productos por franquicia.
 * Todas las respuestas siguen el formato estándar ApiResponseDTO.
 */
@RestController
@RequestMapping("api/franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchise)
    {
        this.franchiseService = franchise;
    }

    /**
     * Obtiene la lista de todas las franquicias registradas en el sistema.
     *
     * @return ApiResponseDTO que contiene:
     *         - Una lista de franquicias (FranchiseListDTO) si la operación es exitosa.
     *         - Un mensaje de error en caso de que ocurra una excepción.
     * Método HTTP: GET
     * Endpoint: /api/franchises
     */
    @GetMapping()
    public ApiResponseDTO<List<FranchiseListDTO>> FranchisesIndex()
    {
        ApiResponseDTO<List<FranchiseListDTO>> franchises = this.franchiseService.IndexFranchise();
        return franchises;
    }

    /**
     * Obtiene la información detallada de una franquicia a partir de su identificador.
     *
     * @param id Identificador único de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - El detalle de la franquicia (FranchiseDTO) si existe.
     *         - Un mensaje de error si la franquicia no se encuentra registrada.
     * Método HTTP: GET
     * Endpoint: /api/franchises/{id}
     */
    @GetMapping("/{id}")
    public ApiResponseDTO<FranchiseDTO> GetFranchiseById(@PathVariable Integer id)
    {
        ApiResponseDTO<FranchiseDTO> franchise = this.franchiseService.SearchFranchiseById(id);
        return franchise;
    }

    /**
     * Crea una nueva franquicia en el sistema.
     *
     * @param franchiseDTO Objeto DTO que contiene los datos de la franquicia a crear.
     * @return ApiResponseDTO que contiene:
     *         - La franquicia creada (FranchiseDTO) si la operación es exitosa.
     *         - Un mensaje de error si la franquicia ya existe o los datos son inválidos.
     * Método HTTP: POST
     * Endpoint: /api/franchises
     */
    @PostMapping()
    public ApiResponseDTO<FranchiseDTO> Create (@RequestBody FranchiseDTO franchiseDTO)
    {
        ApiResponseDTO<FranchiseDTO> franchise = this.franchiseService.CreateFrachise(franchiseDTO);
        return franchise;
    }

    /**
     * Actualiza la información de una franquicia existente.
     *
     * @param id Identificador único de la franquicia a actualizar.
     * @param franchiseDTO Objeto DTO con la información actualizada de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - La franquicia actualizada (FranchiseDTO) si la operación es exitosa.
     *         - Un mensaje de error si la franquicia no existe o los datos son inválidos.
     * Método HTTP: PUT
     * Endpoint: /api/franchises/{id}
     */
    @PutMapping("/{id}")
    public ApiResponseDTO<FranchiseDTO> Update (@PathVariable Integer id, @RequestBody FranchiseDTO franchiseDTO)
    {
        ApiResponseDTO<FranchiseDTO> franchise = this.franchiseService.UpdateFranchise(id, franchiseDTO);
        return franchise;
    }

    /**
     * Elimina una franquicia del sistema a partir de su identificador.
     * Al eliminar una franquicia, también se eliminan las sucursales
     * y productos asociados según la configuración de la capa de dominio.
     *
     * @param id Identificador único de la franquicia a eliminar.
     * @return ApiResponseDTO que contiene:
     *         - La información de la franquicia eliminada (FranchiseDTO) si la operación es exitosa.
     *         - Un mensaje de error si la franquicia no se encuentra registrada.
     * Método HTTP: DELETE
     * Endpoint: /api/franchises/{id}
     */
    @DeleteMapping("/{id}")
    public ApiResponseDTO<FranchiseDTO> Delete (@PathVariable Integer id)
    {
        ApiResponseDTO<FranchiseDTO> franchise = this.franchiseService.DeleteFranchise(id);
        return franchise;
    }

    /**
     * Obtiene las sucursales de una franquicia junto con el producto
     * que tiene el mayor stock en cada una de ellas.
     *
     * @param id Identificador único de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - Una lista de sucursales con su producto de mayor stock (BranchMaxProductsListDTO).
     *         - Un mensaje de error si la franquicia no existe o no tiene sucursales registradas.
     * Método HTTP: GET
     * Endpoint: /api/franchises/getBranchMaxProducts/{id}
     */
    @GetMapping("/getBranchMaxProducts/{id}")
    public ApiResponseDTO<List<BranchMaxProductsListDTO>> GetBranchMaxProducts(@PathVariable Integer id)
    {
        ApiResponseDTO<List<BranchMaxProductsListDTO>> branchesMaxProduct = this.franchiseService.GetBranchMaxProducts(id);
        return branchesMaxProduct;
    }
}
