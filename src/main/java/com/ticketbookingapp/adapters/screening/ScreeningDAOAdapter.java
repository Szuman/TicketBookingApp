package com.ticketbookingapp.adapters.screening;

import com.ticketbookingapp.domain.screening.Screening;
import com.ticketbookingapp.domain.screening.ScreeningDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ScreeningDAOAdapter implements ScreeningDAO {
    private final ScreeningRepository screeningRepository;

    @Override
    public List<Screening> findAllByDateTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return screeningRepository.findAllByStartTimeBetween(from, to, pageable);
    }

    @Override
    public Optional<Screening> findById(Long id) {
        return screeningRepository.findById(id);
    }
}
