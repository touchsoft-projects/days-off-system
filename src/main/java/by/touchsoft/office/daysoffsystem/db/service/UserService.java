package by.touchsoft.office.daysoffsystem.db.service;

import by.touchsoft.office.daysoffsystem.db.repository.UserRepository;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.dto.UserPasswordDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class interacts with database and controls an {@link UserEntity} entity
 * by {@link UserRepository} interface.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    public void addUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        copyToEntity(userDto, userEntity);
        userEntity.setPassword(encodedPassword);

        userRepository.save(userEntity);
    }

    public UserDto getUserById(final String id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        UserDto userDto = null;
        if (optional.isPresent()) {
            userDto = toDto(optional.get());
        }
        return userDto;
    }

    public UserDto getUserByEmail(final String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        UserDto userDto = null;
        if (userEntity != null) {
            userDto = toDto(userEntity);
        }
        return userDto;
    }

    public List<UserDto> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> userDtos = ImmutableList.of();
        if (!userEntities.isEmpty()) {
            userDtos = new ArrayList<>();
            for (UserEntity userEntity : userEntities) {
                userDtos.add(toDto(userEntity));
            }
        }
        return userDtos;
    }

    public void updateUser(UserDto userDto) {
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());
        copyToEntity(userDto, userEntity);
    }
    
    public void deleteById(final String id) {
        userRepository.deleteById(id);
    }
    
    public String getIdByEmail(String email) {
    	UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
        	return userEntity.getId();
        }
        return null;
    }

    public void deleteById(final String id) {
        userRepository.deleteById(id);
    }

    public boolean updateUserPassword(UserPasswordDto userPasswordDto) {
        UserEntity userEntity = userRepository.findByEmail(userPasswordDto.getEmail());
        if (userEntity != null) {
            String encodedPassword = passwordEncoder.encode(userPasswordDto.getPassword());
            userEntity.setPassword(encodedPassword);
            return true;
        }
        return false;
    }

    private void copyToEntity(UserDto userDto, UserEntity userEntity) {
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setSecondName(userDto.getSecondName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEnabled(userDto.isEnabled());
        userEntity.setPassportId(userDto.getPassportId());
        userEntity.setRoles(userDto.getRoles());
		userEntity.setPassword(userDto.getPassword());
    }

    private UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setSecondName(userEntity.getSecondName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEnabled(userEntity.isEnabled());
        userDto.setPassportId(userEntity.getPassportId());
        userDto.setRoles(userEntity.getRoles());
		userDto.setPassword(userEntity.getPassword());
        return userDto;
    }
}
