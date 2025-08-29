package com.example.GoCafe.service;

import com.example.GoCafe.entity.Cafe;
import com.example.GoCafe.repository.CafeRepository;
import com.example.GoCafe.support.EntityIdUtil;
import com.example.GoCafe.support.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository repository;

    @Transactional(readOnly = true)
    public List<Cafe> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Cafe findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cafe not found: " + id));
    }

    @Transactional
    public Cafe create(Cafe entity) {
        EntityIdUtil.setId(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public Cafe update(Long id, Cafe entity) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cafe not found: " + id);
        }
        EntityIdUtil.setId(entity, id);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Cafe not found: " + id);
        }
        repository.deleteById(id);
    }
}
