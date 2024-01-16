package br.com.ticketservice.rest;

import br.com.ticketservice.constants.AppConstants;
import br.com.ticketservice.dto.category.CategorySimpleDTO;
import br.com.ticketservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AppConstants.PATH + AppConstants.API + AppConstants.V1 + "/categories")
@RequiredArgsConstructor
public class CategoryResource {

    private final CategoryService categoryService;

    @PostMapping("/by-list")
    public ResponseEntity<List<CategorySimpleDTO>> createOrUpdateByList(@RequestBody List<CategorySimpleDTO> dtoList) throws Throwable {

        List<CategorySimpleDTO> list = categoryService.createOrUpdateByList(dtoList);

        return ResponseEntity
                .ok()
                .body(list);
    }

    @PostMapping
    public ResponseEntity<CategorySimpleDTO> create(@RequestBody CategorySimpleDTO dto) throws Throwable {

        dto = categoryService.create(dto);

        return ResponseEntity
                .created(URI.create("/categories"))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorySimpleDTO> update(@PathVariable Long id, @RequestBody CategorySimpleDTO dto) throws Throwable {

        dto = categoryService.update(id, dto);

        return ResponseEntity
                .ok()
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<CategorySimpleDTO>> findByAll(Pageable pageable) {

        Page<CategorySimpleDTO> page = categoryService.findByAll(pageable);

        return ResponseEntity
                .ok()
                .body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorySimpleDTO> findById(@PathVariable Long id) throws Throwable {

        CategorySimpleDTO userDTO = categoryService.findById(id);

        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Throwable {

        categoryService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}