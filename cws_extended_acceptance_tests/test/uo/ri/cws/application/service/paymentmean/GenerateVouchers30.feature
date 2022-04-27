Feature: Generate vouchers 30
  As a Manager
  I want to generate vouchers for clients

  Scenario Outline: Voucher30
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |  1500 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description         | balance | accumulated |
      |   1 | CLIENT1 | By invoice over 500 |      30 |           0 |

  Scenario Outline: Voucher30 twice in a row
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |  1500 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
    When I generate vouchers
    And I generate vouchers again
    Then We get no vouchers

  Scenario Outline: Voucher30 invoices no more than 500
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2020-09-2 | false |    50 | 21.0 |
      |      2 | PAID   | 2020-10-2 | false |    50 | 21.0 |
      |      3 | PAID   | 2020-11-2 | false |    50 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-01-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-02-01 14:37:44 | false |             2 |
      | vclient1 | INVOICED | 2021-03-01 14:37:44 | false |             3 |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher30 invoices none more than 500 but sum is more
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2020-09-2 | false |   150 | 21.0 |
      |      2 | PAID   | 2020-10-2 | false |   150 | 21.0 |
      |      3 | PAID   | 2020-11-2 | false |   250 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-01-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-02-01 14:37:44 | false |             2 |
      | vclient1 | INVOICED | 2021-03-01 14:37:44 | false |             3 |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher30 invoices more than 500 are USED
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2020-09-2 | true  |   550 | 21.0 |
      |      2 | PAID   | 2020-10-2 | true  |  1150 | 21.0 |
      |      3 | PAID   | 2020-11-2 | false |   250 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-01-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-02-01 14:37:44 | false |             2 |
      | vclient1 | INVOICED | 2021-03-01 14:37:44 | false |             3 |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher30 several invoices over 500
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient11    |
      | CLIENT1   | vclient12    |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2020-09-2 | false |   550 | 21.0 |
      |      2 | PAID   | 2020-10-2 | false |  1150 | 21.0 |
      |      3 | PAID   | 2020-11-2 | false |   250 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vclient11 | INVOICED | 2021-01-01 14:37:44 | false |             1 |
      | vclient12 | INVOICED | 2021-02-01 14:37:44 | false |             2 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description                             | balance | accumulated |
      |   2 | CLIENT1 | By invoice over 500,By invoice over 500 |   30,30 |         0,0 |

  Scenario Outline: Voucher30 several invoices over 500 for several clients
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient11    |
      | CLIENT1   | vclient12    |
      | CLIENT2   | vclient21    |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2020-09-2 | false |   550 | 21.0 |
      |      2 | PAID   | 2020-10-2 | false |   150 | 21.0 |
      |      3 | PAID   | 2020-11-2 | false |   650 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vclient11 | INVOICED | 2021-01-01 14:37:44 | false |             1 |
      | vclient12 | INVOICED | 2021-02-01 14:37:44 | false |             2 |
      | vclient21 | INVOICED | 2021-03-01 14:37:44 | false |             3 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description         | balance | accumulated |
      |   1 | CLIENT1 | By invoice over 500 |      30 |           0 |
      |   1 | CLIENT2 | By invoice over 500 |      30 |           0 |

  Scenario Outline: Voucher30 several invoices over 500 and three workorders paid
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient11    |
      | CLIENT1   | vclient12    |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2020-09-2 | false |   550 | 21.0 |
      |      2 | PAID   | 2020-10-2 | false |  1150 | 21.0 |
      |      3 | PAID   | 2020-11-2 | false |   250 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vclient11 | INVOICED | 2021-01-01 14:37:44 | false |             1 |
      | vclient12 | INVOICED | 2021-02-01 14:37:44 | false |             2 |
      | vclient11 | INVOICED | 2021-03-01 14:37:44 | false |             3 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description                                                 | balance  | accumulated |
      |   3 | CLIENT1 | By invoice over 500,By invoice over 500,By three workorders | 30,30,20 | 0,0,0       |
