Feature: Add credit card payment mean
  As a Manager
  I want to register payment means for clients 
  Because they will have to pay

  Scenario: Add a new credit card for a client
    Given a client registered
    And a credit card
    When I add the credit card
    Then the credit card results added to the system

  Scenario: Try to add a new credit card for a non existent client
    Given a credit card
    When I try to add the credit card to a non existent client
    Then an error happens with an explaining message

  Scenario: Try to add a repeated credit card number
    Given a client registered
    And a credit card
    When I add the credit card
    And I try to add another credit card with the same number
    Then an error happens with an explaining message

  Scenario: Try to add an expired credit card
    Given a client registered
    And an outdated credit card
    When I try to add the credit card to the client
    Then an error happens with an explaining message

  Scenario Outline: Try to add a credit card with null argument
    When I try to add a new credit card with null argument
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a credit card with null card number
    Given a client registered
    When I try to add a new credit card with null number
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a credit card with null card type
    Given a client registered
    When I try to add a new credit card with null type
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a credit card with null client id
    When I try to add a new credit card with null client id
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a credit card with null expiration date
    Given a client registered
    When I try to add a new credit card with null expiration date
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a credit card with invalid argument
    Given a client registered
    When I try to add a new credit card with <id>, <number>, <type>, <clientid>
    Then argument is rejected with an explaining message

    Examples: 
      | id   | number   | type   | clientid |
      | "id" | ""       | "visa" | "client" |
      | "id" | "      " | "visa" | "client" |
      | "id" | "number" | ""     | "client" |
      | "id" | "number" | "    " | "client" |
      | "id" | "number" | "visa" | ""       |
      | "id" | "number" | "visa" | "      " |
