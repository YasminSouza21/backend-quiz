package com.backend.jogo.api.domain.quiz;

import com.backend.jogo.api.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "games")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Question> questions;

    private int score;

    @Column(name = "time_seconds")
    private LocalTime timeSeconds;

    @Column(name = "start_date")
    private LocalDateTime startDate;
}
