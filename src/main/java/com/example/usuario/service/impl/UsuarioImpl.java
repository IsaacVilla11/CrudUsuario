package com.example.usuario.service.impl;

import com.example.usuario.model.dao.UsuarioDao;
import com.example.usuario.model.entity.Usuario;
import com.example.usuario.service.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioImpl implements IUsuario {

    @Autowired
    private UsuarioDao usuarioDao;

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findById(Integer id) {
        return usuarioDao.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
    }

    // Método para eliminar un usuario
    @Transactional
    @Override
    public void delete(Usuario usuario) {
        // Verifica si el usuario existe antes de eliminarlo
        Usuario usuarioExistente = usuarioDao.findById(usuario.getId()).orElse(null);
        if (usuarioExistente != null) {
            usuarioDao.delete(usuario); // Elimina el usuario si existe
        } else {
            throw new RuntimeException("No se encontró el usuario con ID: " + usuario.getId());
        }
    }

    // Método para actualizar parcialmente los campos de un usuario (solo los campos modificados)
    @Transactional
    public Usuario updatePartial(Integer id, String email, String nombre, String clave, String estado) {
        // Busca al usuario por ID
        Usuario usuarioExistente = usuarioDao.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));

        // Actualiza solo los campos que no sean null
        if (email != null) {
            usuarioExistente.setEmail(email);
        }
        if (nombre != null) {
            usuarioExistente.setNombre(nombre);
        }
        if (clave != null) {
            usuarioExistente.setClave(clave);
        }
        if (estado != null) {
            usuarioExistente.setEstado(estado);
        }

        // Guarda el usuario actualizado
        return usuarioDao.save(usuarioExistente);
    }

    // Método para obtener todos los usuarios
    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAll() {
        // Devuelve todos los usuarios como List
        return (List<Usuario>) usuarioDao.findAll();
    }

}
