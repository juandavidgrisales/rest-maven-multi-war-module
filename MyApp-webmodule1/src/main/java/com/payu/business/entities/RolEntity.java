package com.payu.business.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.payu.util.architecture.bases.BaseEntity;

import java.util.List;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@Table(name="rol")
@NamedQuery(name="RolEntity.findAll", query="SELECT r FROM RolEntity r")
public class RolEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="PAYU_SEQ_GENERATOR", table="PAYU_SEQ_GENERATOR", pkColumnName="PAYU_SEQ_NAME", valueColumnName="PAYU_SEQ_VAL", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PAYU_SEQ_GENERATOR")
	private Integer id;

	private String type;

	//bi-directional many-to-one association to UserEntity
	//@OneToMany(mappedBy="rolBean", fetch=FetchType.EAGER)
	@Transient
	private List<UserEntity> users;

	public RolEntity() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<UserEntity> getUsers() {
		return this.users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public UserEntity addUser(UserEntity user) {
		getUsers().add(user);
		user.setRol(this);

		return user;
	}

	public UserEntity removeUser(UserEntity user) {
		getUsers().remove(user);
		user.setRol(null);

		return user;
	}

}