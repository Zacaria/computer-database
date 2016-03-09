package com.excilys.formation.computerdatabase.mapper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerMapperTest {
	private Computer empty;
	private Computer full;
	private Computer partial;
	
	private ComputerMapper mapper;

	@Before
	public void setUp() throws Exception {
		this.empty = new Computer();
		this.full = new Computer(5l, "full", "1999-04-04", "1999-06-06", 7l);
		this.partial = new Computer(8l, "partial", "", "", 10l);
		
		this.mapper = new ComputerMapper();
	}

	@After
	public void tearDown() throws Exception {
		this.empty = null;
		this.full = null;
		this.partial = null;
		this.mapper = null;
	}

	@Test
	public void testMerge() {
		
		assertNotEquals(this.empty, this.full);
		assertNotEquals(this.empty, this.partial);
		assertNotEquals(this.partial, this.full);
		
		mapper.merge(this.empty, this.partial);
		
		assertEquals(this.empty, this.partial);
		
		mapper.merge(this.partial, this.full);
		
		assertNotEquals(this.partial, this.full);
		assertNotEquals(this.empty, this.partial);
		
		mapper.merge(this.empty, this.partial);
		
		assertEquals(this.empty, this.partial);
	}

}
