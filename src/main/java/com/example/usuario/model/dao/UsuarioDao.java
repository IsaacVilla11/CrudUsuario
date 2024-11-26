package com.example.usuario.model.dao;


import com.example.usuario.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Integer> {

}
