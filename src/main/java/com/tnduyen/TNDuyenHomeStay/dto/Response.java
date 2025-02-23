package com.tnduyen.TNDuyenHomeStay.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	private int statusCode;
	private String message;

	private String token;
	private String role;
	private String expirationTime;
	private String bookingConfirmationCode;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	private UserDTO user;

	public int getStatusCode() {
		return statusCode;
	}

	public Response(int statusCode, String message, String token, String role, String expirationTime,
			String bookingConfirmationCode, UserDTO user, RoomDTO room, BookingDTO booking, List<UserDTO> userList,
			List<RoomDTO> roomList, List<BookingDTO> bookingList) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.token = token;
		this.role = role;
		this.expirationTime = expirationTime;
		this.bookingConfirmationCode = bookingConfirmationCode;
		this.user = user;
		this.room = room;
		this.booking = booking;
		this.userList = userList;
		this.roomList = roomList;
		this.bookingList = bookingList;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}

	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public RoomDTO getRoom() {
		return room;
	}

	public void setRoom(RoomDTO room) {
		this.room = room;
	}

	public BookingDTO getBooking() {
		return booking;
	}

	public void setBooking(BookingDTO booking) {
		this.booking = booking;
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public List<RoomDTO> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomDTO> roomList) {
		this.roomList = roomList;
	}

	public List<BookingDTO> getBookingList() {
		return bookingList;
	}

	public void setBookingList(List<BookingDTO> bookingList) {
		this.bookingList = bookingList;
	}

	private RoomDTO room;
	private BookingDTO booking;
	private List<UserDTO> userList;
	private List<RoomDTO> roomList;
	private List<BookingDTO> bookingList;

}
