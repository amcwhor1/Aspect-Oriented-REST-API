package com.example.demo;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class MyTasks
{

    RestTemplate restTemplate = new RestTemplate();


    @Timed
    @TotalPrice
    @Scheduled(cron = "*/2 * * * * *")
    public void addVehicle()
    {

        String makeModel = RandomStringUtils.randomAlphabetic(10);
        int year = ThreadLocalRandom.current().nextInt(1986, 2017);

        double retailPrice = ThreadLocalRandom.current().nextInt(15000, 45000);

        Vehicle vehicle = new Vehicle( makeModel, year, retailPrice);
        String url = "http://localhost:8080/addVehicle";

        restTemplate.postForObject(url, vehicle, Vehicle.class);
        System.out.println("djfkasdjfklasdj");
    }


    @Timed
    @TotalPrice
    @Scheduled(cron = "*/2 * * * * *")
    public void deleteVehicle()
    {
        int deleteID = ThreadLocalRandom.current().nextInt(1, 101);

        String getUrl = "http://localhost:8080/getVehicle/" + deleteID;
        String deleteUrl = "http://localhost:8080/deleteVehicle/" + deleteID;

        Vehicle v = restTemplate.getForObject(getUrl, Vehicle.class);

        if(v != null)
        {
            restTemplate.delete(deleteUrl);
            System.out.println("Succesfully deleted " + v);
        }

    }


    @Timed
    @TotalPrice
    @Scheduled(cron = "*/1 * * * * *")
    public void updateVehicle()
    {
        int updateID = ThreadLocalRandom.current().nextInt(0, 100);

        Vehicle newVehicle = new Vehicle( "update", 2020, 9999999);
        newVehicle.setId(updateID);
       // String getURL = "http://localhost:8080/getVehicle" + updateID;
        String updateURL = "http://localhost:8080/updateVehicle";


       restTemplate.put(updateURL, newVehicle, Vehicle.class);

        System.out.println("yo dawg updated");



    }

//    @Future
//    @Scheduled(cron = "*/10 * * * * *")
//    public void getFutureExpensiveCar()
//    {
//        String url = "http://localhost:8080/getFutureExpensiveCar";
//
//        restTemplate.getForObject(url, Vehicle.class);
//    }

    @Scheduled(cron = "* * */1 * * *")
    public void latestVehicle()
    {
        String latestURL = "http://localhost:8080/getLatestVehicles";
        List<Vehicle> vehicleList = restTemplate.getForObject(latestURL, List.class);

        System.out.println("********LATEST VEHICLES***********");

        for(int i = 0; i < vehicleList.size(); i++)
        {
            System.out.println(vehicleList.get(i));
        }
    }











}
