package com.payu.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.payu.business.entities.RolEntity;
import com.payu.business.entities.UserEntity;
import com.payu.util.architecture.bases.BaseRepository;
import com.payu.util.exceptions.InfrastructureException;

@Repository
public class RolRepository extends BaseRepository<RolEntity> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RolRepository() {
		super("payu-pu");
	}
	
	public static void main (String[] argsv) {
		RolRepository rolRepository = new RolRepository();
		
		try {
			//READ
			for (RolEntity rolEntity :rolRepository.readEntitiesList(new RolEntity())) {
				System.out.println(rolEntity.getType());
			}
			//CREATE
			RolEntity rolEntity = new RolEntity();
			rolEntity.setType("test");
			System.out.println(rolRepository.createEntity(rolEntity) + " y el ID es: " + rolEntity.getId() );
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
