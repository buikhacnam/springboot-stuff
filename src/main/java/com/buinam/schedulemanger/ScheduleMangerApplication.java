package com.buinam.schedulemanger;

import com.buinam.schedulemanger.model.AppUser;
import com.buinam.schedulemanger.model.Role;
import com.buinam.schedulemanger.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ScheduleMangerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleMangerApplication.class, args);
		System.out.println("hi mom!");
	}


	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner runner(AppUserService appUserService) {
//		return (args) -> {
//			System.out.println("Run from commandline!");
//
//			appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
//			appUserService.saveRole(new Role(null, "ROLE_USER"));
//			appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
//
//
//			appUserService.saveUser(new AppUser(null, "Bui Nam", "buinam", "1234", new ArrayList<>()));
//			appUserService.saveUser(new AppUser(null, "Frank Lampard", "lampard", "1234", new ArrayList<>()));
//			appUserService.saveUser(new AppUser(null, "John Terry", "terry", "1234", new ArrayList<>()));
//
//			appUserService.addRoleToUser("buinam", "ROLE_ADMIN");
//		};
//	}

}
