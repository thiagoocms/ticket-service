package br.com.ticketservice.service.impl;

import br.com.ticketservice.domain.category.Category;
import br.com.ticketservice.dto.category.CategorySimpleDTO;
import br.com.ticketservice.repository.CategoryRepository;
import br.com.ticketservice.service.AbstractService;


import br.com.ticketservice.service.CategoryService;
import br.com.ticketservice.validation.CategoryValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidation categoryValidation;

    @Override
    public List<CategorySimpleDTO> createOrUpdateByList(List<CategorySimpleDTO> dtoList) throws Throwable {
        List<CategorySimpleDTO> userArrayList = new ArrayList<>();
        for (CategorySimpleDTO dto : dtoList) {
            if (Objects.isNull(dto.getId())) {
                userArrayList.add(this.create(dto));
            } else {
                userArrayList.add(this.update(dto.getId(), dto));
            }
        }
        return userArrayList;
    }

    @Override
    public CategorySimpleDTO create(CategorySimpleDTO dto) throws Throwable {

        Category entity = modelMapper.map(dto, Category.class);
        categoryValidation.checkOwnerFieldsToCreate(entity);
        categoryValidation.checkMandatoryFields(entity);
        categoryValidation.checkRelations(entity);
        modelMapper.map(categoryRepository.save(entity), dto);

        return dto;
    }

    @Override
    public CategorySimpleDTO update(Long id, CategorySimpleDTO dto) throws Throwable {

        Category entity = modelMapper.map(dto, Category.class);
        categoryValidation.checkUpdateConsistence(id, entity);
        categoryValidation.checkMandatoryFields(entity);
        categoryValidation.checkRelations(entity);
        modelMapper.map(categoryRepository.save(entity), dto);

        return dto;
    }

    @Override
    public CategorySimpleDTO findById(Long id) throws Throwable {

        Category entity = categoryValidation.checkExistCategory(id);

        return modelMapper.map(entity, CategorySimpleDTO.class);
    }

    @Override
    public Page<CategorySimpleDTO> findByAll(Pageable pageable) {

        Page<Category> page = this.categoryRepository.findAllByDeletedIsFalse(pageable);
        return page.map(item -> modelMapper.map(item, CategorySimpleDTO.class));

    }

    @Override
    public void delete(Long id) throws Throwable {

        Category entity = categoryValidation.checkExistCategory(id);
        entity.setDeleted(Boolean.TRUE);
        categoryRepository.save(entity);

    }
}