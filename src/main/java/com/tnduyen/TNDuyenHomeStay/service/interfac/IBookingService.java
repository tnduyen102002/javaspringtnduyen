package com.tnduyen.TNDuyenHomeStay.service.interfac;

import com.tnduyen.TNDuyenHomeStay.dto.Response;
import com.tnduyen.TNDuyenHomeStay.entity.Booking;

public interface IBookingService {

	Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

	Response findBookingByConfirmationCode(String confirmationCode);

	Response getAllBookings();

	Response cancelBooking(Long bookingId);

}
