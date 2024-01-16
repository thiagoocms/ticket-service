package br.com.ticketservice.validation;

import br.com.ticketservice.domain.category.Category;
import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.enumerated.UserProfileEnum;
import br.com.ticketservice.repository.CategoryRepository;
import br.com.ticketservice.repository.UserRepository;
import br.com.ticketservice.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryValidation extends AbstractService {

    //**********************************************************************************
    // PRIVATE ATTRIBUTES
    //**********************************************************************************

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    //**********************************************************************************
    // VALIDATIONS
    //**********************************************************************************

    public void checkOwnerFieldsToCreate(Category entity) {

        entity.setId(null);
    }

    public void checkMandatoryFields(Category entity) throws Throwable {

        List<String> notInformedFieldsList = new ArrayList<>();

        if (Objects.isNull(entity.getName()) || entity.getName().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("category.name"));
        }
        if (Objects.isNull(entity.getPriority())) {
            notInformedFieldsList.add(messageService.getMessage("category.priority"));
        }
        if (Objects.isNull(entity.getUser()) || Objects.isNull(entity.getUser().getId())) {
            notInformedFieldsList.add(messageService.getMessage("category.user"));
        }

        if (!notInformedFieldsList.isEmpty()) {
            throw badRequestException("error.mandatory.fields", String.join(", ", notInformedFieldsList));
        }

    }

    public Category checkUpdateConsistence(Long id, Category toUpdateEntity) throws Throwable {

        if (Objects.isNull(id) || Objects.isNull(toUpdateEntity.getId())) {
            throw badRequestException("400");
        }

        if (id.compareTo(toUpdateEntity.getId()) != 0) {
            throw badRequestException("400");
        }
        Category persistedEntity = checkExistCategory(id);

        // Valores que não podem ser Alterados
        toUpdateEntity.setCreatedBy(persistedEntity.getCreatedBy());
        toUpdateEntity.setCreatedDate(persistedEntity.getCreatedDate());

        modelMapper.map(toUpdateEntity, persistedEntity);

        return persistedEntity;
    }

    public Category checkRelations(Category toPersistEntity) throws Throwable {

        this.checkUser(toPersistEntity);
        return toPersistEntity;
    }

    public Category checkExistCategory(Long id) throws Throwable {

        Category category = categoryRepository.findFirstByIdAndDeletedIsFalse(id);
        if (Objects.isNull(category)) {
            throw resourceNotFoundException("error.category.not.found");
        }

        return category;
    }

    private Category checkUser(Category entity) throws Throwable {

        if (Objects.isNull(entity.getUser())) {
            return entity;
        }

        if (Objects.isNull(entity.getUser().getId())) {
            entity.setUser(null);
            return entity;
        }

        User user = this.userRepository.findFirstByIdAndDeletedIsFalse(entity.getUser().getId());

        if (user == null) {
            throw resourceNotFoundException("error.user.not.found");
        }

        var profilesPermission = List.of(UserProfileEnum.EMPLOYEE, UserProfileEnum.ADMIM);
        if (!profilesPermission.contains(user.getProfile())) {
            throw badRequestException("error.user.not.permission");
        }

        entity.setUser(user);

        return entity;
    }
}