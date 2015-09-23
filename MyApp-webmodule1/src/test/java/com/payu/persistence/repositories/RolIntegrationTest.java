package com.payu.persistence.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payu.business.entities.RolEntity;
import com.payu.configurations.AppContext;
import com.payu.util.exceptions.InfrastructureException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppContext.class})
public class RolIntegrationTest {
	
	@Autowired
	RolRepository rolRepository;
	
	@Test
	public void readAllEntities () {
		try {
			for (RolEntity rolEntity : rolRepository.readEntitiesList(new RolEntity()))
				System.out.println(rolEntity.getType());
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void createRol () {
		RolEntity rolEntity = new RolEntity();
		rolEntity.setType("TestJUnit");
		
		try {
			System.out.println(rolRepository.createEntity(rolEntity) + "y El ID es: " + rolEntity.getId());
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
