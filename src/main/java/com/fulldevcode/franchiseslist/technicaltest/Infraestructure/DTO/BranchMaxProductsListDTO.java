package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchMaxProductsListDTO {

    private Integer id;

    private String nameBranch;

    private String nameProduct;

    private Integer stock;


    public BranchMaxProductsListDTO(Integer id, String nameBranch, String nameProduct, Integer stock)
    {
        this.id = id;
        this.nameBranch = nameBranch;
        this.nameProduct = nameProduct;
        this.stock = stock;
    }
}
