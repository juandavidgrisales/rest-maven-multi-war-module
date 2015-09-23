package com.payu.business.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.payu.util.architecture.bases.BaseEntity;

import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@Table(name="country")
@NamedQuery(name="CountryEntity.findAll", query="SELECT c FROM CountryEntity c")
public class CountryEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="PAYU_SEQ_GENERATOR", table="PAYU_SEQ_GENERATOR", pkColumnName="PAYU_SEQ_NAME", valueColumnName="PAYU_SEQ_VAL", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PAYU_SEQ_GENERATOR")
	private Integer id;

	private String countryName;

	public CountryEntity() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String type) {
		this.countryName = type;
	}

}