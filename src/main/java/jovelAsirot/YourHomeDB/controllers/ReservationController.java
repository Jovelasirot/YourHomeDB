package jovelAsirot.YourHomeDB.controllers;

import jovelAsirot.YourHomeDB.entities.Reservation;
import jovelAsirot.YourHomeDB.exceptions.BadRequestException;
import jovelAsirot.YourHomeDB.payloads.ReservationDTO;
import jovelAsirot.YourHomeDB.payloads.ReservationResponseDTO;
import jovelAsirot.YourHomeDB.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ReservationResponseDTO createReservation(@RequestBody @Validated ReservationDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return new ReservationResponseDTO(this.reservationService.save(body).getId());
        }
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable Long reservationId) {
        return reservationService.getReservation(reservationId);

    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Reservation> getAllReservations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.reservationService.getReservations(page, size, sortBy);
    }


    @PutMapping("/update/{reservationId}")
    public Reservation updateReservation(@PathVariable Long reservationId, @RequestBody Reservation reservationBody) {
        return this.reservationService.updateReservation(reservationId, reservationBody);
    }

    @DeleteMapping("/delete/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long reservationId) {
        this.reservationService.deleteReservation(reservationId);
    }

    @GetMapping("/user/{userId}")
    public Page<Reservation> getReservationsForUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return reservationService.getReservationsOfUser(userId, page, size, sortBy);
    }

    @GetMapping("/property/{propertyId}")
    public Page<Reservation> getReservationsForProperty(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return reservationService.getReservationsOfProperty(propertyId, page, size, sortBy);
    }

}
