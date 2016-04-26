package com.excilys.formation.computerdatabase.mapper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.dataBinders.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerMapperTest {
  // private Computer empty;
  private Computer full;
  private Computer partial;

  private ComputerMapper mapper;

  @Before
  public void setUp() throws Exception {
    // this.empty = new Computer();
    this.full = Computer.builder("full").id(5l).introduced("1999-04-04").discontinued("1999-06-06")
        .company(Company.builder(7l).name("MOS Technology").build()).build();

    this.partial = Computer.builder("partial").id(8l).introduced("").discontinued("")
        .company(Company.builder(10l).name("Digital Equipment Corporation").build()).build();

    this.mapper = new ComputerMapper();
  }

  @After
  public void tearDown() throws Exception {
    // this.empty = null;
    this.full = null;
    this.partial = null;
    this.mapper = null;
  }

  @Test
  public void testMerge() {

    // assertNotEquals(this.empty, this.full);
    // assertNotEquals(this.empty, this.partial);
    // assertNotEquals(this.partial, this.full);
    //
    // mapper.merge(this.empty, this.partial);
    //
    // assertEquals(this.empty, this.partial);
    //
    // mapper.merge(this.partial, this.full);
    //
    // assertNotEquals(this.partial, this.full);
    // assertNotEquals(this.empty, this.partial);
    //
    // mapper.merge(this.empty, this.partial);
    //
    // assertEquals(this.empty, this.partial);
  }

}
