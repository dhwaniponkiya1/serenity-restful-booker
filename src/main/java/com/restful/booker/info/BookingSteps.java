package com.restful.booker.info;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.constants.Path;
import com.restful.booker.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class BookingSteps {

    @Step("Creating user with firstName : {0}, lastName : {1}, totalPrice :{2}, depositPaid : {3}, checkin : {4}, checkOut :{5}, additionalNeeds :{6}")
    public ValidatableResponse createBooking(String firstName, String lastName, int totalPrice, boolean depositPaid, String checkin, String checkout, String additionalNeeds) {

        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstName, lastName, totalPrice, depositPaid, checkin, checkout, additionalNeeds);

        return SerenityRest.given().
                header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post(Path.BOOKING)
                .then();
    }

    @Step("Get booking information with bookingId :{0}")
    public ValidatableResponse getBookingById(int bookingId) {
        return SerenityRest.given()
                .pathParam("bookingID", bookingId)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }

    @Step("Updating booking info with bookingId :{0}, firstName : {1}, lastName : {2}, totalPrice :{3}, depositPaid : {4}, checkin : {5}, checkOut :{6}, additionalNeeds :{7}")
    public ValidatableResponse updateBookingById(int bookingId, String firstName, String lastName, int totalPrice, boolean depositPaid, String checkin, String checkout, String additionalNeeds) {

        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstName, lastName, totalPrice, depositPaid, checkin, checkout, additionalNeeds);
        return SerenityRest.given()
                .pathParam("bookingID", bookingId)
                .auth().preemptive().basic("admin", "password123")
                .contentType(ContentType.JSON)
                .accept("application/json")
                .when()
                .body(bookingPojo)
                .put(EndPoints.UPDATE_SINGLE_BOOKING_BY_ID)
                .then();
    }


    @Step("Deleting booking information with bookingId :{0}")
    public ValidatableResponse deleteBookingById(int bookingId) {
        return SerenityRest.given()
                .pathParam("bookingID", bookingId)
                .auth().preemptive().basic("admin", "password123")
                .contentType(ContentType.JSON)
                .when()
                .delete(EndPoints.DELETE_SINGLE_BOOKING_BY_ID)
                .then();
    }

}
