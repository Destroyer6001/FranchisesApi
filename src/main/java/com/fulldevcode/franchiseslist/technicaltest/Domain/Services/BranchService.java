package com.fulldevcode.franchiseslist.technicaltest.Domain.Services;

import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ApiResponseDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface.IBranch;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface.IFranchise;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.BranchEntity;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.FranchiseEntity;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    private final IBranch branchRepository;
    private final IFranchise franchiseRepository;

    public BranchService(IBranch branch, IFranchise franchise)
    {
        this.branchRepository = branch;
        this.franchiseRepository = franchise;
    }

    /**
     * Obtiene la lista de sucursales asociadas a una franquicia específica.
     *
     * @param idFranchise Identificador único de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - Una lista de sucursales (BranchListDTO) si la operación es exitosa.
     *         - Un mensaje de error en caso de que ocurra una excepción o la franquicia no exista.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el parámetro recibido es inválido.
     */
    public ApiResponseDTO<List<BranchListDTO>> branchIndex(Integer idFranchise)
    {
        try
        {
            List<BranchListDTO> branches = this.branchRepository.IndexBranch(idFranchise);
            return ApiResponseDTO.Success("Se ha obtenido con exito la lista de sucursales", branches);
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
     * Obtiene la información de una sucursal a partir de su identificador.
     *
     * @param id Identificador único de la sucursal.
     * @return ApiResponseDTO que contiene:
     *         - El detalle de la sucursal (BranchDTO) si existe.
     *         - Un mensaje de error si la sucursal no se encuentra registrada.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<BranchDTO> SearchById(Integer id)
    {
        try
        {
            Optional<BranchEntity> Branch = this.branchRepository.findById(id);

            if (Branch.isEmpty())
            {
                String message = "La sucursal buscada no ha sido registrada en el sistema";
                return  ApiResponseDTO.Error(message);
            }

            return  ApiResponseDTO.Success("Se ha obtenido con exito la sucursal", BranchResponse(Branch.get()));
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
     * Crea una nueva sucursal asociada a una franquicia existente.
     * Realiza las siguientes validaciones:
     * - Verifica que no exista una sucursal con el mismo nombre en la franquicia.
     * - Verifica que la franquicia asociada exista.
     *
     * @param branchDTO Objeto DTO que contiene los datos de la sucursal a crear.
     * @return ApiResponseDTO que contiene:
     *         - La sucursal creada (BranchDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal ya existe o la franquicia no es válida.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si los datos recibidos son inválidos.
     */
    public ApiResponseDTO<BranchDTO> CreateProduct (BranchDTO branchDTO)
    {
        try
        {
            Optional<BranchEntity> SearchBranch = this.branchRepository.FindByName(branchDTO.getFranchiseId(), branchDTO.getName());

            if (SearchBranch.isPresent())
            {
                String message = "Ya hay una sucursal registrada en esta franquicia con ese nombre";
                return ApiResponseDTO.Error(message);
            }

            Optional<FranchiseEntity> Franchise = this.franchiseRepository.findById(branchDTO.getFranchiseId());

            if (Franchise.isEmpty())
            {
                String message = "La franquicia seleccionado no existe en el sistema";
                return ApiResponseDTO.Error(message);
            }

            BranchEntity Branch = new BranchEntity();
            Branch.setName(branchDTO.getName());
            Branch.setFranchise(Franchise.get());
            this.branchRepository.save(Branch);

            return ApiResponseDTO.Success("Se ha añadido con eso la nueva sucursal a la franquicia", BranchResponse(Branch));
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
     * Actualiza la información de una sucursal existente.
     * Realiza las siguientes validaciones:
     * - Verifica que la sucursal exista.
     * - Verifica que no exista otra sucursal con el mismo nombre dentro de la misma franquicia.
     * - Verifica que la franquicia asociada exista.
     *
     * @param id Identificador único de la sucursal a actualizar.
     * @param branchDTO Objeto DTO con la información actualizada de la sucursal.
     * @return ApiResponseDTO que contiene:
     *         - La sucursal actualizada (BranchDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal o la franquicia no existen.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si los datos recibidos son inválidos.
     */
    public ApiResponseDTO<BranchDTO> UpdateBranch(Integer id, BranchDTO branchDTO)
    {
        try
        {
            Optional<BranchEntity> BranchSearch = this.branchRepository.findById(id);

            if (BranchSearch.isEmpty())
            {
                String message = "La sucursal seleccionada no se encuentra registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            Optional<BranchEntity> BranchValidateName = this.branchRepository.FindByIdName(branchDTO.getFranchiseId(), branchDTO.getName(), id);

            if (BranchValidateName.isPresent())
            {
                String message = "Ya hay una sucursal registrada con este nombre que pertenece a esta franquicia";
                return ApiResponseDTO.Error(message);
            }

            Optional<FranchiseEntity> Franchise = this.franchiseRepository.findById(branchDTO.getFranchiseId());

            if (Franchise.isEmpty())
            {
                String message = "La franquicia seleccionada no he sido registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            BranchEntity Branch = BranchSearch.get();
            Branch.setName(branchDTO.getName());
            Branch.setFranchise(Franchise.get());
            this.branchRepository.save(Branch);

            return ApiResponseDTO.Success("Se ha actualizado con exito la sucursal", BranchResponse(Branch));
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
     * Elimina una sucursal del sistema a partir de su identificador.
     *
     * @param id Identificador único de la sucursal a eliminar.
     * @return ApiResponseDTO que contiene:
     *         - La información de la sucursal eliminada (BranchDTO) si la operación es exitosa.
     *         - Un mensaje de error si la sucursal no se encuentra registrada.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<BranchDTO> DeleteBranch (Integer id)
    {
        try
        {
            Optional<BranchEntity> Branch = this.branchRepository.findById(id);

            if (Branch.isEmpty())
            {
                String message = "La sucursal seleccionada no se encuentra registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            this.branchRepository.delete(Branch.get());
            return ApiResponseDTO.Success("Se ha eliminado con exito la sucursal", BranchResponse(Branch.get()));
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
     * Mapea una entidad BranchEntity a su correspondiente DTO (BranchDTO).
     * Este método se utiliza para estandarizar la información de salida
     * y evitar exponer directamente la entidad de persistencia.
     *
     * @param branch Entidad BranchEntity a convertir.
     * @return Objeto BranchDTO con la información mapeada.
     */
    private BranchDTO BranchResponse (BranchEntity branch)
    {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setId(branch.getId());
        branchDTO.setName(branch.getName());
        branchDTO.setFranchiseId(branch.getFranchise().getId());
        return branchDTO;
    }
}
