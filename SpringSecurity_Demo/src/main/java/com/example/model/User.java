package com.example.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;
	@Column(name = "email")
	@Email(message = "*Вы ввели некорректный e-mail")
	@NotEmpty(message = "*Поле e-mail не может быть пустым")
	private String email;
	@Column(name = "password")
	@Length(min = 5, message = "*Пароль должен содержать не менее 5 символов")
	@NotEmpty(message = "*Поле \"Пароль\" не может быть пустым")
	@Transient
	private String password;
	@Column(name = "name")
	@NotEmpty(message = "*Поле \"Имя\" не может быть пустым")
	private String name;
	@Column(name = "last_name")
	@NotEmpty(message = "*Поле \"Фамилия\" не может быть пустым")
	private String lastName;
	@Column(name = "active")
	private int active;
	@Column(name = "cookie")
	private String cookie;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id",nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
	private Set<Role> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
