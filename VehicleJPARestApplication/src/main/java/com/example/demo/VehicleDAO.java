package com.example.demo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
    Using JPA greatly reduces the need to interact with DB, more things can be done from java.
    JPA forms the bridge between the java program and relational models (DBs)
 */


@Repository
@Transactional
public class VehicleDAO {

    //primary interface to be used to interact with the JPA at runtime
    //Java Persistence API (JPA) collection of classes and methods to PERSISTENTLY store data into database
    //persistence = storing the copy of database object into temporary memory
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle)
    {
        //persist makes an instance managed and persistent
        entityManager.persist(vehicle);
        return;//why is this needed??************************************

    }

    //this will find the vehicle with the ID that is passed into this method via the
    //'find' method in EntityManager class
    public Vehicle getById(int id)
    {
        return entityManager.find(Vehicle.class, id);
    }


    public Vehicle update(Vehicle newVehicle)
    {
       entityManager.merge(newVehicle);
       return newVehicle;
    }

    public Vehicle delete(int id)
    {
        //create an instance (not an object) set to the
       Vehicle v = entityManager.find(Vehicle.class, id);

       //so when searching, if it DOES find the object with the passed in ID, we want to delete it
       if(v != null)
       {
           entityManager.remove(v);
           return v;
       }
       return null;
    }

    //-------my third aspect method that gets the highest price of a future vehicle
    public int getFutureExpensiveCar()
    {

        Query q = entityManager.createNativeQuery("SELECT MIN(year) FROM vehicle");

        int newExp = (int) q.getSingleResult();
        return newExp;
    }


    public int getHighestId()
    {
        Query q = entityManager.createNativeQuery("SELECT max(id) FROM vehicle");

        int highest = (int) q.getSingleResult();
        return highest;
    }





}
