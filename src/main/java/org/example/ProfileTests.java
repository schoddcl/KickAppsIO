

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProfileTests {

	@Test
	void canCreateProfileWithUsernameAndPassword() {
		Profile p = new Profile("Luke", "password");
	}
	
	@Test
	void canSetUsername() {
		Profile p = new Profile("Luke", "password");
		p.setUsername("John");
		p.setPassword("word");
		assertEquals("John", p.getUsername());
	}
	
	@Test
	void canSetPassword() {
		Profile p = new Profile("Luke", "notPassword");
		p.setPassword("password");
		assertEquals("password", p.getPassword());
	}
}
