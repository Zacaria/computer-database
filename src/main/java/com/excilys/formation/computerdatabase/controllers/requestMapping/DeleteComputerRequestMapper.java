package com.excilys.formation.computerdatabase.controllers.requestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;
import com.excilys.formation.computerdatabase.util.StringChecker;

public class DeleteComputerRequestMapper implements IRequestMapper<Object> {
  private static final String COMPUTER_IDS = "computersToDelete";

  @Override
  public IRequestDTO postToDTO(HttpServletRequest request) {
    String serialIds = request.getParameter(COMPUTER_IDS);
    
    if (StringChecker.isNullOrEmpty(serialIds)) {
      return new DeleteComputerDTO();
    }

    List<String> stringIds = Arrays.asList(serialIds.split(","));

    List<Long> ids = stringIds.stream()
        .filter(s -> StringChecker.isNumber(s))
        .map(Long::parseLong)
        .collect(Collectors.toCollection(ArrayList::new));

    return new DeleteComputerDTO(ids);
  }
}
