package com.fulldevcode.franchiseslist.technicaltest.Application.Controllers;

import com.fulldevcode.franchiseslist.technicaltest.Domain.Services.BranchService;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ApiResponseDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchListDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de gestionar las operaciones relacionadas
 * con las sucursales (branches) del sistema.
 * Expone endpoints para:
 * - Consultar sucursales por franquicia.
 * - Obtener el detalle de una sucursal.
 * - Crear, actualizar y eliminar sucursales.
 * Todas las respuestas siguen el formato estándar ApiResponseDTO.
 */
@RestController
@RequestMapping("api/branches")
public class BranchController {

    private final BranchService brachesService;

    public BranchController (BranchService branch)
    {
        this.brachesService = branch;
    }

    /**
     * Obtiene la lista de sucursales asociadas a una franquicia específica.
     *
     * @param idFranchise Identificador único de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - Una lista de sucursales (BranchListDTO) si la operación es exitosa.
     *         - Un mensaje de error en caso de que la franquicia no exista
     *           o ocurra una excepción.
     * Método HTTP: GET
     * Endpoint: /api/branches/getAll/{idFranchise}
     */
    @GetMapping("getAll/{idFranchise}")
    public ApiResponseDTO<List<BranchListDTO>> IndexBranches (@PathVariable Integer idFranchise)
    {
        ApiResponseDTO<List<BranchListDTO>> branches = this.brachesService.branchIndex(idFranchise);
        return branches;
    }

    /**
     * Obtiene la información detallada de una sucursal a partir de su identificador.
     *
     * @param id Identificador único de la sucursal.
     * @return ApiResponseDTO que contiene:
     *         - El detalle de la sucursal (BranchDTO) si existe.
     *         - Un mensaje de error si la sucursal no se encuentra registrada.
     * Método HTTP: GET
     * Endpoint: /api/branches/{id}
     */
    @GetMapping("/{id}")
    public ApiResponseDTO<BranchDTO> GetBranchById(@PathVariable Integer id)
    {
        ApiResponseDTO<BranchDTO> branch = this.brachesService.SearchById(id);
        return branch;
    }

    /**
     * Crea una nueva sucursal asociada a una franquicia existente.
     *
     * @param branchDTO Objeto DTO que contiene los datos de la sucursal a crear.
     * @return ApiResponseDTO que contiene:
     *         - La sucursal creada (BranchDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal ya existe o la franquicia no es válida.
     * Método HTTP: POST
     * Endpoint: /api/branches
     */
    @PostMapping()
    public ApiResponseDTO<BranchDTO> Create (@RequestBody BranchDTO branchDTO)
    {
        ApiResponseDTO<BranchDTO> branch = this.brachesService.CreateProduct(branchDTO);
        return branch;
    }

    /**
     * Actualiza la información de una sucursal existente.
     *
     * @param id Identificador único de la sucursal a actualizar.
     * @param branchDTO Objeto DTO con la información actualizada de la sucursal.
     * @return ApiResponseDTO que contiene:
     *         - La sucursal actualizada (BranchDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal no existe o los datos son inválidos.
     * Método HTTP: PUT
     * Endpoint: /api/branches/{id}
     */
    @PutMapping("/{id}")
    public ApiResponseDTO<BranchDTO> Update (@PathVariable Integer id, @RequestBody BranchDTO branchDTO)
    {
        ApiResponseDTO<BranchDTO> branch = this.brachesService.UpdateBranch(id, branchDTO);
        return branch;
    }

    /**
     * Elimina una sucursal del sistema a partir de su identificador.
     *
     * @param id Identificador único de la sucursal a eliminar.
     * @return ApiResponseDTO que contiene:
     *         - La información de la sucursal eliminada (BranchDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal no se encuentra registrada.
     * Método HTTP: DELETE
     * Endpoint: /api/branches/{id}
     */
    @DeleteMapping("/{id}")
    public ApiResponseDTO<BranchDTO> Delete (@PathVariable Integer id)
    {
        ApiResponseDTO<BranchDTO> branch = this.brachesService.DeleteBranch(id);
        return branch;
    }
}
