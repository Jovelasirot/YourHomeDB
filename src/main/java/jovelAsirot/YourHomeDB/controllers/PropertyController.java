package jovelAsirot.YourHomeDB.controllers;


import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.exceptions.BadRequestException;
import jovelAsirot.YourHomeDB.payloads.PropertyDTO;
import jovelAsirot.YourHomeDB.payloads.UserResponseDTO;
import jovelAsirot.YourHomeDB.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public UserResponseDTO saveProperty(@RequestBody @Validated PropertyDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return new UserResponseDTO(this.propertyService.save(body).getId());
        }
    }

    @DeleteMapping("{propertyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long propertyId) {
        this.propertyService.deleteById(propertyId);
    }
    
}
