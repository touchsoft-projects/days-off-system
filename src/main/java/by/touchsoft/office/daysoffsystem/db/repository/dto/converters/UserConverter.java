package by.touchsoft.office.daysoffsystem.db.repository.dto.converters;

import by.touchsoft.office.daysoffsystem.db.repository.dto.dtoEntity.UserDto;
import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class converts {@link UserEntity} into {@link UserDto} or vice versa to work with a view.
 */
@Component
public class UserConverter implements DtoEntityConverter<UserDto, UserEntity> {

    private PeriodConverter periodConverter;

    @Autowired
    private void init(PeriodConverter periodConverter) {
        this.periodConverter = periodConverter;
    }

    /**
     * This method converts {@link UserEntity} into {@link UserDto}.
     *
     * @param userEntity - source {@link UserEntity} entity object.
     * @return converted {@link UserDto} object.
     */
    @Override
    public UserDto convertToDto(final UserEntity userEntity) {
        final UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setSecondName(userEntity.getSecondName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setPassportId(userEntity.getPassportId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPeriods(periodConverter.convertToDto(userEntity.getPeriodEntities()));

        return userDto;
    }

    /**
     * This method converts {@link UserDto} into {@link UserEntity}.
     *
     * @param userDto - source {@link UserDto}.
     * @return converted {@link UserEntity} entity.
     */
    @Override
    public UserEntity convertToEntity(final UserDto userDto) {
        final UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setSecondName(userDto.getSecondName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setPassportId(userDto.getPassportId());
        userEntity.setEmail(userDto.getEmail());

        return userEntity;
    }

}
