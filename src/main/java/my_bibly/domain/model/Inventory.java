package my_bibly.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import my_bibly.domain.base.BaseDefaultEntity;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseDefaultEntity {

    @Column(name = "action", nullable = false)
    private String action;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private InventType type;

}
