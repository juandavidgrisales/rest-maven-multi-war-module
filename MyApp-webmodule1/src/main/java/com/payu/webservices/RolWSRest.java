package com.payu.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payu.business.entities.RolEntity;
import com.payu.persistence.repositories.RolRepository;
import com.payu.util.exceptions.InfrastructureException;
import com.payu.util.loggers.Log;

@Path("/rol")
@Component
public class RolWSRest {
	
	@Autowired
	private RolRepository rolRepository;
	
	@GET
	@Produces(value = {MediaType.APPLICATION_JSON})
	public List<RolEntity> readRolList () {
		try {
			return (List<RolEntity>) rolRepository.readEntitiesList(new RolEntity());
		} catch (InfrastructureException e) {
			Log.error("RolWSRest.readRolList: " + e);
			return null;
		}
	}

}
