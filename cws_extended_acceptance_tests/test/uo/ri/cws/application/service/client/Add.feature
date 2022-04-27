Feature: Add a client
  As a foreman
  I want to register a client

  Scenario: Add a non existing client
    Given a client
    When I add client
    Then the client results added to the system
    And cash is created for the client

  Scenario: Try to add a client with a repeated dni
    Given a client registered
    And a client
    And the second has the same dni as the first
    When I try to add the client
    Then an error happens with an explaining message

  Scenario Outline: Try to add a client with null argument
    When I try to add a new client with null argument
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a client with null dni
    When I try to add a new client with null dni
    Then argument is rejected with an explaining message

  Scenario Outline: Try to add a client with invalid dni
    When I try to add a new client with <dni>
    Then argument is rejected with an explaining message

    Examples: 
      | dni   | name   | surname   |
      | ""    | "Name" | "surname" |
      | "   " | "Name" | "surname" |
