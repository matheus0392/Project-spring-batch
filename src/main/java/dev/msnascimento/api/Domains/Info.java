package dev.msnascimento.api.Domains;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Info implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Column
	private String name;

	@Column
	private String CPF;

	public Info() {
	}

	public Info(Long id, String name, String CPF) {
		super();
		this.id = id;
		this.name = name;
		this.CPF = CPF;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCPF() {
		return this.CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	@Override
	public String toString() {
		return "Info [id=" + id + ", name=" + name + ", CPF=" + CPF + "]";
	}

}
