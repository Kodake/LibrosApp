package com.librosapp.servicio;

import com.librosapp.modelo.Libro;
import java.util.List;

public interface ILibroServicio {
    public List<Libro> listar();
    public Libro buscarPorId(Integer idLibro);
    public void guardar(Libro libro);
    public void eliminar(Libro libro);
}
