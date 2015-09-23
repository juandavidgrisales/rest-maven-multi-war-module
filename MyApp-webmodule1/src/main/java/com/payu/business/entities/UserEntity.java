package com.payu.business.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.payu.util.architecture.bases.BaseEntity;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="UserEntity.findAll", query="SELECT u FROM UserEntity u")
public class UserEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="PAYU_SEQ_GENERATOR1", table="PAYU_SEQ_GENERATOR", pkColumnName="PAYU_SEQ_NAME", valueColumnName="PAYU_SEQ_VAL", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PAYU_SEQ_GENERATOR1")
	private Integer id;

	private String name;

	//bi-directional many-to-one association to RolEntity
	@JoinColumn(name = "rol")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
	private RolEntity rol;

	public UserEntity() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RolEntity getRol() {
		return this.rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
	}

}