package com.galvanize.flightlog;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface FlightsRepository extends CrudRepository<Flights, Long> {

    @Query(value="select * from flights where pilot = :id", nativeQuery = true)
    public Flights[] findFlightsByPilotId(Long id);

    @Query(value="select * from flights where pilot is NULL", nativeQuery = true)
    public Flights[] findNullFlights();

    @Query(value="SELECT * FROM flights WHERE pilot IS NULL AND id = :id", nativeQuery = true)
    public Flights[] checkIfPilotSlotIsNull(Long id);

    @Query(value="SELECT * FROM flights WHERE depart_time like %:date%", nativeQuery = true)
    public Flights[] getFlightsByDepartDate(String date);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update flights set pilot = :pilotId where id = :flightId", nativeQuery = true)
    public void updateFlightPilotById(Long flightId, Long pilotId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update flights set tail_number = :tailNumber where id = :flightId", nativeQuery = true)
    public void updateFlightPlaneById(Long flightId, Integer tailNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update flights set depart_time = :departTime, arrive_time = :arriveTime where id = :flightId", nativeQuery = true)
    public void updateFlightTimeById(Long flightId, Date departTime, Date arriveTime);

    @Query(value="select flights.id, flights.depart_time, flights.arrive_time, flights.depart_from, flights.arrive_to, flights.tail_number, users.name, flights.remarks FROM flights INNER JOIN users on (users.id = flights.pilot)", nativeQuery = true)
    public ResolvedFlight[] getFlightsResolved();

    @Query(value="select flights.id, flights.depart_time, flights.arrive_time, flights.depart_from, flights.arrive_to, flights.tail_number, users.name, flights.remarks FROM flights INNER JOIN users on (users.id = flights.pilot) where depart_time like %:date%", nativeQuery = true)
    public ResolvedFlight[] getFlightsByDepartDateResolved(String date);

}
