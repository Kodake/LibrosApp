package com.librosapp.servicio;

import com.librosapp.modelo.Libro;
import com.librosapp.repositorio.ILibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServicio implements ILibroServicio {

    @Autowired
    private ILibroRepositorio libroRepositorio;

    @Override
    public List<Libro> listar() {
        return libroRepositorio.findAll();
    }

    @Override
    public Libro buscarPorId(Integer idLibro) {
        Libro libro = libroRepositorio.findById(idLibro).orElse(null);
        return libro;
    }

    @Override
    public void guardar(Libro libro) {
        libroRepositorio.save(libro);
    }

    @Override
    public void eliminar(Libro libro) {
        libroRepositorio.delete(libro);
    }
}
