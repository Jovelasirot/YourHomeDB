package jovelAsirot.YourHomeDB.services;

import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.exceptions.NotFoundException;
import jovelAsirot.YourHomeDB.payloads.PropertyDTO;
import jovelAsirot.YourHomeDB.repositories.PropertyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyDAO pDAO;

    public Property save(PropertyDTO payload) {
        Property newProperty = new Property(payload.address(), payload.price(), payload.area(), payload.bedrooms(), payload.bedrooms(), payload.propertyType(), payload.propertyStatus());
        return pDAO.save(newProperty);
    }

    public Property getProperty(Long propertyId) {
        return this.pDAO.findById(propertyId).orElseThrow(() -> new NotFoundException(propertyId));
    }

    public Page<Property> getProperties(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.pDAO.findAll(pageable);
    }

    public void deleteById(Long propertyId) {
        pDAO.deleteById(propertyId);
    }

}
