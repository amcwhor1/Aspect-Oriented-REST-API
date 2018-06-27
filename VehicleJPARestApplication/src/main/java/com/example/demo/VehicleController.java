package com.example.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VehicleController
{

    @Autowired
    private VehicleDAO vehicleDAO;


    @Timed
    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException
    {
        vehicleDAO.create(newVehicle);

        return newVehicle;
    }


    @Timed
    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable ("id") int id) throws IOException
    {
        return vehicleDAO.getById(id);
    }


    @Timed
    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException
    {
        if (vehicleDAO.getById(newVehicle.getId()) == null) {
            return null;
        }
        return vehicleDAO.update(newVehicle);
    }





    @Timed
    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable ("id") int id) throws IOException
    {
        Vehicle v = vehicleDAO.delete(id);

        if(v != null)
        {
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity("Not found", HttpStatus.BAD_REQUEST);

    }


//    @Future
////    @RequestMapping(value = "/getFutureExpensiveCar", method = RequestMethod.GET)
////    public int getFutureExpensiveCar() throws IOException
////    {
////        int v = vehicleDAO.getFutureExpensiveCar();
////        return v;
////    }




    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles( ) throws IOException
    {
        int highestId = vehicleDAO.getHighestId();
        if(highestId == 0)
        {
            return null;
        }
        int foundVehicles = 0;

        List<Vehicle> vehicleList = new ArrayList<Vehicle>();

        //i is the highest ID, then go down one each time until we have incremented
        // foundvehicles 10 times (found 10 vehicles)
        for(int i = highestId; foundVehicles < 10; i-- )
        {
            if(i == 0)
            {
                break; // loop is terminated
            } else if (vehicleDAO.getById(i) != null)
            {
                vehicleList.add(vehicleDAO.getById(i));
                foundVehicles++;
            }


        }
        return vehicleList;
    }





}
