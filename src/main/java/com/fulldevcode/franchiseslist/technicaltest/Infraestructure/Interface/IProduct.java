package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Interface;

import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ProductListDTO;
import com.fulldevcode.franchiseslist.technicaltest.Infraestructure.Models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface IProduct extends JpaRepository<ProductEntity, Integer> {

    @Query("""
            SELECT p
            FROM ProductEntity p
            WHERE p.branch.id = :idBranch
            AND Lower(p.name) = Lower(:name)
            """)
    Optional<ProductEntity> findByName (@Param("idBranch") Integer idBranch, @Param("name") String name);

    @Query("""
            SELECT p
            FROM ProductEntity p
            WHERE p.branch.id = :idBranch
            AND Lower(p.name) = Lower(:name)
            AND p.id != :id
            """)
    Optional<ProductEntity> findByNameId (@Param("idBranch") Integer idBranch, @Param("name") String name, @Param("id") Integer id);

    @Query("""
            SELECT new com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO.ProductListDTO(
                p.id,
                p.name,
                p.stock,
                p.branch.name
            )
            FROM ProductEntity p
            WHERE p.branch.id = :idBranch
            """)
    List<ProductListDTO> IndexProduct(@Param("idBranch") Integer idBranch);

}
