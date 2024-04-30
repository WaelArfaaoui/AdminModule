package com.talan.adminmodule;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@SpringJUnitConfig(AdminModuleApplication.class)
class AdminModuleApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}
}
