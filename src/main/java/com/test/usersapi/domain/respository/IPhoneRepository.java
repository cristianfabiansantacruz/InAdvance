package com.test.usersapi.domain.respository;

import com.test.usersapi.domain.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhoneRepository extends JpaRepository<PhoneEntity, Long> {
}
