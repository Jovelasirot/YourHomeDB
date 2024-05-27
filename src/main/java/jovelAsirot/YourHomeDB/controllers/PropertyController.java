package jovelAsirot.YourHomeDB.controllers;


import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.enums.PropertyStatus;
import jovelAsirot.YourHomeDB.enums.PropertyType;
import jovelAsirot.YourHomeDB.exceptions.BadRequestException;
import jovelAsirot.YourHomeDB.payloads.PropertyDTO;
import jovelAsirot.YourHomeDB.payloads.PropertyResponseDTO;
import jovelAsirot.YourHomeDB.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public Page<Property> getAllProperties(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.propertyService.getProperties(page, size, sortBy);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyResponseDTO saveProperty(@RequestBody @Validated PropertyDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return new PropertyResponseDTO(this.propertyService.save(body).getId());
        }
    }

    @DeleteMapping("{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(@PathVariable Long propertyId) {
        this.propertyService.deleteById(propertyId);
    }

    @PostMapping("/add/{propertyId}/images")
    public PropertyResponseDTO uploadPropertyImages(@PathVariable Long propertyId, @RequestParam("images") List<MultipartFile> images) throws IOException {
        this.propertyService.uploadPropertyImage(propertyId, images);
        return new PropertyResponseDTO(propertyId);
    }

    @PutMapping("{propertyId}")
    public Property updateProperty(@PathVariable Long propertyId, @RequestBody Property propertyBody) {
        return this.propertyService.updateById(propertyId, propertyBody);
    }

    @GetMapping("/search")
    public Page<Property> searchProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minBedrooms,
            @RequestParam(required = false) Integer maxBedrooms,
            @RequestParam(required = false) Integer minBathrooms,
            @RequestParam(required = false) Integer maxBathrooms,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) String propertyStatus,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) String country
    ) {

        PropertyStatus status = propertyStatus != null ? PropertyStatus.valueOf(propertyStatus) : null;
        PropertyType type = propertyType != null ? PropertyType.valueOf(propertyType) : null;

        return this.propertyService.filterProperties(
                page, size, sortBy,
                city, minPrice, maxPrice,
                minBedrooms, maxBedrooms,
                minBathrooms, maxBathrooms,
                minArea, maxArea,
                status, type, country
        );
    }

    @GetMapping("/{propertyId}")
    public Property getPropertyById(@PathVariable Long propertyId) {
        return this.propertyService.getProperty(propertyId);
    }


}
