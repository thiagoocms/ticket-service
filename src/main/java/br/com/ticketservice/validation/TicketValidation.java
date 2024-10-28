package br.com.ticketservice.validation;

import br.com.ticketservice.domain.category.Category;
import br.com.ticketservice.domain.ticket.Ticket;
import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.enumerated.TicketStatusEnum;
import br.com.ticketservice.enumerated.UserProfileEnum;
import br.com.ticketservice.repository.CategoryRepository;
import br.com.ticketservice.repository.TicketRepository;
import br.com.ticketservice.repository.UserRepository;
import br.com.ticketservice.service.AbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketValidation extends AbstractService {

    //**********************************************************************************
    // PRIVATE ATTRIBUTES
    //**********************************************************************************

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TicketRepository ticketRepository;

    //**********************************************************************************
    // VALIDATIONS
    //**********************************************************************************

    public void checkOwnerFieldsToCreate(Ticket entity) {

        entity.setId(null);
        entity.setStatus(TicketStatusEnum.NEW);
    }

    public void checkMandatoryFields(Ticket entity) throws Throwable {

        List<String> notInformedFieldsList = new ArrayList<>();

        if (Objects.isNull(entity.getTitle()) || entity.getTitle().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("ticket.title"));
        }
        if (Objects.isNull(entity.getDescription()) || entity.getDescription().isEmpty()) {
            notInformedFieldsList.add(messageService.getMessage("ticket.description"));
        }
        if (Objects.isNull(entity.getUser()) || Objects.isNull(entity.getUser().getId())) {
            notInformedFieldsList.add(messageService.getMessage("ticket.user"));
        }
        if (Objects.isNull(entity.getCategory()) || Objects.isNull(entity.getCategory().getId())) {
            notInformedFieldsList.add(messageService.getMessage("ticket.category"));
        }

        if (!notInformedFieldsList.isEmpty()) {
            throw badRequestException("error.mandatory.fields", String.join(", ", notInformedFieldsList));
        }

    }

    public Ticket checkUpdateConsistence(Long id, Ticket toUpdateEntity) throws Throwable {

        if (Objects.isNull(id) || Objects.isNull(toUpdateEntity.getId())) {
            throw badRequestException("400");
        }

        if (id.compareTo(toUpdateEntity.getId()) != 0) {
            throw badRequestException("400");
        }
        Ticket persistedEntity = checkExistTicket(id);

        // Valores que não podem ser Alterados
        toUpdateEntity.setCreatedBy(persistedEntity.getCreatedBy());
        toUpdateEntity.setCreatedDate(persistedEntity.getCreatedDate());

        modelMapper.map(toUpdateEntity, persistedEntity);

        return persistedEntity;
    }

    public Ticket checkRelations(Ticket toPersistEntity) throws Throwable {

        this.checkUser(toPersistEntity);
        this.checkCategory(toPersistEntity);
        this.checkEmployee(toPersistEntity);
        return toPersistEntity;
    }

    public Ticket checkExistTicket(Long id) throws Throwable {

        Ticket ticket = ticketRepository.findFirstByIdAndDeletedIsFalse(id);
        if (Objects.isNull(ticket)) {
            throw resourceNotFoundException("error.ticket.not.found");
        }

        return ticket;
    }

    private Ticket checkUser(Ticket entity) throws Throwable {

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

        entity.setUser(user);

        return entity;
    }

    private Ticket checkEmployee(Ticket entity) throws Throwable {

        if (Objects.isNull(entity.getEmployee())) {
            return entity;
        }

        if (Objects.isNull(entity.getEmployee().getId())) {
            entity.setUser(null);
            return entity;
        }

        User employee = this.userRepository.findFirstByIdAndDeletedIsFalse(entity.getEmployee().getId());

        if (employee == null) {
            throw resourceNotFoundException("error.user.not.found");
        }

        var profilesPermission = List.of(UserProfileEnum.EMPLOYEE, UserProfileEnum.ADMIN);
        if (!profilesPermission.contains(employee.getProfile())) {
            throw badRequestException("error.user.not.permission");
        }

        entity.setEmployee(employee);

        return entity;
    }

    private Ticket checkCategory(Ticket entity) throws Throwable {

        if (Objects.isNull(entity.getCategory())) {
            return entity;
        }

        if (Objects.isNull(entity.getCategory().getId())) {
            entity.setCategory(null);
            return entity;
        }

        Category category = this.categoryRepository.findFirstByIdAndDeletedIsFalse(entity.getCategory().getId());

        if (category == null) {
            throw resourceNotFoundException("error.category.not.found");
        }
        var day = switch (category.getPriority()) {
            case URGENCY -> 1;
            case NORMAL -> 3;
            default -> 2;
        };
        entity.setWaitingDate(LocalDateTime.now().plusDays(day));
        entity.setCategory(category);

        return entity;
    }
}