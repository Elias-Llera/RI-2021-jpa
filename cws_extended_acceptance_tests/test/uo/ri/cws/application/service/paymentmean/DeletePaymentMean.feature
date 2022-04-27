Feature: Delete a paymente mean
  As ???
  I want to delete a payment mean

  Scenario: Delete a credit card
    Given a client registered
    And a credit card registered
    When I remove the credit card
    Then the credit card no longer exists

  Scenario: Try to remove a non existing payment mean
    When I try to remove a non existent payment mean
    Then an error happens with an explaining message

  Scenario: Try to remove a cash payment mean
    Given a client registered
    When I try to remove the cash payment mean
    Then an error happens with an explaining message

  Scenario Outline: Try to delete a credit card with charges
    Given a client registered
    And a vehicle
    And a list of several finished workorder ids
    And an invoice
    And a credit card registered
    And some charges to the credit card
    When I try to remove the card
    Then an error happens with an explaining message

  Scenario Outline: Try to delete a voucher with charges
    Given a client registered
    And a vehicle
    And a list of several finished workorder ids
    And an invoice
    And a voucher registered
    And some charges to the voucher
    When I try to remove the voucher
    Then an error happens with an explaining message

  Scenario Outline: Try to delete a null payment mean
    When I try to delete a null payment mean
    Then argument is rejected with an explaining message

  Scenario Outline: Try to delete a mechanic with wrong fields
    When I try to delete a mechanic with <id>
    Then argument is rejected with an explaining message

    Examples: 
      | id    |
      | "   " |
      | ""    |
