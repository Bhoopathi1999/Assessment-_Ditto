# MakeMyTrip Flight Booking - Manual Test Cases

**Application:** MakeMyTrip (https://www.makemytrip.com/)
**Module:** Flight Booking
**Prepared By:** QA Team
**Date:** March 2026

---

## 1. POSITIVE TEST SCENARIOS

| Test Case ID | Test Scenario | Preconditions | Test Steps | Expected Result | Priority |
|---|---|---|---|---|---|
| TC_FS_001 | Search one-way domestic flight | User is on MakeMyTrip homepage | 1. Select "Flights" tab 2. Choose "One Way" 3. Enter From: "Delhi" 4. Enter To: "Mumbai" 5. Select tomorrow's date 6. Select 1 Adult, Economy class 7. Click "Search" | Flight search results page displays available flights from DEL to BOM with prices | High |
| TC_FS_002 | Search round-trip domestic flight | User is on Flights tab | 1. Select "Round Trip" 2. Enter From: "Bangalore" To: "Chennai" 3. Select departure date (next week) 4. Select return date (next week + 3 days) 5. Select 2 Adults, Economy 6. Click "Search" | Results show outbound and return flight options with combined pricing | High |
| TC_FS_003 | Search multi-city flight | User is on Flights tab | 1. Select "Multi City" 2. Segment 1: DEL to BOM, Date 1 3. Segment 2: BOM to GOI, Date 2 4. Click "Search" | Results display flights for both segments with total pricing | Medium |
| TC_FS_004 | Search international flight | User is on Flights tab | 1. Select "One Way" 2. Enter From: "Delhi" To: "Dubai" 3. Select a future date 4. Select 1 Adult, Economy 5. Click "Search" | International flight results displayed with currency and duration | High |
| TC_FS_005 | Search flights in Business class | User is on Flights tab | 1. Select "One Way" 2. Enter From: "Mumbai" To: "Delhi" 3. Select future date 4. Select 1 Adult, Business class 5. Click "Search" | Only Business class flights are shown in results | Medium |
| TC_DD_001 | Select city from auto-suggest dropdown | User is on Flights tab | 1. Click "From" field 2. Type "Ban" 3. Wait for auto-suggest dropdown 4. Select "Bangalore" from suggestions | "Bangalore (BLR)" is populated in the From field | High |
| TC_DD_002 | Select city using airport code | User is on Flights tab | 1. Click "From" field 2. Type "BLR" 3. Select from dropdown | "Bangalore (BLR)" is populated correctly | Medium |
| TC_DD_003 | Swap source and destination | From: Delhi, To: Mumbai are selected | 1. Click the swap icon between From and To fields | From changes to "Mumbai" and To changes to "Delhi" | Medium |
| TC_DD_004 | Select from popular/recent cities | User is on Flights tab | 1. Click "From" field 2. Select a city from "Popular Cities" section | Selected city is populated in the From field | Low |
| TC_DT_001 | Select departure date from calendar | User is on Flights tab | 1. Click on the departure date field 2. Navigate to desired month 3. Click on a future date | Selected date is populated in the departure date field | High |
| TC_DT_002 | Select return date for round-trip | Round Trip selected, departure date chosen | 1. Click return date field 2. Select a date after departure date | Return date is populated and is after departure date | High |
| TC_DT_003 | Navigate calendar months and select date | Date picker is open | 1. Click next month arrow 3 times 2. Select a date in the future month | Date 3 months from now is selected correctly | Medium |
| TC_DT_004 | Select lowest fare date from fare calendar | User is on Flights tab | 1. Enable "Fare Calendar" view 2. Browse dates to find lowest fare 3. Select the lowest fare date | Lowest fare date is selected and search results reflect that date | Low |
| TC_PD_001 | Fill passenger details for 1 adult | User has selected a flight and is on traveller details page | 1. Enter First Name: "Rahul" 2. Enter Last Name: "Kumar" 3. Select Gender: Male 4. Enter Email 5. Enter Mobile Number | All fields accept valid input without errors | High |
| TC_PD_002 | Add multiple passengers (2 adults + 1 child) | Search initiated for 2 Adults + 1 Child | 1. Fill details for Adult 1 2. Fill details for Adult 2 3. Fill details for Child (age 5) 4. Enter contact details | All passenger forms are filled and validated successfully | High |
| TC_PD_003 | Fill GST details for business booking | User is on traveller details page | 1. Expand GST section 2. Enter Company Name 3. Enter GSTIN 4. Enter Company Email | GST details accepted and reflected in invoice section | Low |
| TC_SS_001 | Select an available seat from seat map | User is on seat selection page | 1. View seat map 2. Click an available (green/unoccupied) seat 3. Confirm selection | Seat is highlighted as selected and seat number is shown in booking summary | Medium |
| TC_SS_002 | Select window seat | User is on seat selection page | 1. Identify a window seat (column A or F) 2. Click on it | Window seat selected and reflected in summary | Low |
| TC_SS_003 | Skip seat selection | User is on seat selection page | 1. Click "Skip" or "Continue without seat selection" | User proceeds to next step without seat assignment | Medium |
| TC_AO_001 | Add meal preference | User is on add-ons page | 1. Browse meal options 2. Select a meal (e.g., Veg Meal) 3. Confirm | Meal added to booking, price updated in total | Low |
| TC_AO_002 | Add extra baggage | User is on add-ons page | 1. Select extra baggage option (e.g., +15 kg) 2. Confirm | Extra baggage added, total price updated accordingly | Low |
| TC_AO_003 | Add travel insurance | User is on add-ons page | 1. Select travel insurance add-on 2. Review coverage details 3. Confirm | Insurance premium added to total booking cost | Low |
| TC_AO_004 | Add multiple add-ons and verify total | User is on add-ons page | 1. Add meal 2. Add extra baggage 3. Add travel insurance 4. Verify total | Total price reflects sum of base fare + all add-ons | Medium |
| TC_PM_001 | Complete payment using credit card | User is on payment page with valid booking | 1. Select "Credit Card" 2. Enter valid card number 3. Enter expiry date 4. Enter CVV 5. Click "Pay" | Payment processed successfully, redirected to confirmation | High |
| TC_PM_002 | Complete payment using debit card | User is on payment page | 1. Select "Debit Card" 2. Enter valid card details 3. Complete OTP verification 4. Click "Pay" | Payment successful after OTP verification | High |
| TC_PM_003 | Complete payment using UPI | User is on payment page | 1. Select "UPI" 2. Enter valid UPI ID (e.g., user@upi) 3. Approve payment on UPI app | Payment completed via UPI, booking confirmed | High |
| TC_PM_004 | Complete payment using net banking | User is on payment page | 1. Select "Net Banking" 2. Choose bank 3. Login to bank portal 4. Authorize payment | Payment successful via net banking redirect | Medium |
| TC_PM_005 | Apply valid promo code | User is on payment page | 1. Enter a valid promo code in coupon field 2. Click "Apply" | Discount applied, total amount reduced, discount shown | Medium |
| TC_BC_001 | Verify booking confirmation page | Payment completed successfully | 1. Observe the confirmation page after payment | Booking ID/PNR displayed, flight details match selection, passenger names correct | High |
| TC_BC_002 | Verify confirmation email | Payment completed, valid email provided | 1. Check email inbox for confirmation | Email received with PNR, flight details, e-ticket attachment | High |
| TC_BC_003 | Download e-ticket | Booking confirmed, on confirmation page | 1. Click "Download E-Ticket" or "Print" option | E-ticket PDF downloaded with correct flight and passenger details | Medium |

---

## 2. NEGATIVE TEST SCENARIOS

| Test Case ID | Test Scenario | Preconditions | Test Steps | Expected Result | Priority |
|---|---|---|---|---|---|
| TC_FS_N001 | Search with same source and destination | User is on Flights tab | 1. Enter From: "Delhi" 2. Enter To: "Delhi" 3. Click "Search" | Error message displayed: source and destination cannot be the same | High |
| TC_FS_N002 | Search with past departure date | User is on Flights tab | 1. Attempt to select a past date in the calendar | Past dates should be grayed out / non-clickable | High |
| TC_FS_N003 | Round-trip: return date before departure | User is on Flights tab, Round Trip selected | 1. Select departure: March 25 2. Attempt to select return: March 20 | Return date selection should not allow dates before departure date | High |
| TC_FS_N004 | Search without selecting source city | User is on Flights tab | 1. Leave "From" field empty 2. Fill "To" and date 3. Click "Search" | Validation error: "Please enter departure city" or field highlighted | High |
| TC_FS_N005 | Search without selecting destination | User is on Flights tab | 1. Fill "From" field 2. Leave "To" empty 3. Click "Search" | Validation error prompting user to enter destination | High |
| TC_FS_N006 | Search with 0 passengers | User is on Flights tab | 1. Try to reduce adults count to 0 | System should not allow 0 passengers; minimum is 1 adult | Medium |
| TC_FS_N007 | Exceed maximum passenger limit (9+) | User is on Flights tab | 1. Try to add more than 9 total passengers | System shows maximum limit message (9 passengers max) | Medium |
| TC_DD_N001 | Enter non-existent city name | User is on Flights tab | 1. Click "From" field 2. Type "XYZABC123" | Auto-suggest shows "No results found" or no suggestions appear | Medium |
| TC_DD_N002 | Enter special characters in city field | User is on Flights tab | 1. Type "@#$%^&" in From field | No suggestions shown; special characters not accepted or ignored | Low |
| TC_DT_N001 | Select date beyond max booking window | Date picker is open | 1. Navigate calendar beyond 365 days 2. Try to select a date | Dates beyond maximum advance booking period should be disabled | Low |
| TC_PD_N001 | Submit empty passenger form | User is on traveller details page | 1. Leave all mandatory fields empty 2. Click "Continue" | Validation errors shown for all mandatory fields (name, email, phone) | High |
| TC_PD_N002 | Enter numbers in name field | User is on traveller details page | 1. Enter "12345" in First Name field | Error: "Please enter a valid name" or field rejects numeric input | Medium |
| TC_PD_N003 | Enter special characters in name | User is on traveller details page | 1. Enter "@#$%&" in First Name | Error message or input rejected | Medium |
| TC_PD_N004 | Enter invalid email format | User is on traveller details page | 1. Enter "abc@" in email field 2. Click Continue | Validation error: "Please enter a valid email address" | High |
| TC_PD_N005 | Enter phone with less than 10 digits | User is on traveller details page | 1. Enter "98765" in mobile field | Validation error: "Please enter a valid 10-digit mobile number" | High |
| TC_PD_N006 | Add infant without an adult | Search for 0 adults + 1 infant (if possible) | 1. Try to set adults to 0 and infants to 1 | System prevents this: infants require at least one adult | Medium |
| TC_PD_N007 | Child age exceeds limit (12+) | User adding child passenger | 1. Enter DOB that makes child older than 11 years | System should reclassify as adult or show validation error | Low |
| TC_SS_N001 | Select an occupied/blocked seat | User is on seat selection page | 1. Click on a grayed-out or occupied seat | Seat should not be selectable; no action or tooltip "Seat unavailable" | Medium |
| TC_SS_N002 | Emergency exit seat for child | User is on seat selection page, child passenger present | 1. Try to assign emergency exit row seat to child passenger | System prevents assignment with message about age restriction | Low |
| TC_AO_N001 | Add then remove an add-on | User added meal add-on | 1. Add Veg Meal (price increases) 2. Remove Veg Meal | Price reverts to original amount without the add-on | Medium |
| TC_PM_N001 | Enter expired credit card | User is on payment page | 1. Select Credit Card 2. Enter card with past expiry date 3. Click Pay | Error: "Card expired" or "Invalid expiry date" | High |
| TC_PM_N002 | Enter invalid CVV (2 digits) | User is on payment page | 1. Enter card number 2. Enter "12" as CVV | Validation error: CVV must be 3 digits | Medium |
| TC_PM_N003 | Enter invalid card number | User is on payment page | 1. Enter "1234567890123456" (fails Luhn) 2. Click Pay | Error: "Invalid card number" | High |
| TC_PM_N004 | Apply expired promo code | User is on payment page | 1. Enter expired promo code 2. Click Apply | Error: "Promo code expired" or "Invalid promo code" | Medium |
| TC_PM_N005 | Payment gateway timeout | User is on payment page | 1. Initiate payment 2. Simulate network delay/timeout | Error page with retry option; no booking created | Medium |
| TC_BC_N001 | Browser back after successful booking | Booking confirmed successfully | 1. Click browser Back button on confirmation page | Should not create a duplicate booking; shows original confirmation or error | Medium |
| TC_BC_N002 | Payment failure - no booking created | User on payment page | 1. Initiate payment 2. Payment fails (insufficient funds) | User redirected to retry page; no booking or PNR generated | High |

---

## 3. END-TO-END TEST SCENARIOS

| Test Case ID | Test Scenario | Preconditions | Test Steps | Expected Result | Priority |
|---|---|---|---|---|---|
| TC_E2E_001 | Book one-way domestic Economy flight for 1 adult | User has MakeMyTrip account, valid payment method | 1. Login to MakeMyTrip 2. Select Flights > One Way 3. From: Delhi, To: Mumbai 4. Select tomorrow's date 5. 1 Adult, Economy 6. Search flights 7. Select cheapest flight 8. Fill passenger details (Name, Email, Phone) 9. Skip seat selection 10. Skip add-ons 11. Select UPI payment 12. Complete payment 13. Verify confirmation page | Booking confirmed with PNR, flight details match, confirmation email received, e-ticket available | High |
| TC_E2E_002 | Book round-trip international Business class for 2 adults + 1 child with add-ons | Valid account, international travel docs | 1. Login 2. Flights > Round Trip 3. From: Mumbai, To: London 4. Departure: next month, Return: +7 days 5. 2 Adults + 1 Child, Business class 6. Search flights 7. Select outbound flight 8. Select return flight 9. Fill details for all 3 passengers 10. Select seats for all passengers on both legs 11. Add meals for all passengers 12. Add extra baggage (25 kg) 13. Pay by Credit Card 14. Verify confirmation | All segments booked, 3 passengers confirmed, seats assigned, add-ons reflected, total price correct, PNR generated | High |
| TC_E2E_003 | Book with promo code and verify discount | Valid promo code available | 1. Search one-way flight DEL to BLR 2. Select a flight 3. Fill traveller details 4. On payment page, enter promo code 5. Verify discount applied 6. Complete payment 7. Verify confirmation and discounted amount | Discount applied correctly, final amount = original - discount, confirmation shows discounted price | Medium |
| TC_E2E_004 | Multi-city booking with seat selection and insurance | Valid account | 1. Select Multi City 2. Segment 1: DEL to BOM (Date 1) 3. Segment 2: BOM to GOI (Date 2) 4. 3 Adults, Economy 5. Search flights 6. Select flights for both segments 7. Fill details for 3 adults 8. Select seats on all segments 9. Add travel insurance 10. Complete payment 11. Verify confirmation | Multi-city booking confirmed with correct segments, all passengers, seats, and insurance reflected | Medium |
| TC_E2E_005 | Book and cancel with refund request | Completed booking exists | 1. Complete a full booking (one-way, 1 adult) 2. Go to "My Trips" section 3. Find the booking by PNR 4. Click "Cancel Booking" 5. Select reason for cancellation 6. Confirm cancellation 7. Verify refund initiation | Booking cancelled, cancellation confirmation shown, refund initiated to original payment method, cancellation email received | High |

---

## Summary

| Category | Count |
|---|---|
| Positive Test Scenarios | 30 |
| Negative Test Scenarios | 27 |
| End-to-End Test Scenarios | 5 |
| **Total Test Cases** | **62** |
