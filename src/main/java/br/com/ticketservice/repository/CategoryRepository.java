package br.com.ticketservice.repository;

import br.com.ticketservice.domain.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findFirstByIdAndDeletedIsFalse(Long id);

    Page<Category> findAllByDeletedIsFalse(Pageable pageable);
}
