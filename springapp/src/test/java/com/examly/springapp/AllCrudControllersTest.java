package com.examly.springapp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.examly.springapp.model.User;
import com.examly.springapp.model.Restaurant;
import com.examly.springapp.model.FoodItem;
import com.examly.springapp.model.Order;
import com.examly.springapp.service.UserService;
import com.examly.springapp.service.RestaurantService;
import com.examly.springapp.service.FoodItemService;
import com.examly.springapp.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AllCrudControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private FoodItemService foodItemService;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    // ********** USER TESTS **********

    @Test
    public void CRUD_test_Get_All_Users() throws Exception {
        User user1 = new User(1L, "John Doe", "password123", "john@example.com");
        User user2 = new User(2L, "Jane Smith", "password456", "jane@example.com");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    public void CRUD_test_Get_User_By_Id() throws Exception {
        User user = new User(1L, "John Doe", "password123", "john@example.com");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void CRUD_test_Create_User() throws Exception {
        User user = new User(1L, "John Doe", "password123", "john@example.com");
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void CRUD_test_Update_User() throws Exception {
        User updatedUser = new User(1L, "John Updated", "password123", "john@example.com");
        when(userService.updateUser(1L, updatedUser)).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    public void CRUD_test_Delete_User() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }

    // ********** RESTAURANT TESTS **********

    @Test
    public void CRUD_test_Get_All_Restaurants() throws Exception {
        Restaurant r1 = new Restaurant(1L, "Pizza Place", "Downtown");
        Restaurant r2 = new Restaurant(2L, "Burger Joint", "Uptown");

        when(restaurantService.getAllRestaurants()).thenReturn(Arrays.asList(r1, r2));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pizza Place"))
                .andExpect(jsonPath("$[1].name").value("Burger Joint"));
    }

    @Test
    public void CRUD_test_Get_Restaurant_By_Id() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
        when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Place"));
    }

    @Test
    public void CRUD_test_Create_Restaurant() throws Exception {
        Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
        when(restaurantService.saveRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        mockMvc.perform(post("/api/restaurants")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Place"));
    }

    @Test
    public void CRUD_test_Update_Restaurant() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(1L, "Pizza Hub", "Downtown");
        when(restaurantService.updateRestaurant(1L, updatedRestaurant)).thenReturn(updatedRestaurant);

        mockMvc.perform(put("/api/restaurants/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedRestaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Hub"));
    }

    @Test
    public void CRUD_test_Delete_Restaurant() throws Exception {
        doNothing().when(restaurantService).deleteRestaurant(1L);

        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());
    }

    // ********** FOOD ITEM TESTS **********

    // @Test
    // public void CRUD_test_Get_All_FoodItems() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     FoodItem item1 = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     FoodItem item2 = new FoodItem(2L, "Pepperoni", 12.0, restaurant);

    //     when(foodItemService.getAllFoodItems()).thenReturn(Arrays.asList(item1, item2));

    //     mockMvc.perform(get("/api/fooditems"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0].name").value("Margherita"))
    //             .andExpect(jsonPath("$[1].name").value("Pepperoni"));
    // }

    // @Test
    // public void CRUD_test_Get_FoodItem_By_Id() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     FoodItem foodItem = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     when(foodItemService.getFoodItemById(1L)).thenReturn(foodItem);

    //     mockMvc.perform(get("/api/fooditems/1"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Margherita"));
    // }

    // @Test
    // public void CRUD_test_Create_FoodItem() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     FoodItem foodItem = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     when(foodItemService.saveFoodItem(any(FoodItem.class))).thenReturn(foodItem);

    //     mockMvc.perform(post("/api/fooditems")
    //             .contentType("application/json")
    //             .content(objectMapper.writeValueAsString(foodItem)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Margherita"));
    // }

    // @Test
    // public void CRUD_test_Update_FoodItem() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     FoodItem updatedItem = new FoodItem(1L, "Veggie Delight", 11.0, restaurant);
    //     when(foodItemService.updateFoodItem(1L, updatedItem)).thenReturn(updatedItem);

    //     mockMvc.perform(put("/api/fooditems/1")
    //             .contentType("application/json")
    //             .content(objectMapper.writeValueAsString(updatedItem)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("Veggie Delight"));
    // }

    // @Test
    // public void CRUD_test_Delete_FoodItem() throws Exception {
    //     doNothing().when(foodItemService).deleteFoodItem(1L);

    //     mockMvc.perform(delete("/api/fooditems/1"))
    //             .andExpect(status().isOk());
    // }

    // // ********** ORDER TESTS **********

    // @Test
    // public void CRUD_test_Get_All_Orders() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     User user = new User(1L, "John Doe", "password123", "john@example.com");
    //     FoodItem foodItem = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     Order order1 = new Order(1L, user, foodItem);
    //     Order order2 = new Order(2L, user, foodItem);

    //     when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

    //     mockMvc.perform(get("/api/orders"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0].id").value(1))
    //             .andExpect(jsonPath("$[1].id").value(2));
    // }

    // @Test
    // public void CRUD_test_Get_Order_By_Id() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     User user = new User(1L, "John Doe", "password123", "john@example.com");
    //     FoodItem foodItem = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     Order order = new Order(1L, user, foodItem);
    //     when(orderService.getOrderById(1L)).thenReturn(order);

    //     mockMvc.perform(get("/api/orders/1"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value(1));
    // }

    // @Test
    // public void CRUD_test_Create_Order() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     User user = new User(1L, "John Doe", "password123", "john@example.com");
    //     FoodItem foodItem = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     Order order = new Order(1L, user, foodItem);
    //     when(orderService.saveOrder(any(Order.class))).thenReturn(order);

    //     mockMvc.perform(post("/api/orders")
    //             .contentType("application/json")
    //             .content(objectMapper.writeValueAsString(order)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value(1));
    // }

    // @Test
    // public void CRUD_test_Update_Order() throws Exception {
    //     Restaurant restaurant = new Restaurant(1L, "Pizza Place", "Downtown");
    //     User user = new User(1L, "John Doe", "password123", "john@example.com");
    //     FoodItem foodItem = new FoodItem(1L, "Margherita", 10.0, restaurant);
    //     Order updatedOrder = new Order(1L, user, foodItem);
    //     when(orderService.updateOrder(1L, updatedOrder)).thenReturn(updatedOrder);

    //     mockMvc.perform(put("/api/orders/1")
    //             .contentType("application/json")
    //             .content(objectMapper.writeValueAsString(updatedOrder)))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.id").value(1));
    // }

    // @Test
    // public void CRUD_test_Delete_Order() throws Exception {
    //     doNothing().when(orderService).deleteOrder(1L);

    //     mockMvc.perform(delete("/api/orders/1"))
    //             .andExpect(status().isOk());
    // }
}
