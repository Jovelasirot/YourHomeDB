package jovelAsirot.YourHomeDB.entities;

import jakarta.persistence.*;
import jovelAsirot.YourHomeDB.enums.PropertyStatus;
import jovelAsirot.YourHomeDB.enums.PropertyType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    private String address;

    private double price;

    private double area;

    private int bedrooms;

    private int bathrooms;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;

    private Boolean sold;

    private String description;

    private LocalDate createdAt;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Property(String address, double price, double area, int bedrooms, int bathrooms, String propertyType, String propertyStatus, String description, User user) {
        this.address = address;
        this.price = price;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.propertyType = PropertyType.valueOf(propertyType);
        this.propertyStatus = PropertyStatus.valueOf(propertyStatus);
        this.sold = false;
        this.description = description;
        this.user = user;
        this.createdAt = LocalDate.now();
    }

}
