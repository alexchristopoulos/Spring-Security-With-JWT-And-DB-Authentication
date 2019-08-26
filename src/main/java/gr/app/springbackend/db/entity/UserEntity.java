package gr.app.springbackend.db.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity implements UserDetails{

    private static final long serialVersionUID = 194987917964L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(
        name = "user_generator",
        sequenceName = "user_sequence",
        initialValue = 1199,
        allocationSize = 1
    )
    private Long id;

    @NotBlank
    @Column(name ="username")
    @Size(min = 2, max = 64)
    private String username;

    @NotBlank
    @Column(name ="password")
    @Size(min = 8, max = 256)
    private String password;

    @NotBlank
    @Column(name ="name")
    @Size(min = 2, max = 64)
    private String name;

    @NotBlank
    @Column(name ="surname")
    @Size(min = 2, max = 64)
    private String surname;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="authorities")
    private List<String> authorities = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<String> getAuthoritiesList(){
        return this.authorities;
    }

    public void setAuthorities(List<String> authorities){
        this.authorities = authorities;
    }

    public UserEntity() {

    }

    public UserEntity(Long id, String username, String password, String name, String surname, List<String> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
	}   
}