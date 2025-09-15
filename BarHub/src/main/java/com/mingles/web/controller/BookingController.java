package com.mingles.web.controller;

import com.mingles.web.dto.booking.BookingRequest;
import com.mingles.web.dto.booking.BookingResponse;
import com.mingles.web.dto.booking.UpdateBookingStatusRequest;
import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request
    ){
        ApiResponse<BookingResponse> res = bookingService.createBooking(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/staffs/bookings/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> confirmBooking(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request
    ){
        ApiResponse<BookingResponse> response = bookingService.confirmBooking(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<?>> getLogs(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getAuditLogs(id));
    }


}
