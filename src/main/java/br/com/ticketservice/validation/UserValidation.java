package br.com.ticketservice.validation;

import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.repository.UserRepository;
import br.com.ticketservice.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserValidation extends AbstractService {

    //**********************************************************************************
    // PRIVATE ATTRIBUTES
    //**********************************************************************************

    private final UserRepository userRepository;


    //**********************************************************************************
    // VALIDATIONS
    //**********************************************************************************

    public void checkOwnerFieldsToCreate(User entity) {

        entity.setId(null);
    }

    public void checkMandatoryFields(User entity) throws Throwable {

        List<String> notInformedFieldsList = new ArrayList<>();

        if (Objects.isNull(entity.getName()) || entity.getName().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("user.name"));
        }
        if (Objects.isNull(entity.getDocumentNumber()) || entity.getDocumentNumber().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("user.documentNumber"));
        }
        if (Objects.isNull(entity.getDocumentType())) {
            notInformedFieldsList.add(messageService.getMessage("user.documentType"));
        }
        if (Objects.isNull(entity.getPassword()) || entity.getPassword().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("user.password"));
        }
        if (Objects.isNull(entity.getLogin()) || entity.getLogin().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("user.login"));
        }
        if (Objects.isNull(entity.getProfile())) {
            notInformedFieldsList.add(messageService.getMessage("user.profile"));
        }

        if (!notInformedFieldsList.isEmpty()) {
            throw badRequestException("error.mandatory.fields", String.join(", ", notInformedFieldsList));
        }

    }

    public User checkUpdateConsistence(Long id, User toUpdateEntity) throws Throwable {

        if (Objects.isNull(id) || Objects.isNull(toUpdateEntity.getId())) {
            throw badRequestException("400");
        }

        if (id.compareTo(toUpdateEntity.getId()) != 0) {
            throw badRequestException("400");
        }
        User persistedEntity = checkExistUser(id);

        // Valores que não podem ser Alterados
        toUpdateEntity.setCreatedBy(persistedEntity.getCreatedBy());
        toUpdateEntity.setCreatedDate(persistedEntity.getCreatedDate());

        modelMapper.map(toUpdateEntity, persistedEntity);

        return persistedEntity;
    }

    public User checkExistUser(Long id) throws Throwable {

        User user = userRepository.findFirstByIdAndDeletedIsFalse(id);
        if (Objects.isNull(user)) {
            throw resourceNotFoundException("error.user.not.found");
        }

        return user;
    }
}