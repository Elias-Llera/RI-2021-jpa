Feature: Find all clients
  As a Manager
  I need to know data about all clients

  Scenario Outline: Find all existing client when there are no clients yet
    When we read all clients
    Then we get an empty list of clients

  Scenario Outline: Find all existing client
    Given the following relation of clients with data
      | dni  | name  | surname  |
      | DNI1 | Name1 | Surname1 |
      | DNI2 | Name2 | Surname2 |
      | DNI3 | Name3 | Surname3 |
    When we read all clients
    Then we get the following <client>

    Examples: 
      | client                |
      | "DNI1,Name1,Surname1" |
      | "DNI2,Name2,Surname2" |
      | "DNI3,Name3,Surname3" |
