package jovelAsirot.YourHomeDB.services;

import com.cloudinary.Cloudinary;
import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.entities.User;
import jovelAsirot.YourHomeDB.exceptions.NotFoundException;
import jovelAsirot.YourHomeDB.payloads.PropertyDTO;
import jovelAsirot.YourHomeDB.repositories.PropertyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PropertyService {

    @Autowired
    private PropertyDAO pDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Property save(PropertyDTO payload) {

        Long userId = extractUserIdFromToken();

        User userFound = this.userService.findById(userId);

        Property newProperty = new Property(payload.address(), payload.price(), payload.area(), payload.bedrooms(), payload.bedrooms(), payload.propertyType(), payload.propertyStatus(), userFound);
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

    private Long extractUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userFound = (User) authentication.getPrincipal();
        return userFound.getId();
    }

    public List<String> uploadPropertyImage(Long propertyId, List<MultipartFile> images) throws IOException {
        Property property = getProperty(propertyId);

        List<String> uploadedImages = new ArrayList<>();

        for (MultipartFile image : images) {
            Map<String, Object> uploadResult = cloudinaryUploader.uploader().upload(image.getBytes(), null);
            String url = (String) uploadResult.get("url");
            uploadedImages.add(url);
        }

        property.getImages().addAll(uploadedImages);

        pDAO.save(property);

        return uploadedImages;
    }

}
