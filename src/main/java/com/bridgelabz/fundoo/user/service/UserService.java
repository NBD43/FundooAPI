package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.EmailDTO;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
@Service
public interface UserService {
	Response onRegister(UserDTO userDto) throws UserException, UnsupportedEncodingException;

	ResponseToken onLogin(LoginDTO loginDto) throws UserException, UnsupportedEncodingException;

	/**
	 * to verify valid emailId
	 */
	Response validateEmailId(String token) throws UserException;
	
	Response addProfile(String token,String path) throws UserException;
	
	String getProfile(String token) throws UserException;
	
	public Response uploadProfilePic(String token,MultipartFile picture);
	public Resource getProfilePic(String token);


	/**
	 * to send forget password link
	 */
	Response forgetPassword(EmailDTO emailDto) throws UserException, UnsupportedEncodingException;

	/**
	 * use to reset already register user password
	 * 
	 */
	public Response resetPaswords(String token, String password) throws UserException;

	ResponseToken authentication(Optional<User> user, String password)
			throws UnsupportedEncodingException, UserException;

}
