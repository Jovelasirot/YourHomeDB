package jovelAsirot.YourHomeDB.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.entities.User;
import jovelAsirot.YourHomeDB.exceptions.BadRequestException;
import jovelAsirot.YourHomeDB.exceptions.NotFoundException;
import jovelAsirot.YourHomeDB.payloads.UserDTO;
import jovelAsirot.YourHomeDB.payloads.UserResponseFavoriteDTO;
import jovelAsirot.YourHomeDB.repositories.PropertyDAO;
import jovelAsirot.YourHomeDB.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDAO uDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private PropertyDAO pDAO;

    public Page<User> getUsers(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.uDAO.findAll(pageable);
    }

    public User save(UserDTO payload) throws BadRequestException {
        this.uDAO.findByEmail(payload.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email: " + user.getEmail() + " is already being used (ᗒᗣᗕ)՞");
                }
        );
        User newUser = new User(payload.name(), payload.surname(), payload.email(), bcrypt.encode(payload.password()), payload.role() == null ? "USER" : payload.role(), "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname(), payload.birthdate());

        return uDAO.save(newUser);
    }

    public User findById(Long userId) {
        return this.uDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User updateById(Long userId, User updatedUser) {
        User userFound = this.findById(userId);

        this.uDAO.findByEmailAndId(updatedUser.getEmail(), userId).ifPresent(
                employee -> {
                    throw new BadRequestException("The email: " + employee.getEmail() + " is already being used by another user (ᗒᗣᗕ)՞ ");
                }
        );

        userFound.setName(updatedUser.getName());
        userFound.setSurname(updatedUser.getSurname());
        userFound.setEmail(updatedUser.getEmail() == null ? userFound.getEmail() : updatedUser.getEmail());

        return this.uDAO.save(userFound);
    }

    public void deleteById(Long userId) {
        User userFound = this.findById(userId);

        this.uDAO.delete(userFound);
    }

    public User findByEmail(String email) {
        return uDAO.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public String uploadProfileImage(Long userId, MultipartFile image) throws IOException {
        User user = findById(userId);

        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");

        user.setAvatar(url);
        uDAO.save(user);
        return url;
    }

    public boolean updateFavoriteProperty(Long userId, Long propertyId) {
        User user = findById(userId);
        Property property = pDAO.findById(propertyId)
                .orElseThrow(() -> new NotFoundException("Property not found with ID: " + propertyId));

        if (user.getFavoriteProperties().contains(property)) {
            user.getFavoriteProperties().remove(property);
            uDAO.save(user);
            return false;

        } else {
            user.getFavoriteProperties().add(property);
            uDAO.save(user);
            return true;
        }
    }

    public UserResponseFavoriteDTO getUserFavoriteProperties(Long userId) {
        User user = uDAO.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.getFavoriteProperties().size();

        return new UserResponseFavoriteDTO(user.getFavoriteProperties()
                .stream()
                .map(Property::getId)
                .collect(Collectors.toSet()));
    }

}
