package com.example.usuario.service;


import com.example.usuario.model.entity.Usuario;

import java.util.List;

public interface IUsuario {

    Usuario save(Usuario usuario);
    Usuario findById(Integer id);
    void delete(Usuario usuario);
    List<Usuario> findAll();
}
