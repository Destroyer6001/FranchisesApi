package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface;

import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchMaxProductsListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.FranchiseListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IFranchise extends JpaRepository<FranchiseEntity, Integer> {

    @Query("""
            SELECT f
            FROM FranchiseEntity f
            WHERE Lower(f.name) = Lower(:name)
            """)
    Optional<FranchiseEntity> SearchName(@Param("name") String name);

    @Query("""
            SELECT f
            FROM FranchiseEntity f
            WHERE Lower(f.name) = Lower(:name) AND f.id != :id
            """)
    Optional<FranchiseEntity> SearchNameById(@Param("name") String name, @Param("id") Integer id);

    @Query("""
            SELECT new com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.FranchiseListDTO(
                f.id,
                f.name,
                COUNT(b.id)
            )
            FROM FranchiseEntity f
            LEFT JOIN f.branches b
            GROUP BY f.id, f.name
            """)
    List<FranchiseListDTO> IndexFranchise();

    @Query("""
            SELECT new com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.BranchMaxProductsListDTO(
                b.id,
                b.name,
                p.name,
                p.stock
            )
            FROM BranchEntity b
            JOIN b.products p
            JOIN b.franchise f
            WHERE p.stock = (
                SELECT MAX(p2.stock)
                FROM ProductEntity p2
                WHERE p2.branch = b
            )
            AND p.id = (
                SELECT MIN(p3.id)
                FROM ProductEntity p3
                WHERE p3.branch = b
                AND p3.stock = p.stock
            )
            AND b.franchise.id = :id
            """)
    List<BranchMaxProductsListDTO> SearchBranchesMaxProducts(@Param("id") Integer id);


}
