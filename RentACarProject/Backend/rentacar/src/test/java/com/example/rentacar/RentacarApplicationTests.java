// RentacarApplicationTests.java - Nihai Versiyon
package com.example.rentacar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RentacarApplicationTests {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.username}")
	private String datasourceUsername;

	@Value("${spring.datasource.password}")
	private String datasourcePassword;

	@Value("${server.port}")
	private String serverPort;

	@Test
	void contextLoads() {
		// Spring ApplicationContext'in doğru bir şekilde yüklendiğini doğrular
	}

	@Test
	void applicationStartsSuccessfully() {
		// Uygulamanın başarıyla başladığını kontrol etmek için kullanılabilir
		assertDoesNotThrow(() -> RentacarApplication.main(new String[]{}));
	}

	@Test
	void testProfileLoadsCorrectly() {
		// Aktif profilin "test" olduğunu doğrular
		String activeProfile = System.getProperty("spring.profiles.active");
		assertEquals("test", activeProfile, "Active profile should be 'test'");
	}

	@Test
	void testDatasourceUrlLoaded() {
		// Veritabanı URL'sinin test ortamında yüklendiğini kontrol eder
		assertNotNull(datasourceUrl, "Datasource URL should not be null in the test profile");
		assertTrue(datasourceUrl.contains("jdbc"), "Datasource URL should contain 'jdbc'");
	}

	@Test
	void testDatasourceCredentialsLoaded() {
		// Veritabanı kullanıcı adı ve şifresinin yüklendiğini kontrol eder
		assertNotNull(datasourceUsername, "Datasource username should not be null in the test profile");
		assertNotNull(datasourcePassword, "Datasource password should not be null in the test profile");
		assertTrue(datasourceUsername.length() > 0, "Datasource username should not be empty");
		assertTrue(datasourcePassword.length() > 0, "Datasource password should not be empty");
	}

	@Test
	void testServerPortLoaded() {
		// Sunucu portunun doğru bir şekilde yüklendiğini kontrol eder
		assertNotNull(serverPort, "Server port should not be null");
		assertEquals("8080", serverPort, "Server port should be '8080' in test profile");
	}
}
