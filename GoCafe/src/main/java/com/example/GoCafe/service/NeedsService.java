package com.example.GoCafe.service;

import com.example.GoCafe.entity.Needs;
import com.example.GoCafe.repository.NeedsRepository;
import com.example.GoCafe.support.EntityIdUtil;
import com.example.GoCafe.support.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NeedsService {

    private final NeedsRepository repository;

    @Transactional(readOnly = true)
    public List<Needs> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Needs findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Needs not found: " + id));
    }

    @Transactional
    public Needs create(Needs entity) {
        EntityIdUtil.setId(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public Needs update(Long id, Needs entity) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Needs not found: " + id);
        }
        EntityIdUtil.setId(entity, id);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Needs not found: " + id);
        }
        repository.deleteById(id);
    }
}
