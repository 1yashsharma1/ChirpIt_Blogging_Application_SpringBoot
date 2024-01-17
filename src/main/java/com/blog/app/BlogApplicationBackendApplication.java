package com.blog.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.blog.app.configs.AppConstants;
import com.blog.app.entities.Role;
import com.blog.app.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplicationBackendApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationBackendApplication.class, args);
	}

	@Override
	public void run(String... args) {

		try {
			Role role = new Role();
			role.setId(AppConstants.NORMAL_USER);
			role.setName("NORMAL_ROLE");

			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ADMIN_ROLE");

			List<Role> list = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(list);

			result.forEach(r -> {
				System.out.println(role.getId());
				System.out.println(role.getName());
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
