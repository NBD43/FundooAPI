package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepo;
import com.bridgelabz.fundoo.utility.ResponseHelper;
import com.bridgelabz.fundoo.utility.TokenGenerator;
import com.bridgelabz.fundoo.utility.Utility;




@PropertySource("classpath:message.properties")
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenGenerator tokenUtil;

	@Autowired
	private Response statusResponse;

	@Autowired
	private Environment environment;


	@Override
	public Response onRegister(UserDTO userDto) {

		String emailid = userDto.getEmailId();

		User user = modelMapper.map(userDto, User.class);
		Optional<User> alreadyPresent = userRepo.findByEmailId(user.getEmailId());
		if (alreadyPresent.isPresent()) {
			throw new UserException(environment.getProperty("status.register.emailExistError"));
		}
		// encode user password
		String password = passwordEncoder.encode(userDto.getPassword());

		user.setPassword(password);
		user = userRepo.save(user);
		Long userId = user.getUserId();
		// System.out.println(emailid + " " + userId);
		Utility.send(emailid, "confirmation mail", Utility.getUrl(userId) + "/valid");

		statusResponse = ResponseHelper.statusResponse(200, "register successfully");
		return statusResponse;

	}

	
	
	public ResponseToken onLogin(LoginDTO loginDto) {
		// extract user details by using emailid
		Optional<User> user = userRepo.findByEmailId(loginDto.getEmailId());
		System.out.println(user);
		ResponseToken response = new ResponseToken();
		if (user.isPresent()) {
			System.out.println("password..." + (loginDto.getPassword()));
			
		return authentication(user, loginDto.getPassword());

		}
		
		return response;

	}
	
	
	
	
	@Override
	public ResponseToken authentication(Optional<User> user, String password) {

		ResponseToken response = new ResponseToken();
		if (user.get().isVerify()) {
			boolean status = passwordEncoder.matches(password, user.get().getPassword());
			if (status == true) {
				System.out.println("logged in");
				String token = tokenUtil.createToken(user.get().getUserId());
				response.setToken(token);
				response.setStatusCode(200);
				response.setStatusMessage(environment.getProperty("user.login"));
				return response;
			}
			throw new UserException(401, environment.getProperty("user.login.password"));
		}
		throw new UserException(401, environment.getProperty("user.login.verification"));
	}

	@Override
	public Response validateEmailId(String token) {
		Long id = tokenUtil.decodeToken(token);
		User user = userRepo.findById(id)
				.orElseThrow(() -> new UserException(404, environment.getProperty("user.validation.email")));
		user.setVerify(true);
		userRepo.save(user);
		statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("user.validation"));
		return statusResponse;
	}



	@Override
	public Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Response resetPaswords(String token, String password) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
