package com.excilys.formation.computerdatabase.controllers.requestMapping;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;
import com.excilys.formation.computerdatabase.controllers.requestDTO.RegisterDTO;
import com.excilys.formation.computerdatabase.model.User;

public class RegisterRequestMapper implements IRequestMapper<User> {

  @Override
  public User fromDTO(IRequestDTO requestDTO) {
    RegisterDTO dto = (RegisterDTO) requestDTO;
    return User.builder()
      .username(dto.getUsername())
      .password(new BCryptPasswordEncoder().encode(dto.getPassword()))
      .build();
  }
}
