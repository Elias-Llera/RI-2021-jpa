Feature: Find a client by id
  As a Manager
  I need to recover a client by id
  To see its details

  Scenario Outline: Find an existing client
    Given a client registered
    When I look for client
    Then I get client

  Scenario: Try to find a client with non existing id
    When I try to find a non existent client
    Then client not found

  Scenario Outline: Try to find a client with null argument
    When I try to find a client with null argument
    Then argument is rejected with an explaining message

  Scenario Outline: Try to find a client with wrong parameters
    When I look for a client by wrong id <id>
    Then argument is rejected with an explaining message

    Examples: 
      | id          |
      | ""          |
      | "         " |
