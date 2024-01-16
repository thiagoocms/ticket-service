package br.com.ticketservice.service.impl;

import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.dto.user.UserDTO;
import br.com.ticketservice.repository.UserRepository;
import br.com.ticketservice.service.AbstractService;
import br.com.ticketservice.service.UserService;
import br.com.ticketservice.validation.UserValidation;
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
public class UserServiceImpl extends AbstractService implements UserService {

    private final UserRepository userRepository;
    private final UserValidation userValidation;

    @Override
    public List<UserDTO> createOrUpdateByList(List<UserDTO> dtoList) throws Throwable {
        List<UserDTO> userArrayList = new ArrayList<>();
        for (UserDTO dto : dtoList) {
            if (Objects.isNull(dto.getId())) {
                userArrayList.add(this.create(dto));
            } else {
                userArrayList.add(this.update(dto.getId(), dto));
            }
        }
        return userArrayList;
    }

    @Override
    public UserDTO create(UserDTO dto) throws Throwable {

        User user = modelMapper.map(dto, User.class);
        userValidation.checkOwnerFieldsToCreate(user);
        userValidation.checkMandatoryFields(user);
        modelMapper.map(userRepository.save(user), dto);

        return dto;
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) throws Throwable {

        User user = modelMapper.map(dto, User.class);
        userValidation.checkUpdateConsistence(id, user);
        userValidation.checkMandatoryFields(user);
        modelMapper.map(userRepository.save(user), dto);

        return dto;
    }

    @Override
    public UserDTO findById(Long id) throws Throwable {

        User user = userValidation.checkExistUser(id);

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public Page<UserDTO> findByAll(Pageable pageable) {

        Page<User> page = this.userRepository.findAllByDeletedIsFalse(pageable);
        return modelMapper.map(page, new TypeToken<Page<UserDTO>>() {
        }.getType());

    }

    @Override
    public void delete(Long id) throws Throwable {

        User user = userValidation.checkExistUser(id);
        user.setDeleted(Boolean.TRUE);
        userRepository.save(user);

    }
}