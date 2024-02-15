package com.soulai.api.repository;

import com.soulai.api.dto.AvailableSlot;
import com.soulai.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(nativeQuery = true)
    List<AvailableSlot> getAvailableSlots(@Param(value = "userIds") List<UUID> userIds, @Param(value = "minSecs") Long minSecs);
}