package ru.clevertec;

import ru.clevertec.entity.Car;
import ru.clevertec.entity.CarShowroom;
import ru.clevertec.entity.Category;
import ru.clevertec.entity.Client;
import ru.clevertec.entity.Review;
import ru.clevertec.repository.CarRepository;
import ru.clevertec.repository.CarShowroomRepository;
import ru.clevertec.repository.CategoryRepository;
import ru.clevertec.repository.ClientRepository;
import ru.clevertec.repository.ReviewRepository;
import ru.clevertec.util.DatabaseFiller;
import ru.clevertec.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DatabaseFiller.fillDatabase();
        mainTest();
        /*testCriteriaApi();
        testFullTextSearch();
        testL2Cache();*/
        HibernateUtil.shutdown();
    }

    private static void testL2Cache(){
        CarRepository carRepository = new CarRepository();
        ClientRepository clientRepository = new ClientRepository();

        System.out.println("First load from DB:");
        List<Car> all = carRepository.findAll();
        System.out.println("Cars: " + all);

        System.out.println("Second load from Cache:");
        List<Car> all1 = carRepository.findAll();
        System.out.println("Cars: " + all1);

        System.out.println("First load Client from DB:");
        List<Client> loadedClients1 = clientRepository.findAll();
        System.out.println("Client: " + loadedClients1);

        System.out.println("Second load Client from Cache:");
        List<Client> loadedClients2 = clientRepository.findAll();
        System.out.println("Client: " + loadedClients2);
    }

    private static void testFullTextSearch() {
        ReviewRepository reviewService = new ReviewRepository();

        String keyword = "great car";
        List<Review> reviewsByText = reviewService.searchByKeyword(keyword);
        reviewsByText.forEach(review -> System.out.println("Review Text: " + review.getText()));

        int rating = 5;
        List<Review> reviewsByRating = reviewService.searchByRating(rating);
        reviewsByRating.forEach(review -> System.out.println("Review Rating: " + review.getRating()));
    }

    private static void testCriteriaApi() {
        CarRepository carRepository = new CarRepository();
        ReviewRepository reviewRepository = new ReviewRepository();

        List<Car> filteredCars = carRepository.findCarsByParameters("Toyota", 2022, "Truck", 20000.0, 50000.0);
        filteredCars.forEach(car -> System.out.println("Filtered Car: " + car));

        List<Car> sortedCars = carRepository.findCarsSortedByPrice(false);
        sortedCars.forEach(car -> System.out.println("Sorted Car: " + car));

        List<Car> paginatedCars = carRepository.findCarsWithPagination(1, 5);
        paginatedCars.forEach(car -> System.out.println("Paginated Car: " + car));

        List<Review> reviews = reviewRepository.searchByKeyword("great");
        reviews.forEach(review -> System.out.println("Review: " + review.getText()));
    }

    private static void mainTest() {
        CarRepository carRepository = new CarRepository();
        CarShowroomRepository showroomRepository = new CarShowroomRepository();
        ClientRepository clientRepository = new ClientRepository();
        ReviewRepository reviewRepository = new ReviewRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        Category sedan = new Category();
        sedan.setName("Sedan");
        categoryRepository.create(sedan);
        CarShowroom showroom = CarShowroom.builder()
                .name("Affordable Cars Showroom")
                .address("456 Economy St")
                .build();
        showroomRepository.create(showroom);

        // 1. Добавление автомобиля
        System.out.println("Adding a car...");
        Car car = Car.builder()
                .brand("Tesla")
                .model("Model 3")
                .year(2020)
                .price(BigDecimal.valueOf(25000))
                .showroom(showroom)
                .category(sedan)
                .build();
        System.out.println("Added car: " + car);
        carRepository.create(car);

        // 2. Поиск автомобилей по фильтрам
        System.out.println("\nSearching for cars by filters...");
        List<Car> cars = carRepository.findCarsByParameters("Tesla", 2020, "Sedan", 20000.0, 50000.0);
        cars.forEach(foundCar -> System.out.println("Found car: " + foundCar));

        // 3. Привязка автомобиля к автосалону
        System.out.println("\nAssigning car to a showroom...");
        CarShowroom showroom1 = CarShowroom.builder()
                .name("Luxury Cars Showroom")
                .address("123 Luxury Ave")
                .build();
        showroomRepository.create(showroom1);
        carRepository.assignToShowroom(car, showroom1);
        System.out.println("Assigned " + car + " to showroom " + showroom1);

        // 4. Регистрация клиента
        System.out.println("\nRegistering a client...");
        Client client = Client.builder()
                .name("John")
                .contacts(List.of("email@example.com", "+1234567890"))
                .build();
        clientRepository.create(client);
        System.out.println("Registered client: " + client);

        // 5. Привязка автомобиля к клиенту (покупка)
        System.out.println("\nClient buys a car...");
        clientRepository.buyCar(client, car);
        System.out.println(client.getName() + " bought " + car.getModel());

        // 6. Добавление отзыва клиента
        System.out.println("\nAdding a review...");
        reviewRepository.addReview(client, car, "Amazing car! Highly recommend.", 5);
        System.out.println("Review added by " + client.getName() + " for " + car.getModel());

        // 7. Полнотекстовый поиск отзывов
        System.out.println("\nSearching for reviews with keyword 'Amazing'...");
        List<Review> reviews = reviewRepository.searchByKeyword("Amazing");
        reviews.forEach(foundReview -> System.out.println("Found review: " + foundReview.getText()));

        System.out.println("\nTesting completed.");
    }
}