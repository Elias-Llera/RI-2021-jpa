Feature: List voucher summary
  As a Manager
  I want to generate vouchers for clients

  Scenario Outline: No clients
    When I list voucher summary
    Then I get no result

  Scenario Outline: Client does not have vouchers
    Given a client registered
    When I list voucher summary
    Then I get no result

  Scenario Outline: Clients with vouchers
    Given the following clients and vouchers
      | dni  | name  | surname  | code  | accumulated | balance |
      | dni1 | name1 | surname1 | code1 |          10 |      20 |
      | dni1 | name1 | surname1 | code2 |          15 |       0 |
      | dni1 | name1 | surname1 | code3 |           0 |      20 |
      | dni2 | name2 | surname2 | code4 |          10 |      20 |
      | dni2 | name2 | surname2 | code5 |          15 |       0 |
      | dni2 | name2 | surname2 | code6 |           0 |      20 |
    When I list voucher summary
    Then I get the following result
      | dni  | name  | surname  | issued | accumulated | balance | total |
      | dni1 | name1 | surname1 |      3 |          25 |      40 |    65 |
      | dni2 | name2 | surname2 |      3 |          25 |      40 |    65 |
