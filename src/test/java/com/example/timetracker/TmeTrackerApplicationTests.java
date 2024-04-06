package com.example.timetracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@DataJpaTest()
@SpringBootTest
class TmeTrackerApplicationTests {

	@Test
	void contextLoads() {
		assertEquals(true, true);
	}

}
