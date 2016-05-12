package com.excilys.formation.computerdatabase.api.endpoints.dto;

import java.util.ArrayList;
import java.util.List;

public final class ResponseDTO {
  private String status;
  private List<Object> data;
  
  public ResponseDTO(String status, List<Object> data) {
    this.status = status;
    this.data = data;
  }
  
  public ResponseDTO(String status, Object data) {
    this.status = status;
    this.data = new ArrayList<>();
    this.data.add(data);
  }

  public String getStatus() {
    return status;
  }

  public List<Object> getData() {
    return data;
  }
}
