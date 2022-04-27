Feature: Delete a client
  As a Manager
  I want to delete a client
  in case have not been used to keep the system clean

  Scenario: Delete an existing unused client
    Given a client registered
    When I remove the client
    Then the client no longer exists
    And there are no payment mean for this client

  Scenario: Try to remove a non existing client
    When I try to remove a non existent client
    Then an error happens with an explaining message

  Scenario: Try to remove a client with vehicles
    Given a client registered
    And vehicles under client identity
    When I try to remove the client
    Then an error happens with an explaining message

  Scenario Outline: Try to delete a client with null argument
    When I try to remove a client with null argument
    Then argument is rejected with an explaining message

  Scenario Outline: Try to delete a client with wrong fields
    When I try to delete a client with <id>
    Then argument is rejected with an explaining message

    Examples: 
      | id    |
      | "   " |
      | ""    |
