package alexDavid.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends Product {
    private Integer level_required;
    private LocalDateTime time_starts;
    private LocalDateTime time_ends;
    private Integer available_spaces;
    private Boolean available;

    // Constructors
    public Activity(Long id, String name, String description, Double starting_price, Double final_price,
                    String image, Category category, Integer level_required, LocalDateTime start,
                    LocalDateTime end, Integer available_spaces, Boolean available, String tag) {
        super(id, name, description, starting_price, final_price, image, category, tag);
        this.level_required = level_required;
        this.time_starts = start;
        this.time_ends = end;
        this.available_spaces = available_spaces;
        this.available = available;
    }
}
