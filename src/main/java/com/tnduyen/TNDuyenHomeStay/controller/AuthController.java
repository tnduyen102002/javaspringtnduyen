package com.tnduyen.TNDuyenHomeStay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnduyen.TNDuyenHomeStay.dto.LoginRequest;
import com.tnduyen.TNDuyenHomeStay.dto.Response;
import com.tnduyen.TNDuyenHomeStay.entity.User;
import com.tnduyen.TNDuyenHomeStay.service.interfac.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserService userService;

	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody User user) {
		Response response = userService.register(user);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
		Response response = userService.login(loginRequest);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
