package com.example.GoCafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoCafeApplication.class, args);
        System.out.println(">>> entering main"); // ✅ 강제 표준출력
        try {
            SpringApplication.run(GoCafeApplication.class, args);
        } catch (Throwable t) {                    // ✅ 어떤 예외라도 출력
            t.printStackTrace();
        }
        System.out.println(">>> after SpringApplication.run");
	}

}
