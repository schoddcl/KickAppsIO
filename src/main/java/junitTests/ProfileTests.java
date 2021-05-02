package junitTests;


import static org.junit.jupiter.api.Assertions.*;

import org.example.Profile;
import org.junit.jupiter.api.Test;

class ProfileTests {

	@Test
	void canCreateProfileWithUsernameAndPassword() {
		Profile p = new Profile("Luke", "password", "none");
	}
	
	@Test
	void canSetUsername() {
		Profile p = new Profile("Luke", "password","none");
		p.setUsername("John");
		p.setPassword("word");
		assertEquals("John", p.getUsername());
	}
	
	@Test
	void canSetPassword() {
		Profile p = new Profile("Luke", "notPassword","none");
		p.setPassword("password");
		assertEquals("password", p.getPassword());
	}
	
//	@Test
//	void wrongPassword() {
//		Profile p = new Profile("Luke", "notPassword");
//		p.setPassword("password");
//		assertNotEquals("notPassword", p.getPassword());
//	}
}
