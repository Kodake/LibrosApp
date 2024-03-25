package com.librosapp.repositorio;

import com.librosapp.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILibroRepositorio extends JpaRepository<Libro, Integer> {
}
