package com.galvanize.flightlog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long>  {

    @Query(value="select is_admin from users where id= :id", nativeQuery = true)
    public Boolean isUserIdAdmin(Long id);

    @Query(value="select * from users where is_admin = false order by rand() limit 1", nativeQuery = true)
    public Users getRandomPilot();
}
