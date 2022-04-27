Feature: Update a client
  As a Manager
  I want to update a client

  Scenario: Update an existing client
    Given a client registered
    When I update the client
    Then the client results updated

  Scenario: Try to update an non existing client
    When I try to update a non existing client
    Then an error happens with an explaining message

  Scenario Outline: Try to update a client with null id
    When I try to update a client with null id
    Then argument is rejected with an explaining message

  Scenario Outline: Try to update client with wrong parameters
    Given a client registered
    When I try to update the client with wrong <id>
    Then argument is rejected with an explaining message

    Examples: 
      | id   |
      | ""   |
      | "  " |
