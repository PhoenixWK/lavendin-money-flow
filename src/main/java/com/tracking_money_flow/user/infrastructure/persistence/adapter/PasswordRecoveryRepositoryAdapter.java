package com.tracking_money_flow.user.infrastructure.persistence.adapter;

import com.tracking_money_flow.user.application.port.PasswordRecoveryRepository;
import com.tracking_money_flow.user.domain.PasswordRecovery;
import com.tracking_money_flow.user.domain.exception.InvalidPasswordRecoveryIdException;
import com.tracking_money_flow.user.infrastructure.persistence.jpa.PasswordRecoveryJpaEntity;
import com.tracking_money_flow.user.infrastructure.persistence.mapper.PasswordRecoveryMapper;
import com.tracking_money_flow.user.infrastructure.persistence.repository.PasswordRecoveryJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Transactional
public class PasswordRecoveryRepositoryAdapter implements PasswordRecoveryRepository {

    private final PasswordRecoveryJpaRepository repo;

    public PasswordRecoveryRepositoryAdapter(PasswordRecoveryJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<PasswordRecovery> findById(String id) {
        PasswordRecoveryJpaEntity entity = repo.findById(id)
                .orElseThrow(() -> new InvalidPasswordRecoveryIdException("Invalid password recovery"));
        return Optional.of(PasswordRecoveryMapper.toDomain(entity));
    }

    @Override
    public void save(PasswordRecovery passwordRecovery) {
        repo.save(PasswordRecoveryMapper.toEntity(passwordRecovery));
    }

    @Override
    public void updateExpiredStatus(String token) {
        PasswordRecovery pr = findById(token)
                .orElseThrow(() -> new InvalidPasswordRecoveryIdException("Invalid password recovery code"));

        pr.setIsExpired(false);
        pr.setExpiredAt(Timestamp.valueOf(LocalDateTime.now()));

        save(pr);
    }
}
