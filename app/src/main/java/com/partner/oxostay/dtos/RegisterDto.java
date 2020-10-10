package com.partner.oxostay.dtos;

import java.util.ArrayList;

public class RegisterDto {
    private String fullName, phNumber, address, aadhaarCard, panCard, gstCert, hotel_name, hotel_desc, hotel_pictures, hotel_address, hotel_rating, hotel_email, hotel_secondary_email, manager_added, rooms_available, room_rate_3_hour, room_rate_6_hour, room_rate_12_hour, room_3h_first_checkin, room_3h_last_checkin, room_6h_first_checkin, room_6h_last_checkin, room_12h_first_checkin, room_12h_last_checkin, fcm_token, date_from, date_to, hotel_img_1, hotel_img_2, hotel_img_3, hotel_img_4, hotel_img_5, city_name;
    private boolean approvedOrNot;
    private AmenetiesDto amenetiesDto;
    private ArrayList<String> hotel_images;
    private ArrayList<String> amenities;


    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    public ArrayList<String> getHotel_images() {
        return hotel_images;
    }

    public void setHotel_images(ArrayList<String> hotel_images) {
        this.hotel_images = hotel_images;
    }

    public String getHotel_img_5() {
        return hotel_img_5;
    }

    public void setHotel_img_5(String hotel_img_5) {
        this.hotel_img_5 = hotel_img_5;
    }

    public String getHotel_img_1() {
        return hotel_img_1;
    }

    public void setHotel_img_1(String hotel_img_1) {
        this.hotel_img_1 = hotel_img_1;
    }

    public String getHotel_img_2() {
        return hotel_img_2;
    }

    public void setHotel_img_2(String hotel_img_2) {
        this.hotel_img_2 = hotel_img_2;
    }

    public String getHotel_img_3() {
        return hotel_img_3;
    }

    public void setHotel_img_3(String hotel_img_3) {
        this.hotel_img_3 = hotel_img_3;
    }

    public String getHotel_img_4() {
        return hotel_img_4;
    }

    public void setHotel_img_4(String hotel_img_4) {
        this.hotel_img_4 = hotel_img_4;
    }

    public AmenetiesDto getAmenetiesDto() {
        return amenetiesDto;
    }

    public void setAmenetiesDto(AmenetiesDto amenetiesDto) {
        this.amenetiesDto = amenetiesDto;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_desc() {
        return hotel_desc;
    }

    public void setHotel_desc(String hotel_desc) {
        this.hotel_desc = hotel_desc;
    }

    public String getHotel_pictures() {
        return hotel_pictures;
    }

    public void setHotel_pictures(String hotel_pictures) {
        this.hotel_pictures = hotel_pictures;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_rating() {
        return hotel_rating;
    }

    public void setHotel_rating(String hotel_rating) {
        this.hotel_rating = hotel_rating;
    }

    public String getHotel_email() {
        return hotel_email;
    }

    public void setHotel_email(String hotel_email) {
        this.hotel_email = hotel_email;
    }

    public String getHotel_secondary_email() {
        return hotel_secondary_email;
    }

    public void setHotel_secondary_email(String hotel_secondary_email) {
        this.hotel_secondary_email = hotel_secondary_email;
    }

    public String getManager_added() {
        return manager_added;
    }

    public void setManager_added(String manager_added) {
        this.manager_added = manager_added;
    }

    public String getRooms_available() {
        return rooms_available;
    }

    public void setRooms_available(String rooms_available) {
        this.rooms_available = rooms_available;
    }

    public String getRoom_rate_3_hour() {
        return room_rate_3_hour;
    }

    public void setRoom_rate_3_hour(String room_rate_3_hour) {
        this.room_rate_3_hour = room_rate_3_hour;
    }

    public String getRoom_rate_6_hour() {
        return room_rate_6_hour;
    }

    public void setRoom_rate_6_hour(String room_rate_6_hour) {
        this.room_rate_6_hour = room_rate_6_hour;
    }

    public String getRoom_rate_12_hour() {
        return room_rate_12_hour;
    }

    public void setRoom_rate_12_hour(String room_rate_12_hour) {
        this.room_rate_12_hour = room_rate_12_hour;
    }

    public String getRoom_3h_first_checkin() {
        return room_3h_first_checkin;
    }

    public void setRoom_3h_first_checkin(String room_3h_first_checkin) {
        this.room_3h_first_checkin = room_3h_first_checkin;
    }

    public String getRoom_3h_last_checkin() {
        return room_3h_last_checkin;
    }

    public void setRoom_3h_last_checkin(String room_3h_last_checkin) {
        this.room_3h_last_checkin = room_3h_last_checkin;
    }

    public String getRoom_6h_first_checkin() {
        return room_6h_first_checkin;
    }

    public void setRoom_6h_first_checkin(String room_6h_first_checkin) {
        this.room_6h_first_checkin = room_6h_first_checkin;
    }

    public String getRoom_6h_last_checkin() {
        return room_6h_last_checkin;
    }

    public void setRoom_6h_last_checkin(String room_6h_last_checkin) {
        this.room_6h_last_checkin = room_6h_last_checkin;
    }

    public String getRoom_12h_first_checkin() {
        return room_12h_first_checkin;
    }

    public void setRoom_12h_first_checkin(String room_12h_first_checkin) {
        this.room_12h_first_checkin = room_12h_first_checkin;
    }

    public String getRoom_12h_last_checkin() {
        return room_12h_last_checkin;
    }

    public void setRoom_12h_last_checkin(String room_12h_last_checkin) {
        this.room_12h_last_checkin = room_12h_last_checkin;
    }

    public boolean getApprovedOrNot() {
        return approvedOrNot;
    }

    public void setApprovedOrNot(boolean approvedOrNot) {
        this.approvedOrNot = approvedOrNot;
    }

    public String getAadhaarCard() {
        return aadhaarCard;
    }

    public void setAadhaarCard(String aadhaarCard) {
        this.aadhaarCard = aadhaarCard;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getGstCert() {
        return gstCert;
    }

    public void setGstCert(String gstCert) {
        this.gstCert = gstCert;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
