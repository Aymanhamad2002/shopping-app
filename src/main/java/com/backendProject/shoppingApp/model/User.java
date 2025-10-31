package com.backendProject.shoppingApp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long  id ;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
	private Cart cart ;
	
	@OneToMany(mappedBy = "user" ,fetch =FetchType.EAGER,cascade =  CascadeType.ALL, orphanRemoval =  true)
	private List<Order> orders = new ArrayList<Order>();
	
	@ManyToMany(fetch = FetchType.EAGER , cascade = {
			CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="user_roles", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
			inverseJoinColumns =  @JoinColumn(name = "role_id", referencedColumnName = "id")
			)
	private Set<Role> roles = new HashSet<>();
}
