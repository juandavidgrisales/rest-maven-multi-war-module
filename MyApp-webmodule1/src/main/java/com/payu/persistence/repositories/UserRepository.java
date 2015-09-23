package com.payu.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.payu.business.entities.RolEntity;
import com.payu.business.entities.UserEntity;
import com.payu.util.architecture.bases.BaseRepository;
import com.payu.util.exceptions.InfrastructureException;

@Repository
public class UserRepository extends BaseRepository<UserEntity>{

	public UserRepository() {
		super("payu-pu");
	}
	
	public static void main (String[] argsv) {
		UserRepository userRepository = new UserRepository();
		
		
		try {
			//READ
			for (UserEntity userEntity :userRepository.readEntitiesList(new UserEntity())) {
				System.out.println(userEntity.getName());
			}
			//CREATE
			UserEntity userEntity = new UserEntity();
			userEntity.setName("Carlangas");
			RolEntity rolEntity = new RolEntity();
			rolEntity.setId(new Integer(1));
			userEntity.setRol(rolEntity);
			System.out.println(userRepository.createEntity(userEntity) + " y el ID es: " +userEntity.getId());
		} catch (InfrastructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
