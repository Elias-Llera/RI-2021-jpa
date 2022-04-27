Feature: Generate vouchers 20
  As a Manager
  I want to generate vouchers for clients

  Scenario Outline: Geneating one Voucher20
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    #	  | CLIENT2 | vclient2 |
    #	  | CLIENT3 | vclient3 |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   300 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-03-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-06-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description         | balance | accumulated |
      |   1 | CLIENT1 | By three workorders |      20 |           0 |

  Scenario Outline: Voucher20 two in a row
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    #	  | CLIENT2 | vclient2 |
    #	  | CLIENT3 | vclient3 |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   300 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-03-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-06-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
    When I generate vouchers
    And I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher20 not trustable client
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
    And the following invoices
      | number | status       | date      | used  | money | vat  |
      |      1 | NOT_YET_PAID | 2021-09-2 | false |   300 | 21.0 |
    And the following workorders
      | platenum | status   | date                | used  | invoiceNumber |
      | vclient1 | INVOICED | 2021-03-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-06-01 14:37:44 | false |             1 |
      | vclient1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher20 trustable client with several not paid
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient11    |
      | CLIENT1   | vclient12    |
    And the following invoices
      | number | status       | date      | used  | money | vat  |
      |      1 | NOT_YET_PAID | 2021-09-2 | false |   300 | 21.0 |
      |      2 | PAID         | 2021-09-2 | false |   300 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vclient11 | INVOICED | 2021-03-01 14:37:44 | false |             2 |
      | vclient12 | INVOICED | 2021-06-01 14:37:44 | false |             2 |
      | vclient12 | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient12 | INVOICED | 2020-09-01 14:37:44 | false |             1 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description         | balance | accumulated |
      |   1 | CLIENT1 | By three workorders |      20 |           0 |

  Scenario Outline: Voucher20 trustable client more than one voucher
    Given the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient11    |
      | CLIENT1   | vclient12    |
      | CLIENT1   | vclient13    |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   300 | 21.0 |
      |      2 | PAID   | 2021-09-2 | false |   300 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vclient11 | INVOICED | 2021-03-01 14:37:44 | false |             2 |
      | vclient12 | INVOICED | 2021-06-02 14:37:44 | false |             1 |
      | vclient13 | INVOICED | 2021-09-03 14:37:44 | false |             2 |
      | vclient11 | INVOICED | 2021-09-04 14:37:44 | false |             2 |
      | vclient12 | INVOICED | 2021-09-05 14:37:44 | false |             2 |
      | vclient13 | INVOICED | 2020-09-06 14:37:44 | false |             1 |
      | vclient13 | INVOICED | 2020-09-07 14:37:44 | false |             1 |
      | vclient13 | INVOICED | 2020-09-08 14:37:44 | false |             1 |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description                             | balance | accumulated |
      |   2 | CLIENT1 | By three workorders,By three workorders |   20,20 |         0,0 |
