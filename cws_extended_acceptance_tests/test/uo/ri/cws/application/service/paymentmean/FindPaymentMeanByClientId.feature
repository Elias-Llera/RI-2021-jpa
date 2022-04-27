Feature: Find a payment mean by client id
  As a Manager
  I need to recover payment mean by client id
  To see its details

  Scenario Outline: Find payment means from a fresh new client
    Given a client registered
    When I look for payment mean by client id
    Then I get the cash

  Scenario Outline: Find payment means from a client with cash and credit card
    Given a client registered
    And a credit card registered
    When I look for payment mean by client id
    Then I get the cash
    And I get credit cards registered

  Scenario Outline: Find payment means from a client with cash and voucher
    Given a client registered
    And a voucher registered
    When I look for payment mean by client id
    Then I get the cash
    And I get vouchers registered

  Scenario Outline: Find payment means from a client with cash and several credit cards and vouchers
    Given a client registered
    And a voucher registered
    And a credit card registered
    When I look for payment mean by client id
    And I get vouchers registered
    And I get credit cards registered
    And I get the cash

  Scenario Outline: Find payment means from a non existing client
    When I look payment means for a non existing client id
    Then payment means not found

  Scenario Outline: Try to find payment means  with null argument
    When I try to find payment means with null client id
    Then argument is rejected with an explaining message

  Scenario Outline: Try to find payment means  with wrong parameters
    When I look for payment means  by wrong client id <client>
    Then argument is rejected with an explaining message

    Examples: 
      | client      |
      | ""          |
      | "         " |
