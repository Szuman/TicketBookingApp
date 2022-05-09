package com.ticketbookingapp.domain.screening;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningDAO {
    List<Screening> findAllByDateTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Optional<Screening> findById(Long id);
}
