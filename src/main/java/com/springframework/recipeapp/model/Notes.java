package com.springframework.recipeapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(exclude = {"recipe"})
@Data
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    // @Lob Allows very large String characters (way bigger than ~255 characters by default)
    @Lob
    private String recipeNotes;

}
