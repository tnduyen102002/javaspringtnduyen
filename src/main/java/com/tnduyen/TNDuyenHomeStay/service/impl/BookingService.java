package com.tnduyen.TNDuyenHomeStay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tnduyen.TNDuyenHomeStay.dto.BookingDTO;
import com.tnduyen.TNDuyenHomeStay.dto.Response;
import com.tnduyen.TNDuyenHomeStay.entity.Booking;
import com.tnduyen.TNDuyenHomeStay.entity.Room;
import com.tnduyen.TNDuyenHomeStay.entity.User;
import com.tnduyen.TNDuyenHomeStay.exception.OurException;
import com.tnduyen.TNDuyenHomeStay.repo.BookingRepository;
import com.tnduyen.TNDuyenHomeStay.repo.RoomRepository;
import com.tnduyen.TNDuyenHomeStay.repo.UserRepository;
import com.tnduyen.TNDuyenHomeStay.service.interfac.IBookingService;
import com.tnduyen.TNDuyenHomeStay.service.interfac.IRoomService;
import com.tnduyen.TNDuyenHomeStay.utils.Utils;

@Service
public class BookingService implements IBookingService {

	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private IRoomService roomService;
	@Autowired
	private RoomRepository roomRepository;

	public BookingService(BookingRepository bookingRepository, IRoomService roomService, RoomRepository roomRepository,
			UserRepository userRepository) {
		super();
		this.bookingRepository = bookingRepository;
		this.roomService = roomService;
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
	}

	public BookingService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookingRepository getBookingRepository() {
		return bookingRepository;
	}

	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public IRoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	public RoomRepository getRoomRepository() {
		return roomRepository;
	}

	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {

		Response response = new Response();

		try {
			if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
				throw new IllegalArgumentException("Check in date must come after check out date");
			}
			Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
			User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

			List<Booking> existingBookings = room.getBookings();

			if (!roomIsAvailable(bookingRequest, existingBookings)) {
				throw new OurException("Room not Available for selected date range");
			}

			bookingRequest.setRoom(room);
			bookingRequest.setUser(user);
			String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
			bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
			bookingRepository.save(bookingRequest);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setBookingConfirmationCode(bookingConfirmationCode);

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Saving a booking: " + e.getMessage());

		}
		return response;
	}

	@Override
	public Response findBookingByConfirmationCode(String confirmationCode) {

		Response response = new Response();

		try {
			Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode)
					.orElseThrow(() -> new OurException("Booking Not Found"));
			BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setBooking(bookingDTO);

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Finding a booking: " + e.getMessage());

		}
		return response;
	}

	@Override
	public Response getAllBookings() {

		Response response = new Response();

		try {
			List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
			List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
			response.setStatusCode(200);
			response.setMessage("successful");
			response.setBookingList(bookingDTOList);

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Getting all bookings: " + e.getMessage());

		}
		return response;
	}

	@Override
	public Response cancelBooking(Long bookingId) {

		Response response = new Response();

		try {
			bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking Does Not Exist"));
			bookingRepository.deleteById(bookingId);
			response.setStatusCode(200);
			response.setMessage("successful");

		} catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());

		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Cancelling a booking: " + e.getMessage());

		}
		return response;
	}

	private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

		return existingBookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));
	}
}
