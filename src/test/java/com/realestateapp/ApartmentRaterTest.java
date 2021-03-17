package com.realestateapp;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {

    @Nested
    class RateApartmentTests {
        @Test
        void itShould_ReturnMinusOne_WhenAreaIsZero() {
            // Given
            Apartment apartment = new Apartment(0.0, new BigDecimal("5000.0"));
            int expected = -1;
            // When
            int actual = ApartmentRater.rateApartment(apartment);
            // Then
            assertEquals(expected, actual);
        }

        @Test
        void itShould_ReturnZeroWhen_RatioLessThanCheapThreshold() {
            // Given
            Apartment apartment = new Apartment(300.0, new BigDecimal("200000.0"));
            BigDecimal ratio = apartment.getPrice().divide(BigDecimal.valueOf(apartment.getArea()), RoundingMode.HALF_UP);

            // When
            int actual = ApartmentRater.rateApartment(apartment);

            // Then
            assertEquals(0, actual);
        }

        @Test
        void itShould_ReturnOneWhen_RatioGreaterEqualThanCheapestThreshold_ButLessThanMax() {
            // Given
            Apartment apartment = new Apartment(300.0, new BigDecimal("2099900.0"));
            BigDecimal ratio = apartment.getPrice().divide(BigDecimal.valueOf(apartment.getArea()), RoundingMode.HALF_UP);
            // When
            int actual = ApartmentRater.rateApartment(apartment);
            System.out.println(actual);
            // Then
            assertEquals(1, actual);
        }

        @Test
        void itShould_ReturnTwoWhen_RatioGreaterEqual_ThanMaxThreshold() {
            // Given
            Apartment apartment = new Apartment(300.0, new BigDecimal("20000000.0"));
            BigDecimal ratio = apartment.getPrice().divide(BigDecimal.valueOf(apartment.getArea()), RoundingMode.HALF_UP);
            // When
            int actual = ApartmentRater.rateApartment(apartment);
            System.out.println(actual);
            // Then
            assertEquals(2, actual);
        }
    }

    @Nested
    class CalculateAverageRatingTests {
        @Test
        void itShould_ThrowExceptionWhen_ListIsEmpty() {
            // Given
            List<Apartment> apartments = Collections.emptyList();
            // When
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);
            // Then
            assertThrows(RuntimeException.class, executable);
        }

        @Test
        void itShould_ReturnCorrectAverageWhen_ListNotEmpty() {
            // Given
            List<Apartment> apartments = List.of(
                    new Apartment(300.0, new BigDecimal("20000000.0")),
                    new Apartment(300.0, new BigDecimal("20000000.0")),
                    new Apartment(300.0, new BigDecimal("20000000.0"))
            );
            // When
            Double actual = ApartmentRater.calculateAverageRating(apartments);
            System.out.println(actual);
            // Then
            assertEquals(2.0, actual);
        }
    }
}