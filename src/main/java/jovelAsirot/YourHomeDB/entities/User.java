package jovelAsirot.YourHomeDB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jovelAsirot.YourHomeDB.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"password", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked", "enabled"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String avatar;

    private LocalDate birthdate;

    private String country;

    private String phone;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_favorite_properties",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    private Set<Property> favoriteProperties = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reservation> reservations = new HashSet<>();

    public User(String name, String surname, String email, String password, String role, String avatar, String birthdate, String country, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = UserRole.valueOf(role);
        this.avatar = avatar;
        this.birthdate = LocalDate.parse(birthdate);
        this.country = country;
        this.phone = phone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        {
            return List.of(new SimpleGrantedAuthority(this.role.name()));
        }
    }

    @Override
    public String getUsername() {
        return null;
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
