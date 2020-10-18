package com.partner.oxostay.dtos;

public class BookingsDto {
    private String booking_Status, check_in_date, check_in_time, fcm_token, hotel_address, hotel_id, hotel_name, payment_type, rooms_booked, selected_room, total_amount, transaction_id, user_id, user_phnno, username, check_out_time, bookingsKey;

    public String getBookingsKey() {
        return bookingsKey;
    }

    public void setBookingsKey(String bookingsKey) {
        this.bookingsKey = bookingsKey;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getBooking_Status() {
        return booking_Status;
    }

    public void setBooking_Status(String booking_Status) {
        this.booking_Status = booking_Status;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getRooms_booked() {
        return rooms_booked;
    }

    public void setRooms_booked(String rooms_booked) {
        this.rooms_booked = rooms_booked;
    }

    public String getSelected_room() {
        return selected_room;
    }

    public void setSelected_room(String selected_room) {
        this.selected_room = selected_room;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_phnno() {
        return user_phnno;
    }

    public void setUser_phnno(String user_phnno) {
        this.user_phnno = user_phnno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
