package com.tnduyen.TNDuyenHomeStay.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tnduyen.TNDuyenHomeStay.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
}
