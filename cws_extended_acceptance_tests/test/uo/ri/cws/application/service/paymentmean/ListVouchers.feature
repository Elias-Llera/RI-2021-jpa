Feature: List vouchers
  As a Manager
  I want to generate vouchers for clients

  Scenario Outline: Client does not exist
    When I list vouchers by non existent client
    Then I get no vouchers

  Scenario Outline: Client does not have vouchers
    Given a client registered
    When I list vouchers for the client
    Then I get no vouchers

  Scenario Outline: Client has vouchers
    Given the following clients and vouchers
      | dni  | name  | surname  | code  | accumulated | balance |
      | dni1 | name1 | surname1 | code1 |          10 |      20 |
      | dni1 | name1 | surname1 | code2 |          15 |       0 |
      | dni1 | name1 | surname1 | code3 |           0 |      20 |
      | dni2 | name2 | surname2 | code4 |          10 |      20 |
      | dni2 | name2 | surname2 | code5 |          15 |       0 |
      | dni2 | name2 | surname2 | code6 |           0 |      20 |
    When I list vouchers for the first client
    Then I get the following vouchers
      | dni  | name  | surname  | code  | accumulated | balance |
      | dni1 | name1 | surname1 | code1 |          10 |      20 |
      | dni1 | name1 | surname1 | code2 |          15 |       0 |
      | dni1 | name1 | surname1 | code3 |           0 |      20 |
