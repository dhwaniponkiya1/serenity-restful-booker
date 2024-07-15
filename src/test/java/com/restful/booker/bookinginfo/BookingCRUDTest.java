package com.restful.booker.bookinginfo;

import com.restful.booker.info.BookingSteps;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class BookingCRUDTest extends TestBase {

    String firstName = "John" + TestUtils.getRandomValue();
    String lastName = "Vialy" + TestUtils.getRandomValue();
    int totalPrice = 900;
    boolean depositPaid = true;
    String checkIn = "2020-05-03";
    String checkOut = "2020-05-10";
    String additionalNeeds = "Lunch";
    static int bookingId;

    @Steps
    BookingSteps bookingSteps;

    @Title("Create a new Booking")
    @Test
    public void test001() {

        ValidatableResponse response = bookingSteps.createBooking(firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds);

        response.statusCode(200);
        response.log().body();

        bookingId = response.extract().path("bookingid");
    }

    @Title("Get booking detail by id")
    @Test
    public void test002() {
        ValidatableResponse response = bookingSteps.getBookingById(bookingId);
        response.statusCode(200);
    }


    @Title("Update booking detail")
    @Test
    public void test003() {
        String fName = "John" + "Updated";
        String lName = "Vialy" + "Updated";

        ValidatableResponse response = bookingSteps.updateBookingById(bookingId, fName, lName, totalPrice, depositPaid, checkIn,checkOut, additionalNeeds);
        response.statusCode(200);
    }

    @Title("Delete booking")
    @Test
    public void test005() {
        bookingSteps.deleteBookingById(bookingId).statusCode(201);
        bookingSteps.getBookingById(bookingId).statusCode(404);

    }



}
