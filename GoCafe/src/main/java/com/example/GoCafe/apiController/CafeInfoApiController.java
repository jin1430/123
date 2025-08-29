package com.example.GoCafe.apiController;

import com.example.GoCafe.entity.CafeInfo;
import com.example.GoCafe.service.CafeInfoService;
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
@RequestMapping("/api/cafe-infos")
@RequiredArgsConstructor
public class CafeInfoApiController {

    private final CafeInfoService service;

    @GetMapping
    public List<CafeInfo> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CafeInfo getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<CafeInfo> create(@RequestBody @Valid CafeInfo body, UriComponentsBuilder uriBuilder) {
        CafeInfo saved = service.create(body);
        Object id = EntityIdUtil.getId(saved);
        URI location = uriBuilder.path("/api/cafe-infos/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public CafeInfo update(@PathVariable Long id, @RequestBody @Valid CafeInfo body) {
        return service.update(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
