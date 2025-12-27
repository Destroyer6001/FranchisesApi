package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchListDTO {

    private Integer id;

    private String name;

    private Long productsNum;

    private String franchiseName;

    public BranchListDTO (Integer id, String name, Long productsNum, String franchiseName)
    {
        this.id = id;
        this.name = name;
        this.productsNum = productsNum;
        this.franchiseName = franchiseName;
    }
}
