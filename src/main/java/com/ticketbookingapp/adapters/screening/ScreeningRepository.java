package com.ticketbookingapp.adapters.screening;

import com.ticketbookingapp.domain.screening.Screening;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
interface ScreeningRepository extends PagingAndSortingRepository<Screening, Long> {
    List<Screening> findAllByStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Optional<Screening> findById(Long id);

}
