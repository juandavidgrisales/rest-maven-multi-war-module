package com.payu.webservices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payu.business.entities.CountryEntity;
import com.payu.persistence.repositories.CountryRepository;
import com.payu.util.exceptions.InfrastructureException;
import com.payu.util.loggers.Log;

@Path("/country")
@Component
public class CountryWSRest {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@GET
	@Produces(value = {MediaType.APPLICATION_JSON})
	public List<CountryEntity> readCountryList () {
		try {
			return (List<CountryEntity>) countryRepository.readEntitiesList(new CountryEntity());
		} catch (InfrastructureException e) {
			Log.error("CountryWSRest.readCountryList: " + e);
			return null;
		}
	}

}
