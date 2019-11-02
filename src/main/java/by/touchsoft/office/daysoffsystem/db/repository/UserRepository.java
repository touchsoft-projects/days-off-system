package by.touchsoft.office.daysoffsystem.db.repository;

import by.touchsoft.office.daysoffsystem.db.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
