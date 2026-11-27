package com.example.demo;

import com.flickr4java.flickr.Flickr;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ApplicationTests {

	@MockitoBean
	private Flickr flickr;

	@Test
	void contextLoads() {
	}

}
