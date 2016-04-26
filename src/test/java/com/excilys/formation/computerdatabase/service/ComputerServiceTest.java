package com.excilys.formation.computerdatabase.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerServiceTest {

  private ComputerService cs;
  private Computer mock;

  @Before
  public void setUp() throws Exception {
    this.cs = ComputerService.getInstance();
    this.mock = Computer.builder("deltest").build();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Ignore
  @Test
  public void deleteTest() {
    Long newId = this.cs.create(this.mock);

    // valid.
    assertTrue(this.cs.delete(newId));
    // invalid.
    assertFalse(this.cs.delete(0l));
    // invalid.
    assertFalse(this.cs.delete(null));
  }
}
