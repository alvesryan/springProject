package com.devryan.course.repositories;

import com.devryan.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository é uma interface do spring Data que ao estendê-lo, o spring implementa os métodos de básicos de um bando de dados
@Repository // indica que essa é uma interface atua como componente de acesso a dados(embora seja uma anotação opcional)
public interface UserRepository extends JpaRepository<User, Long> {

}
