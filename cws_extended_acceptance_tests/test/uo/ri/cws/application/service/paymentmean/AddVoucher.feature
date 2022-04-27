Feature: Add voucher payment mean
  As a Manager
  I want to register payment means for clients 
  Because they will have to pay

  Scenario: Add a new voucher for a client
    Given a client registered
    And a voucher
    When I add the voucher
    Then the voucher results added to the system

  Scenario: Try to add a new voucher for a non existent client
    Given a voucher
    When I try to add the voucher to a non existent client
    Then an error happens with an explaining message

  Scenario: Try to add a repeated voucher code
    Given a client registered
    And a voucher
    When I add the voucher
    And I try to add another voucher with the same code
    Then an error happens with an explaining message

  Scenario Outline: Try to add a voucher with null argument
    When I try to add a new voucher with null argument
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a voucher with null code
    Given a client registered
    When I try to add a new voucher with null code
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a voucher with null client id
    When I try to add a new voucher with null client id
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a voucher with invalid argument
    Given a client registered
    When I try to add a new voucher with <id>, <code>, <description>, <clientid>, <balance>
    Then argument is rejected with an explaining message

    Examples: 
      | id   | code     | description   | clientid | balance |
      | "id" | ""       | "description" | "client" |    10.0 |
      | "id" | "      " | "description" | "client" |    10.0 |
      | "id" | "code"   | ""            | "client" |    10.0 |
      | "id" | "code"   | "    "        | "client" |    10.0 |
      | "id" | "code"   | "description" | ""       |    10.0 |
      | "id" | "code"   | "description" | "      " |    10.0 |
      | "id" | "code"   | "description" | "client" |   -10.0 |
