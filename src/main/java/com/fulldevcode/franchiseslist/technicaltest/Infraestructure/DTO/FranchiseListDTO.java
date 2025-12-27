package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FranchiseListDTO {

    private Integer id;

    private String name;

    private Long branchesNum;

    public FranchiseListDTO(Integer id, String name, Long branchesNum) 
    {
        this.id = id;
        this.name = name;
        this.branchesNum = branchesNum;
    }

}
