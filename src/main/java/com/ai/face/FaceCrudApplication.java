package com.ai.face;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Go to: https://www.lfd.uci.edu/~gohlke/pythonlibs/#dlib
// Go to the official CMake website: https://cmake.org/download/
// Download Visual Studio Build Tools: https://visualstudio.microsoft.com/visual-cpp-build-tools/
// You can download precompiled wheels from: https://pypi.org/project/dlib/#files

@SpringBootApplication
public class FaceCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceCrudApplication.class, args);
		System.out.println("Face CRUD Application is running on http://localhost:8080");
	}
}