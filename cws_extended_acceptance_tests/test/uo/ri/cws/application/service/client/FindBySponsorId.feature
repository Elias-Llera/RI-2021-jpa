Feature: Find a client by sponsor
  As a Manager
  I need to recover a client by dni
  To see its details

  Scenario Outline: Find an existing client
    Given a client registered
    And an sponsor registered
    And a recommendation registered
    When I look for clients recommended by sponsor id
    Then client is found

  Scenario: Find clients recommended by a non existing sponsor id
    When I find clients recommended by a non existent sponsor id
    Then clients not found

  Scenario Outline: Try to find a client with null sponsor id
    When I try to find clients recommended by null sponsor id
    Then argument is rejected with an explaining message

  Scenario Outline: Try to find client recommended by wrong sponsor id
    When I look for a client recommended by wrong sponsor id <sponsorId>
    Then argument is rejected with an explaining message

    Examples: 
      | sponsorId   |
      | ""          |
      | "         " |
