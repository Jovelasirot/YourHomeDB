package jovelAsirot.YourHomeDB.services;

import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.entities.Reservation;
import jovelAsirot.YourHomeDB.entities.User;
import jovelAsirot.YourHomeDB.enums.ReservationStatus;
import jovelAsirot.YourHomeDB.exceptions.NotFoundException;
import jovelAsirot.YourHomeDB.payloads.ReservationDTO;
import jovelAsirot.YourHomeDB.repositories.PropertyDAO;
import jovelAsirot.YourHomeDB.repositories.ReservationDAO;
import jovelAsirot.YourHomeDB.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {

    @Autowired
    private ReservationDAO rDAO;

    @Autowired
    private UserDAO uDAO;

    @Autowired
    private PropertyDAO pDAO;

    public Reservation save(ReservationDTO payload) {
        User user = uDAO.findById(payload.userId()).orElseThrow(() -> new NotFoundException(payload.userId()));
        Property property = pDAO.findById(payload.propertyId()).orElseThrow(() -> new NotFoundException(payload.propertyId()));

        Reservation reservation = new Reservation(user, property, payload.reservationDate(), payload.time(), payload.reservationStatus() == null ? String.valueOf(ReservationStatus.PENDING) : payload.reservationStatus());
        return rDAO.save(reservation);
    }

    public Reservation getReservation(Long reservationId) {
        return this.rDAO.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));
    }

    public Page<Reservation> getReservations(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.rDAO.findAll(pageable);
    }

    public Reservation updateReservation(Long reservationId, Reservation updatedReservation) {
        Reservation reservationFound = rDAO.findById(reservationId).orElseThrow(() -> new NotFoundException(reservationId));

        reservationFound.setReservationDate(updatedReservation.getReservationDate() == null ? reservationFound.getReservationDate() : updatedReservation.getReservationDate());
        reservationFound.setTime(updatedReservation.getTime() == null ? reservationFound.getTime() : updatedReservation.getTime());
        reservationFound.setReservationStatus(updatedReservation.getReservationStatus() == null ? reservationFound.getReservationStatus() : updatedReservation.getReservationStatus());
        reservationFound.setUpdatedAt(LocalDate.now());
        return rDAO.save(reservationFound);
    }

    public void deleteReservation(Long reservationId) {
        Reservation reservationFound = this.getReservation(reservationId);
        rDAO.delete(reservationFound);
    }

    public Page<Reservation> getReservationsOfUser(Long userId, int page, int size, String sortBy) {
        User user = uDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return rDAO.findByUser(user, pageable);
    }

    public Page<Reservation> getReservationsOfProperty(Long propertyId, int page, int size, String sortBy) {
        Property property = pDAO.findById(propertyId).orElseThrow(() -> new NotFoundException(propertyId));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return rDAO.findByProperty(property, pageable);
    }

}
