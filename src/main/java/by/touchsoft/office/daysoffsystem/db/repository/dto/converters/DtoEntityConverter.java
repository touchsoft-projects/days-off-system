package by.touchsoft.office.daysoffsystem.db.repository.dto.converters;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Interface converts entity into transfer objects (can be Set/List of them) or vice versa to work with a view.
 *
 * @param <T> - Data Transfer Object.
 * @param <B> - Entity Object.
 */
public abstract class DtoEntityConverter<T, B> {

    /**
     * This method converts entity into data transfer object.
     *
     * @param dbo - source entity.
     * @return converted data transfer object.
     */
    abstract T convertToDto(final B dbo);

    /**
     * This method converts data transfer object into entity.
     *
     * @param dto - source data transfer object.
     * @return converted entity.
     */
    abstract B convertToEntity(final T dto);

    /**
     * This method converts Set of entities into Set of data transfer objects.
     *
     * @param entitySet - source Set of entities.
     * @return converted Set of data transfer objects.
     */
    public Set<T> convertToDto(final Set<B> entitySet) {
        if (entitySet != null) {
            final Set<T> dtoSet = new HashSet<>();
            for (final B entity : entitySet) {
                final T dto = convertToDto(entity);
                dtoSet.add(dto);
            }
            return dtoSet;
        }
        return null;
    }

    /**
     * This method converts Set of data transfer objects into Set of entities.
     *
     * @param dtoSet - source Set of data transfer objects.
     * @return converted Set of entities.
     */
    public Set<B> convertToEntity(final Set<T> dtoSet) {
        if (dtoSet != null) {
            final Set<B> entitySet = new HashSet<>();
            for (final T dto : dtoSet) {
                final B entity = convertToEntity(dto);
                entitySet.add(entity);
            }
            return entitySet;
        }
        return null;
    }

    /**
     * This method converts List of entities into List of data transfer objects.
     *
     * @param entityList - source List of entities.
     * @return converted List of data transfer objects.
     */
    public List<T> convertToDto(final List<B> entityList) {
        if (entityList != null) {
            final List<T> dtoList = new LinkedList<>();
            for (final B entity : entityList) {
                final T dto = convertToDto(entity);
                dtoList.add(dto);
            }
            return dtoList;
        }
        return null;
    }

    /**
     * This method converts List of data transfer objects into List of entities.
     *
     * @param dtoList - source List of data transfer objects.
     * @return converted List of entities.
     */
    public List<B> convertToEntity(final List<T> dtoList) {
        if (dtoList != null) {
            final List<B> entityList = new LinkedList<>();
            for (final T dto : dtoList) {
                final B entity = convertToEntity(dto);
                entityList.add(entity);
            }
            return entityList;
        }
        return null;
    }

}
