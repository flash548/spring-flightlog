package com.galvanize.flightlog;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FlightsRepository extends CrudRepository<Flights, Long> {

    @Query(value="select * from flights where pilot = :id", nativeQuery = true)
    public Flights[] findFlightsByPilotId(Long id);

    @Query(value="select * from flights where pilot is NULL", nativeQuery = true)
    public Flights[] findNullFlights();

    @Query(value="SELECT * FROM flights WHERE pilot IS NULL AND id = :id", nativeQuery = true)
    public Flights[] checkIfPilotSlotIsNull(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update flights set pilot = :pilotId where id = :flightId", nativeQuery = true)
    public void updateFlightPilotById(Long flightId, Long pilotId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update flights set depart_time = :departTime, arrive_time = :arriveTime where id = :flightId", nativeQuery = true)
    public void updateFlightTimeById(Long flightId, String departTime, String arriveTime);

}
