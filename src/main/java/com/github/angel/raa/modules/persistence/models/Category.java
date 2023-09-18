package com.github.angel.raa.modules.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 50)
    private String name;
}
