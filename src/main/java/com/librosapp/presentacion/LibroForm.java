package com.librosapp.presentacion;

import com.librosapp.modelo.Libro;
import com.librosapp.servicio.ILibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class LibroForm extends JFrame {
    ILibroServicio libroServicio;
    private JPanel panel;
    private JTable tablaLibros;
    private JTextField idTexto;
    private JLabel Libro;
    private JTextField libroTexto;
    private JLabel Autor;
    private JTextField autorTexto;
    private JLabel Precio;
    private JTextField precioTexto;
    private JLabel Existencias;
    private JTextField existenciasTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private DefaultTableModel tablaModeloLibros;

    @Autowired
    public LibroForm(ILibroServicio libroServicio) {
        this.libroServicio = libroServicio;
        iniciarForm();
        agregarButton.addActionListener(e -> agregarLibro());
        modificarButton.addActionListener(e -> actualizarLibro());
        eliminarButton.addActionListener(e -> eliminarLibro());
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarLibroSeleccionado();
            }
        });
    }

    public void cargarLibroSeleccionado() {
        modificarButton.setVisible(true);
        agregarButton.setVisible(false);
        var fila = tablaLibros.getSelectedRow();
        if (fila != -1) {
            String idLibro = tablaLibros.getModel().getValueAt(fila, 0).toString();
            idTexto.setText(idLibro);
            String nombre = tablaLibros.getModel().getValueAt(fila, 1).toString();
            libroTexto.setText(nombre);
            String autor = tablaLibros.getModel().getValueAt(fila, 2).toString();
            autorTexto.setText(autor);
            String precio = tablaLibros.getModel().getValueAt(fila, 3).toString();
            precioTexto.setText(precio);
            String existencias = tablaLibros.getModel().getValueAt(fila, 4).toString();
            existenciasTexto.setText(existencias);
        }
    }

    private void agregarLibro() {
        if (libroTexto.getText().equals("")) {
            mostrarMensaje("Proporciona el nombre del libro");
            libroTexto.requestFocusInWindow();
            return;
        }
        var nombre = libroTexto.getText();
        var autor = autorTexto.getText();
        var precio = Double.parseDouble(precioTexto.getText());
        var existencias = Integer.parseInt(existenciasTexto.getText());

        var libro = new Libro(null, nombre, autor, precio, existencias);
        this.libroServicio.guardar(libro);
        mostrarMensaje("Libro agregado satisfactoriamente.");
        limpiarForm();
        listarLibros();
    }

    private void actualizarLibro() {
        if (this.idTexto.getText().equals("")) {
            mostrarMensaje("Debe seleccionar un registro.");
        } else {
            if (libroTexto.getText().equals("")) {
                mostrarMensaje("Proporciona el nombre del libro");
                libroTexto.requestFocusInWindow();
                return;
            }
            int idLibro = Integer.parseInt(idTexto.getText());
            var nombre = libroTexto.getText();
            var autor = autorTexto.getText();
            var precio = Double.parseDouble(precioTexto.getText());
            var existencias = Integer.parseInt(existenciasTexto.getText());
            var libro = new Libro(idLibro, nombre, autor, precio, existencias);
            libroServicio.guardar(libro);
            mostrarMensaje("Libro actualizado satisfactoriamente.");
            agregarButton.setVisible(true);
            modificarButton.setVisible(false);
            limpiarForm();
            listarLibros();
        }
    }

    private void eliminarLibro() {
        var fila = tablaLibros.getSelectedRow();
        if (fila != -1) {
            String idLibro = tablaLibros.getModel().getValueAt(fila, 0).toString();
            var libro = new Libro();
            libro.setIdLibro(Integer.parseInt(idLibro));
            libroServicio.eliminar(libro);
            mostrarMensaje("Libro eliminado satisfactoriamente.");
            agregarButton.setVisible(true);
            modificarButton.setVisible(false);
            limpiarForm();
            listarLibros();
        } else {
            mostrarMensaje("No se ha seleccionado ningún libro a eliminar.");
        }
    }

    private void limpiarForm() {
        libroTexto.setText("");
        autorTexto.setText("");
        precioTexto.setText("");
        existenciasTexto.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void iniciarForm() {
        modificarButton.setVisible(false);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth() / 2);
        int y = (tamanioPantalla.height - getHeight() / 2);
        setLocation(x, y);
    }

    private void createUIComponents() {
        idTexto = new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloLibros = new DefaultTableModel(0, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] cabeceras = {"Id", "Libro", "Autor", "Precio", "Existencias"};
        this.tablaModeloLibros.setColumnIdentifiers(cabeceras);
        this.tablaLibros = new JTable(tablaModeloLibros);

        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarLibros();
    }

    public void listarLibros() {
        tablaModeloLibros.setRowCount(0);
        var libros = libroServicio.listar();
        libros.forEach((libro) -> {
            Object[] filas = {
                    libro.getIdLibro(),
                    libro.getNombre(),
                    libro.getAutor(),
                    libro.getPrecio(),
                    libro.getExistencias()
            };
            this.tablaModeloLibros.addRow(filas);
        });
    }
}
