package com.blog.app.blog;

import com.blog.app.blog.config.AppConstants;
import com.blog.app.blog.role.entity.Role;
import com.blog.app.blog.role.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstants.ROLE_USER);
			role2.setName("ROLE_USER");

			List<Role> roles = List.of(role1, role2);

			List<Role> result = this.roleRepo.saveAll(roles);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
