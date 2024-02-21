package dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.Category;

@Data
@AllArgsConstructor
public class ProductRequesDto {
    private final String name;
    private final String description;
    private final Double startingPrice;
    private final Double finalPrice;
    private final String image;
    private final Category category;

}
