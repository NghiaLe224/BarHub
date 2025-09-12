package com.mingles.web.controller;

import com.mingles.web.dto.booking.BookingRequest;
import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Place Booking (User)
//Edit Booking / Cancel Booking
//View Booking Status / History
//Staff: Confirm / Reject Booking
//Staff: Assign Table / Check-in / Mark as Seated
//Admin/Staff: View Incoming Bookings
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @RequestBody BookingRequest request
    ){
        ApiResponse<BookingResponse> res = bookingService.createBooking(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


}
