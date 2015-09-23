package com.payu.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.payu.business.entities.CountryEntity;
import com.payu.util.architecture.bases.BaseRepository;
import com.payu.util.exceptions.InfrastructureException;

@Repository
public class CountryRepository extends BaseRepository<CountryEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountryRepository() {
		super("payu-pu");
	}
	
	public static void main (String[] argsv) {
		CountryRepository countryRepository = new CountryRepository();
		
		try {
			//READ
			for (CountryEntity countryEntity :countryRepository.readEntitiesList(new CountryEntity())) {
				System.out.println(countryEntity.getCountryName());
			}
			//CREATE
			CountryEntity countryEntity = new CountryEntity();
			countryEntity.setCountryName("test");
			System.out.println(countryRepository.createEntity(countryEntity) + " y el ID es: " + countryEntity.getId() );
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
