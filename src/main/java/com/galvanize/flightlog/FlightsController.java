package com.galvanize.flightlog;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class FlightsController {

    private final FlightsRepository flightsRepository;
    private final UsersRepository usersRepository;
    private final ModelsRepository modelsRepository;

    public FlightsController(FlightsRepository flightsRepository,
                             UsersRepository usersRepository,
                             ModelsRepository modelsRepository) {
        this.flightsRepository = flightsRepository;
        this.usersRepository = usersRepository;
        this.modelsRepository = modelsRepository;
    }

    @GetMapping("/flights/pilot/{id}")
    public Flights[] getFlightsByPilot(@PathVariable Long id) {
        return this.flightsRepository.findFlightsByPilotId(id);
    }

    @PostMapping("/flights/add/{id}")
    public Flights addFlight(@PathVariable Long id, @RequestBody Flights newFlight, HttpServletResponse resp) {
        if (this.usersRepository.isUserIdAdmin(id)) {
            return this.flightsRepository.save(newFlight);
        }
        else {
            resp.setStatus(500);
            return null;
        }
    }

    @DeleteMapping("/pilot/delete/{id}/{pilotId}")
    public String deletePilot(@PathVariable Long id, @PathVariable Long pilotId, HttpServletResponse resp) {
        if (this.usersRepository.isUserIdAdmin(id)) {
            try {
                this.usersRepository.deleteById(pilotId);
                return "Deleted";
            } catch (Exception e) {
                resp.setStatus(500);
                return "User does not exist";
            }
        }
        else {
            return "Action requires an admin";
        }
    }

    @PostMapping("/pilot/add/{id}")
    public Users addPilot(@PathVariable Long id, @RequestBody Users newPilot, HttpServletResponse resp) {
        if (this.usersRepository.isUserIdAdmin(id)) {
            return this.usersRepository.save(newPilot);
        }
        else {
            resp.setStatus(500);
            return null;
        }
    }

    @DeleteMapping("/flights/delete/{id}/{flightId}")
    public String deleteFlight(@PathVariable Long id, @PathVariable Long flightId, HttpServletResponse resp) {
        if (this.usersRepository.isUserIdAdmin(id)) {
            try {
                this.flightsRepository.deleteById(flightId);
                return "Deleted";
            }
            catch (Exception e) {
                resp.setStatus(500);
                return "Flight ID not found";
            }
        }
        else {
            resp.setStatus(500);
            return "Action requires admin";
        }
    }

    @PatchMapping("/flights/update/pilot/{id}/{flightId}/{pilotId}")
    public String updateFlight(@PathVariable Long id, @PathVariable Long flightId, @PathVariable Long pilotId, HttpServletResponse resp) {
        if (this.usersRepository.isUserIdAdmin(id)) {
            try {
                this.flightsRepository.updateFlightPilotById(flightId, pilotId);
                return "Updated";
            }
            catch (Exception e) {
                resp.setStatus(500);
                return "Flight ID or Pilot ID not found";
            }
        }
        else {
            // check if the flight pilot pos is NULL
            if (this.flightsRepository.checkIfPilotSlotIsNull(flightId).length != 0 && id == pilotId) {
                this.flightsRepository.updateFlightPilotById(flightId, pilotId);
                return "Updated";
            }
            else if (this.flightsRepository.checkIfPilotSlotIsNull(flightId).length == 0) {
                Optional<Flights> flt = this.flightsRepository.findById(flightId);
                if (flt.isPresent()) {
                    System.out.println(id);
                    System.out.println(flt.get().getPilot());
                    if (id == Long.valueOf(flt.get().getPilot())) {
                        // means requestor ID is the pilot currently assigned to this flight
                        this.flightsRepository.updateFlightPilotById(flightId, null);
                        return "Pilot Removed";
                    }
                    else {
                        return "Cant remove pilot";
                    }
                }
                else {
                    return "Flight Not Found";
                }

            }
            else {
                return "Pilot slot must be null to assign oneself, and request id must equal pilot id";
            }
        }
    }

    @PatchMapping("/flights/update/time/{id}/{flightId}/{departTime}/{arriveTime}")
    public String updateTimeFlight(@PathVariable Long id, @PathVariable Long flightId,
                                   @PathVariable String departTime, @PathVariable String arriveTime, HttpServletResponse resp) {
        if (this.usersRepository.isUserIdAdmin(id)) {
            try {
                this.flightsRepository.updateFlightTimeById(flightId, departTime, arriveTime);
                return "Updated";
            }
            catch (Exception e) {
                resp.setStatus(500);
                return "Flight ID not found";
            }
        }
        else {
            // check if the flight pilot == requestor id
            Optional<Flights> flt = this.flightsRepository.findById(id);
            if (flt.isPresent()) {
                if (id == Long.valueOf(flt.get().getPilot())) {
                    this.flightsRepository.updateFlightTimeById(flightId, departTime, arriveTime);
                    return "Updated";
                } else {
                    return "Flight time changes requires requestor to be pilot of that flight";
                }
            }
            else {
                return "Requestor not an admin or flight doesn't exist";
            }
        }
    }

    @PatchMapping("/flights/update/remarks/{flightId}")
    public Flights updateFlightsRemarks(@PathVariable Long flightId, @RequestBody String remarks,
                                        HttpServletResponse resp) {

        Optional<Flights> theFlight = this.flightsRepository.findById(flightId);
        if (theFlight.isPresent()) {
            Flights flt = theFlight.get();
            flt.setRemarks(remarks);
            return this.flightsRepository.save(flt);
        }
        else {
            resp.setStatus(500);
            return null;
        }
    }

    @PatchMapping("/flights/fillFlights/{id}")
    public String fillFlightsWithPilots(@PathVariable Long id, HttpServletResponse resp) {

        if (this.usersRepository.isUserIdAdmin(id)) {
            Flights[] nullFlights = this.flightsRepository.findNullFlights();
            if (nullFlights.length > 0) {
                for (Flights flt : nullFlights) {
                    Users candidate = this.usersRepository.getRandomPilot();
                    this.flightsRepository.updateFlightPilotById(flt.getId(), candidate.getId());
                }

                return "Done";
            }
            else {
                return "No empty pilot seats found";
            }

        }
        else {
            resp.setStatus(500);
            return "User must be an admin";
        }

    }


}
