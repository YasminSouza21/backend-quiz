package com.backend.jogo.api.domain.users;

import com.backend.jogo.api.domain.quiz.Game;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
    private int age;

    @OneToMany(mappedBy = "userId")
    private List<Game> games;

    private int getAge(){
        int currentYear = LocalDate.now().getYear();
        int birthdayYear = birthday.getYear();
        return this.age = currentYear - birthdayYear;
    }
}
