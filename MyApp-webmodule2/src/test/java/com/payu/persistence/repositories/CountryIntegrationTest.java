package com.payu.persistence.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payu.business.entities.CountryEntity;
import com.payu.configurations.AppContext;
import com.payu.util.exceptions.InfrastructureException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {AppContext.class})
public class CountryIntegrationTest {
	
	@Autowired
	CountryRepository countryRepository;
	
	@Test
	public void readAllEntities () {
		try {
			for (CountryEntity countryEntity : countryRepository.readEntitiesList(new CountryEntity()))
				System.out.println(countryEntity.getCountryName());
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void createCountry () {
		CountryEntity countryEntity = new CountryEntity();
		countryEntity.setCountryName("Peru");
		
		try {
			System.out.println(countryRepository.createEntity(countryEntity) + "y El ID es: " + countryEntity.getId());
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
