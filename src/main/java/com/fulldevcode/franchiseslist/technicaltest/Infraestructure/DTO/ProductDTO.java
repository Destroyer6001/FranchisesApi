package com.fulldevcode.franchiseslist.technicaltest.Infraestructure.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Integer id;

    private String name;

    private Integer stock;

    private Integer branchId;

}
