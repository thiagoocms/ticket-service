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
            notInformedFieldsList.add("Nome");
        }
        if (Objects.isNull(entity.getDocumentNumber()) || entity.getDocumentNumber().isEmpty()) {
            notInformedFieldsList.add("N° do documento");
        }
        if (Objects.isNull(entity.getDocumentType())) {
            notInformedFieldsList.add("Tipo do documento");
        }
        if (Objects.isNull(entity.getPassword()) || entity.getPassword().isEmpty()) {
            notInformedFieldsList.add("Senha");
        }
        if (Objects.isNull(entity.getLogin()) || entity.getLogin().isEmpty()) {
            notInformedFieldsList.add("Login");
        }
        if (Objects.isNull(entity.getProfile())) {
            notInformedFieldsList.add("Profile");
        }

        if (!notInformedFieldsList.isEmpty()) {
            throw throwsException("Campos obrigatórios não informados: " + String.join(", ", notInformedFieldsList));
        }

    }

    public User checkUpdateConsistence(Long id, User toUpdateEntity) throws Throwable {

        if (Objects.isNull(id) || Objects.isNull(toUpdateEntity.getId())) {
            throw throwsException("400");
        }

        if (id.compareTo(toUpdateEntity.getId()) != 0) {
            throw throwsException("400");
        }
        User persistedEntity = checkExistUser(id);

        // Valores que não podem ser Alterados
        toUpdateEntity.setCreatedBy(persistedEntity.getCreatedBy());
        toUpdateEntity.setCreatedDate(persistedEntity.getCreatedDate());

        modelMapper.map(toUpdateEntity, persistedEntity);

        return persistedEntity;
    }

    public User checkExistUser(Long id) throws Throwable {

        User user = userRepository.findFirstById(id);
        if (Objects.isNull(user)) {
            throw throwsException("Usuário não encontrado.");
        }

        return user;
    }
}