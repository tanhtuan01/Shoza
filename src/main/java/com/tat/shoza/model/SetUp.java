package com.tat.shoza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_setup")
public class SetUp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "set_name")
	private String setName;
	
	@Column(name = "set_value")
	private String setValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}

	public SetUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SetUp [id=" + id + ", setName=" + setName + ", setValue=" + setValue + "]";
	}
	
}
