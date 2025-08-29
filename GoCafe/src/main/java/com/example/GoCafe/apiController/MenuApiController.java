package com.example.GoCafe.apiController;

import com.example.GoCafe.entity.Menu;
import com.example.GoCafe.service.MenuService;
import com.example.GoCafe.support.EntityIdUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuApiController {

    private final MenuService service;

    @GetMapping
    public List<Menu> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Menu getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody @Valid Menu body, UriComponentsBuilder uriBuilder) {
        Menu saved = service.create(body);
        Object id = EntityIdUtil.getId(saved);
        URI location = uriBuilder.path("/api/menus/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public Menu update(@PathVariable Long id, @RequestBody @Valid Menu body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
