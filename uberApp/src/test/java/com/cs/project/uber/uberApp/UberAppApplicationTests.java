package com.cs.project.uber.uberApp;

import com.cs.project.uber.uberApp.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberAppApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		/*emailSenderService.sendEmail("lehapeb881@neuraxo.com", "This is the testing email.",
				"Body of my email. Let's go......");*/

		String[] emails = {
		"lehapeb881@neuraxo.com", "suvarnakarabhishek76@gmail.com"
		};

		emailSenderService.sendEmail(emails, "Hello from UBER App demo.", "Keep coding !!!");
	}

}
