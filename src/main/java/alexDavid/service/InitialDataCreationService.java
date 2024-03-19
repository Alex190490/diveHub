package alexDavid.service;

import alexDavid.models.Activity;
import alexDavid.models.Category;
import alexDavid.models.Item;
import alexDavid.models.Level;
import alexDavid.models.User.User;
import lombok.RequiredArgsConstructor;
import alexDavid.models.Product;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InitialDataCreationService {
    private final ItemService itemService;
    private final ActivityService activityService;
    private final Faker faker = new Faker(new Locale("en-US"));
    private final UserDetailsServiceImpl userDetailsService;


    public void createFakeProducts(int number) {
        if (number <= 0) return;

        for (int i = 0; i<number; i++) {
            int categoryIndex = faker.number().numberBetween(0, Category.values().length);
            Category category = Category.values()[categoryIndex];

            if (category == Category.COURSE || category == Category.DIVE) createFakeActivity(category);
            else createFakeItem(category);
        }
    }


    private void createFakeActivity(Category category) {
        int levelIndex = faker.number().numberBetween(0, Level.values().length);
        Level level = Level.values()[levelIndex];

        LocalDateTime startingDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = startingDate.plusDays(faker.number().numberBetween(1, 10));

        int available_spaces = faker.number().randomDigit();
        boolean available = available_spaces != 0;

        Activity activity = new Activity(
                null,
                faker.commerce().productName(),
                faker.lorem().sentence(20),
                faker.number().randomDouble(2, 50, 100),
                faker.number().randomDouble(2, 50, 100),
                faker.avatar().image(),
                category,
                level,
                startingDate,
                endDate,
                available_spaces,
                available,
                faker.commerce().department()
        );
        activityService.save(activity);
    }


    private void createFakeItem(Category category) {
        Item item = new Item(
                null,
                faker.commerce().productName(),
                faker.lorem().sentence(20),
                faker.number().randomDouble(2, 50, 100),
                faker.number().randomDouble(2, 50, 100),
                faker.avatar().image(),
                faker.number().randomDouble(2, 0, 5),
                category,
                faker.commerce().department()
        );
        itemService.save(item);
    }


    public void createFakeUser(){
        User user = new User("user", "$2a$12$K4tojeaYWMK55KzWzDWtLOuuUjRTkycWhSGHYWA2LXMZqmZUtuXPO"); // Esto es "password" codificado con bcrypt)
        userDetailsService.save(user);
    }
}
