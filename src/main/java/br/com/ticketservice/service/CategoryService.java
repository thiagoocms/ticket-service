package br.com.ticketservice.service;

import br.com.ticketservice.dto.category.CategorySimpleDTO;
import br.com.ticketservice.enumerated.CategoryPriorityEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<CategorySimpleDTO> createOrUpdateByList(List<CategorySimpleDTO> dtoList) throws Throwable;

    CategorySimpleDTO create(CategorySimpleDTO dto) throws Throwable;

    CategorySimpleDTO update(Long id, CategorySimpleDTO dto) throws Throwable;

    CategorySimpleDTO findById(Long id) throws Throwable;

    Page<CategorySimpleDTO> findByAll(Pageable pageable);

    void delete(Long id) throws Throwable;

    List<CategoryPriorityEnum> findPriority();
}