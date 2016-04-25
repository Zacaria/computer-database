package com.excilys.formation.computerdatabase.servlets.requestDTO;

import java.util.List;

public class DeleteComputerDTO implements IRequestDTO{
  private List<Long> ids;
  
  public DeleteComputerDTO(){
    
  }
  
  public DeleteComputerDTO(List<Long> ids){
    this.ids = ids;
  }

  public List<Long> getIds() {
    return ids;
  }
}
