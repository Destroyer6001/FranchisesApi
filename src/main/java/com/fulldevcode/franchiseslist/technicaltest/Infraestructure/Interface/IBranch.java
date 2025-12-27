package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface;

import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.BranchEntity;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface IBranch extends JpaRepository<BranchEntity, Integer> {

    @Query("""
            SELECT b
            FROM BranchEntity b
            WHERE b.franchise.id = :idFranchise AND Lower(name) = Lower(:name)
            """)
    Optional<BranchEntity> FindByName (@Param("idFranchise") Integer idFranchise, @Param("name") String name);

    @Query("""
            SELECT b
            FROM BranchEntity b
            WHERE b.franchise.id = :idFranchise AND Lower(b.name) = Lower(:name) AND b.id != :id
            """)
    Optional<BranchEntity> FindByIdName (@Param("idFranchise") Integer idFranchise, @Param("name") String name, @Param("id") Integer id);

    @Query("""
            SELECT new com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchListDTO(
                b.id,
                b.name,
                COUNT(p.id),
                b.franchise.name
            )
            FROM BranchEntity b
            LEFT JOIN b.products p
            WHERE b.franchise.id = :idFranchise
            GROUP BY b.id, b.name, b.franchise.name
            """)
    List<BranchListDTO> IndexBranch (@PathVariable("idFranchise") Integer idFranchise);

}
