package com.example.technology.infrastructure.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("technology")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyEntity {

    @Id
    private Long id;

    private String name;

    private String description;
}
