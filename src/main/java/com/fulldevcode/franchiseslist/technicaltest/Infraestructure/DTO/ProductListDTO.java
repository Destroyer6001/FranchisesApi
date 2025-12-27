package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDTO {

    private Integer id;

    private String name;

    private Integer stock;

    private String branchName;

    public ProductListDTO(Integer id, String name, Integer stock, String branchName)
    {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branchName = branchName;
    }
}
