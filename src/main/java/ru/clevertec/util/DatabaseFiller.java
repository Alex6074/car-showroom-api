package ru.clevertec.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.entity.Client;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.CarShowroomRepository;
import ru.clevertec.repository.CategoryRepository;
import ru.clevertec.repository.ClientRepository;
import ru.clevertec.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@UtilityClass
public class DatabaseFiller {
    public static void fillDatabase() {
        CarRepository carRepository = new CarRepository();
        CarShowroomRepository showroomRepository = new CarShowroomRepository();
        ClientRepository clientRepository = new ClientRepository();
        ReviewRepository reviewRepository = new ReviewRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        Category sedan = Category.builder()
                .name("Sedan")
                .build();
        categoryRepository.create(sedan);

        Category suv = Category.builder()
                .name("SUV")
                .build();
        categoryRepository.create(suv);

        Category truck = Category.builder()
                .name("Truck")
                .build();
        categoryRepository.create(truck);

        CarShowroom showroom1 = CarShowroom.builder()
                .name("Luxury Cars Showroom")
                .address("123 Luxury Ave")
                .build();
        showroomRepository.create(showroom1);

        CarShowroom showroom2 = CarShowroom.builder()
                .name("Affordable Cars Showroom")
                .address("456 Economy St")
                .build();
        showroomRepository.create(showroom2);

        List<Car> cars = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Car car = Car.builder()
                    .brand(i % 2 == 0 ? "Toyota" : "BMW")
                    .model("Model-" + i)
                    .year(2020 + i % 3)
                    .price(20000D + i * 3000)
                    .showroom(i % 2 == 0 ? showroom1 : showroom2)
                    .build();

            if (i % 3 == 0) {
                car.setCategory(sedan);
            } else if (i % 3 == 1) {
                car.setCategory(suv);
            } else {
                car.setCategory(truck);
            }

            carRepository.create(car);
            cars.add(car);
        }

        List<Client> clients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Client client = Client.builder()
                    .name("Client-" + i)
                    .contacts(List.of("email" + i + "@example.com", "+1234567890"))
                    .registrationDate(LocalDate.now().minusDays(i * 10L))
                    .build();
            clientRepository.create(client);
            clients.add(client);
        }

        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            Car car = cars.get(i);
            clientRepository.buyCar(client, car);
        }

        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            Client client = clients.get(i % clients.size());
            reviewRepository.addReview(client, car, "Great car! Review by " + client.getName(), 5);
        }

        System.err.println("Database filled successfully!");
    }
}
