package com.backend.jogo.api.domain.quiz;

import com.backend.jogo.api.dto.RequestCategoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "category")
    private List<Game> game;

    @OneToMany(mappedBy = "category")
    private List<Response> responses;

    public Category(RequestCategoryDTO categoryDto) {
        this.name = categoryDto.name();
    }
}
