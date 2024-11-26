package com.example.usuario.controller;


import com.example.usuario.model.entity.Usuario;
import com.example.usuario.service.IUsuario;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Usuarios", description = "Operaciones de gestión de usuarios")
public class UsuarioController {

    @Autowired
    private IUsuario usuarioService;

    @PostMapping( "usuario")
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con la información proporcionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("usuario/{id}")
    @Operation(summary = "Actualizar un usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuarioUpdateDTO) {
        // Verifica si el usuario existe
        Usuario usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve un 404
        }

        // Actualiza solo los campos que vienen en el DTO
        if (usuarioUpdateDTO.getEmail() != null) {
            usuarioExistente.setEmail(usuarioUpdateDTO.getEmail());
        }
        if (usuarioUpdateDTO.getNombre() != null) {
            usuarioExistente.setNombre(usuarioUpdateDTO.getNombre());
        }
        if (usuarioUpdateDTO.getClave() != null) {
            usuarioExistente.setClave(usuarioUpdateDTO.getClave());
        }
        if (usuarioUpdateDTO.getEstado() != null) {
            usuarioExistente.setEstado(usuarioUpdateDTO.getEstado());
        }

        // Guarda el usuario actualizado
        Usuario usuarioActualizado = usuarioService.save(usuarioExistente);

        return ResponseEntity.ok(usuarioActualizado); // Devuelve el usuario actualizado
    }



    // Método para eliminar un usuario
    @DeleteMapping("usuario/{id}")
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public void delete(@PathVariable Integer id) {
        Usuario usuarioDelete = usuarioService.findById(id);
        if (usuarioDelete != null) {
            usuarioService.delete(usuarioDelete);  // Elimina el usuario
        }
    }

    @GetMapping("usuario/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene los detalles de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> showById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("usuario")
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios")
    })
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();  // Devuelve 204 si no hay usuarios
        }
        return ResponseEntity.ok(usuarios);  // Devuelve la lista de usuarios
    }

}
