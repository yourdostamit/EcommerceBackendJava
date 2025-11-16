package com.ecommerce.auth.entity;

import java.util.Date;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "AUTH_USER_DETAILS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	private String firstName;
	private String LastName;
	
	@JsonIgnore
	private String password;
	

    
    private Date createdOn;
    
    private Date updatedOn;
	
	
	private String phoneNumber;
	
	private String provider;
	
	private String verificationCode;
	
	private boolean enabled=false;
	
	
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name= "AUTH_USER_AUTHORITY", joinColumns = @JoinColumn(referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
	
	private List<Authority> authorities;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Adresses> addressList;
	
	@Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	
}
