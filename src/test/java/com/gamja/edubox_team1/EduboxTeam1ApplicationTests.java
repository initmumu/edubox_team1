package com.gamja.edubox_team1;

import com.gamja.edubox_team1.auth.service.UserSetupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EduboxTeam1ApplicationTests {

	@Autowired
	private UserSetupService userSetupService;

	@Test
	void createTestUser() {
		userSetupService.createUser("testuser", "password123", "USER");
	}

}
