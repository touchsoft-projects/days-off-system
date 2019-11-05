package by.touchsoft.office.daysoffsystem.db.service;

import by.touchsoft.office.daysoffsystem.db.repository.UserRepository;
import by.touchsoft.office.daysoffsystem.db.repository.dto.converters.UserConverter;
import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * This class interacts with database and controls an {@link UserEntity} entity by {@link UserRepository} interface.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(final UserRepository userRepository, final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDto> getAll() {

        return userConverter.convertToDto(userRepository.findAll());
    }

    public UserDto getById(final int id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(userConverter::convertToDto).orElse(null);
    }

    public void deleteById(final int id) {
        userRepository.deleteById(id);
    }

    public void save(final UserDto userDto) {
        userRepository.save(userConverter.convertToEntity(userDto));
    }

}
