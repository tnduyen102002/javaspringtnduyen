package com.tnduyen.TNDuyenHomeStay.service.interfac;

import com.tnduyen.TNDuyenHomeStay.dto.LoginRequest;
import com.tnduyen.TNDuyenHomeStay.dto.Response;
import com.tnduyen.TNDuyenHomeStay.entity.User;

public interface IUserService {
	Response register(User user);

	Response login(LoginRequest loginRequest);

	Response getAllUsers();

	Response getUserBookingHistory(String userId);

	Response deleteUser(String userId);

	Response getUserById(String userId);

	Response getMyInfo(String email);

}
