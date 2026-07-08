package com.example.technology.infrastructure.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("capacity_technology")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CapacityTechnologyEntity {
    @Id
    private Long id;

    private Long technologyId;

    private Long capacityId;
}
