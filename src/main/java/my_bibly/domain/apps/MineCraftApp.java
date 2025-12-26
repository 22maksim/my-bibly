package my_bibly.domain.apps;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_bibly.domain.base.AppBase;
import my_bibly.domain.model.Inventory;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "minecraft_app")
@NoArgsConstructor
@AllArgsConstructor
public class MineCraftApp extends AppBase {

    @Column(nullable = false)
    private int shore;

    @ManyToMany
    @JoinTable(
            name = "app_to_inventory",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id")
    )
    private List<Inventory> inventories;

}
