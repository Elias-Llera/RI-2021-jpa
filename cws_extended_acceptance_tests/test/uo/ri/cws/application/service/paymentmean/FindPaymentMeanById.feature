Feature: Find a payment mean by id
  As a Manager
  I need to recover a payment mean by id
  To see its details

  Scenario Outline: Find an existing credit card
    Given a client registered
    And a credit card registered
    When I search payment mean by id
    Then I get the credit card

  Scenario Outline: Find an existing voucher
    Given a client registered
    And a voucher registered
    When I search payment mean by id
    Then I get the voucher

  Scenario: Find a payment mean with non existing id
    When I search a non existent payment mean
    Then payment mean not found

  Scenario Outline: Try to find a payment mean with null argument
    When I try to find a payment mean with null argument
    Then argument is rejected with an explaining message

  Scenario Outline: Try to find a payment mean with wrong parameters
    When I look for a payment mean by wrong id <id>
    Then argument is rejected with an explaining message

    Examples: 
      | id          |
      | ""          |
      | "         " |
