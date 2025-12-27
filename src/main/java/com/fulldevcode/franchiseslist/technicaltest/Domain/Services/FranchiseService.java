package com.fulldevcode.franchiseslist.technicaltest.Domain.Services;

import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.*;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface.IFranchise;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.FranchiseEntity;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FranchiseService {

    private final IFranchise franchiseRepository;

    public FranchiseService(IFranchise franchise)
    {
        this.franchiseRepository = franchise;
    }

    /**
     * Obtiene la lista de todas las franquicias registradas en el sistema.
     *
     * @return ApiResponseDTO que contiene:
     *         - Una lista de franquicias (FranchiseListDTO) si la operación es exitosa.
     *         - Un mensaje de error en caso de que ocurra una excepción.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si ocurre un error por argumentos inválidos.
     */
    public ApiResponseDTO<List<FranchiseListDTO>> IndexFranchise()
    {
        try
        {
            List<FranchiseListDTO> Franchises = this.franchiseRepository.IndexFranchise();
            return ApiResponseDTO.Success("Lista de franquicias obtenida con exito", Franchises);
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
     * Obtiene la información detallada de una franquicia a partir de su identificador.
     *
     * @param id Identificador único de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - El detalle de la franquicia (FranchiseDTO) si existe.
     *         - Un mensaje de error si la franquicia no se encuentra registrada.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<FranchiseDTO> SearchFranchiseById(Integer id)
    {
        try
        {
            Optional<FranchiseEntity> Franchise = this.franchiseRepository.findById(id);

            if (Franchise.isEmpty())
            {
                String message = "La franquicia seleccionada no se encuentra registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            return ApiResponseDTO.Success("Se ha obtenido con exito la franquicia", ResponseFranchise(Franchise.get()));
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
     * Crea una nueva franquicia en el sistema.
     * Realiza la siguiente validación:
     * - Verifica que no exista una franquicia con el mismo nombre.
     *
     * @param franchiseDTO Objeto DTO que contiene la información de la franquicia a crear.
     * @return ApiResponseDTO que contiene:
     *         - La franquicia creada (FranchiseDTO) si la operación es exitosa.
     *         - Un mensaje de error si ya existe una franquicia con el mismo nombre.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si los datos recibidos son inválidos.
     */
    public ApiResponseDTO<FranchiseDTO> CreateFrachise(FranchiseDTO franchiseDTO)
    {
        try
        {
            Optional<FranchiseEntity> SearchFranchise = this.franchiseRepository.SearchName(franchiseDTO.getName());

            if (SearchFranchise.isPresent())
            {
                String message = "Ya ha una franquicia registrada en el sistema con ese nombre";
                return ApiResponseDTO.Error(message);
            }

            FranchiseEntity Franchise = new FranchiseEntity();
            Franchise.setName(franchiseDTO.getName());
            this.franchiseRepository.save(Franchise);

            return  ApiResponseDTO.Success("Se ha creado con exito la franquicia", ResponseFranchise(Franchise));
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
     * Actualiza la información de una franquicia existente.
     * Realiza las siguientes validaciones:
     * - Verifica que la franquicia exista.
     * - Verifica que no exista otra franquicia con el mismo nombre.
     *
     * @param id Identificador único de la franquicia a actualizar.
     * @param franchiseDTO Objeto DTO con la información actualizada de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - La franquicia actualizada (FranchiseDTO) si la operación es exitosa.
     *         - Un mensaje de error si la franquicia no existe o el nombre ya está registrado.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si los datos recibidos son inválidos.
     */
    public ApiResponseDTO<FranchiseDTO> UpdateFranchise(Integer id, FranchiseDTO franchiseDTO)
    {
        try
        {
            Optional<FranchiseEntity> FranchiseSearch = this.franchiseRepository.findById(id);

            if (FranchiseSearch.isEmpty())
            {
                String message = "La franquicia seleccionada no ha sido registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            Optional<FranchiseEntity> ValidateNameFranchise = this.franchiseRepository.SearchNameById(franchiseDTO.getName(), id);

            if (ValidateNameFranchise.isPresent())
            {
                String message = "Ya hay una franquicia registrada con ese nombre";
                return ApiResponseDTO.Error(message);
            }

            FranchiseEntity Franchise = FranchiseSearch.get();
            Franchise.setName(franchiseDTO.getName());
            this.franchiseRepository.save(Franchise);

            return ApiResponseDTO.Success("Se ha actualizado con exito la franquicia", ResponseFranchise(Franchise));
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
     * Elimina una franquicia del sistema a partir de su identificador.
     *
     * @param id Identificador único de la franquicia a eliminar.
     * @return ApiResponseDTO que contiene:
     *         - La información de la franquicia eliminada (FranchiseDTO) si la operación es exitosa.
     *         - Un mensaje de error si la franquicia no se encuentra registrada.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<FranchiseDTO> DeleteFranchise(Integer id)
    {
        try
        {
            Optional<FranchiseEntity> Franchise = this.franchiseRepository.findById(id);

            if (Franchise.isEmpty())
            {
                String message = "La franquicia seleccionada no ha sido registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            this.franchiseRepository.delete(Franchise.get());
            return ApiResponseDTO.Success("Se ha eliminado con exito la franquicia", ResponseFranchise(Franchise.get()));

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
     * Obtiene las sucursales de una franquicia junto con el producto
     * que posee la mayor cantidad de stock en cada sucursal.
     *
     * @param id Identificador único de la franquicia.
     * @return ApiResponseDTO que contiene:
     *         - Una lista de sucursales con su producto de mayor stock (BranchMaxProductsListDTO).
     *         - Un mensaje de error si la franquicia no existe.
     *
     * @throws PersistenceException Si ocurre un error de acceso a datos.
     * @throws IllegalArgumentException Si el identificador es inválido.
     */
    public ApiResponseDTO<List<BranchMaxProductsListDTO>> GetBranchMaxProducts(Integer id)
    {
        try
        {
            Optional<FranchiseEntity> Franchise = this.franchiseRepository.findById(id);

            if (Franchise.isEmpty())
            {
                String message = "La franquicia de la cual se desean ver sus sucursales con sus productos con mas stock no se encuentre registrada en el sistema";
                return ApiResponseDTO.Error(message);
            }

            List<BranchMaxProductsListDTO> FranchisesBranches = this.franchiseRepository.SearchBranchesMaxProducts(id);
            return ApiResponseDTO.Success("Se ha obtenido con exito las sucursales con su producto con mas stock", FranchisesBranches);
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
     * Mapea una entidad FranchiseEntity a su correspondiente DTO (FranchiseDTO).
     * Este método se utiliza para estandarizar la información de salida
     * y evitar exponer directamente la entidad de persistencia.
     *
     * @param franchise Entidad FranchiseEntity a convertir.
     * @return Objeto FranchiseDTO con la información mapeada.
     */
    private FranchiseDTO ResponseFranchise(FranchiseEntity franchise)
    {
        FranchiseDTO franchiseResponse = new FranchiseDTO();
        franchiseResponse.setId(franchise.getId());
        franchiseResponse.setName(franchise.getName());
        return franchiseResponse;
    }
}
