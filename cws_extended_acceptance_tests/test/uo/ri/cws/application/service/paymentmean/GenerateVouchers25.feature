Feature: Generate vouchers 25
  As a Manager
  I want to generate vouchers for clients

  Scenario Outline: Voucher25
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      2 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      4 | PAID   | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | false |
      | CLIENT2   | SPONSOR    | false |
      | CLIENT3   | SPONSOR    | false |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description       | balance | accumulated |
      |   1 | SPONSOR | By recommendation |      25 |           0 |

  Scenario Outline: Voucher25 twice in a row
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      2 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      4 | PAID   | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | false |
      | CLIENT2   | SPONSOR    | false |
      | CLIENT3   | SPONSOR    | false |
    When I generate vouchers
    And I generate vouchers again
    Then We get no vouchers

  Scenario Outline: Voucher25 generate more than one
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
      | CLIENT4   | vclient4     |
      | CLIENT5   | vclient5     |
      | CLIENT6   | vclient6     |
      | CLIENT7   | vclient7     |
      | CLIENT8   | vclient8     |
      | CLIENT9   | vclient9     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      2 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      4 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      5 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      6 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      7 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      8 | PAID   | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
      | vclient4  | INVOICED | 2021-09-01 14:37:44 | false |             5 |
      | vclient5  | INVOICED | 2021-09-01 14:37:44 | false |             6 |
      | vclient6  | INVOICED | 2021-09-01 14:37:44 | false |             7 |
      | vclient7  | INVOICED | 2021-09-01 14:37:44 | false |             8 |
      | vclient8  | OPEN     | 2021-09-01 14:37:44 | false |               |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | false |
      | CLIENT2   | SPONSOR    | false |
      | CLIENT3   | SPONSOR    | false |
      | CLIENT4   | SPONSOR    | false |
      | CLIENT5   | SPONSOR    | false |
      | CLIENT6   | SPONSOR    | false |
      | CLIENT7   | SPONSOR    | false |
      | CLIENT8   | SPONSOR    | false |
      | CLIENT9   | SPONSOR    | false |
    When I generate vouchers
    Then We get the following vouchers
      | num | client  | description                         | balance | accumulated |
      |   2 | SPONSOR | By recommendation,By recommendation |   25,25 |         0,0 |

  Scenario Outline: Voucher25 not enough recommendations
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
      | CLIENT4   | vclient4     |
      | CLIENT5   | vclient5     |
      | CLIENT6   | vclient6     |
      | CLIENT7   | vclient7     |
      | CLIENT8   | vclient8     |
      | CLIENT9   | vclient9     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      2 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      4 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      5 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      6 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      7 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      8 | PAID   | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
      | vclient4  | INVOICED | 2021-09-01 14:37:44 | false |             5 |
      | vclient5  | INVOICED | 2021-09-01 14:37:44 | false |             6 |
      | vclient6  | INVOICED | 2021-09-01 14:37:44 | false |             7 |
      | vclient7  | INVOICED | 2021-09-01 14:37:44 | false |             8 |
      | vclient8  | OPEN     | 2021-09-01 14:37:44 | false |               |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | false |
      | CLIENT2   | SPONSOR    | false |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher25 not enough unused recommendations from trustable clients
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
      | CLIENT4   | vclient4     |
      | CLIENT5   | vclient5     |
      | CLIENT6   | vclient6     |
      | CLIENT7   | vclient7     |
      | CLIENT8   | vclient8     |
      | CLIENT9   | vclient9     |
    And the following invoices
      | number | status | date      | used  | money | vat  |
      |      1 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      2 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      4 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      5 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      6 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      7 | PAID   | 2021-09-2 | false |   100 | 21.0 |
      |      8 | PAID   | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
      | vclient4  | INVOICED | 2021-09-01 14:37:44 | false |             5 |
      | vclient5  | INVOICED | 2021-09-01 14:37:44 | false |             6 |
      | vclient6  | INVOICED | 2021-09-01 14:37:44 | false |             7 |
      | vclient7  | INVOICED | 2021-09-01 14:37:44 | false |             8 |
      | vclient8  | OPEN     | 2021-09-01 14:37:44 | false |               |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | true  |
      | CLIENT2   | SPONSOR    | true  |
      | CLIENT3   | SPONSOR    | true  |
      | CLIENT4   | SPONSOR    | false |
      | CLIENT5   | SPONSOR    | true  |
      | CLIENT6   | SPONSOR    | true  |
      | CLIENT7   | SPONSOR    | false |
      | CLIENT8   | SPONSOR    | true  |
      | CLIENT9   | SPONSOR    | false |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher25 sponsor not trustable
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
      | CLIENT4   | vclient4     |
      | CLIENT5   | vclient5     |
      | CLIENT6   | vclient6     |
      | CLIENT7   | vclient7     |
      | CLIENT8   | vclient8     |
      | CLIENT9   | vclient9     |
    And the following invoices
      | number | status       | date      | used  | money | vat  |
      |      1 | NOT_YET_PAID | 2021-09-2 | false |   100 | 21.0 |
      |      2 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      4 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      5 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      6 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      7 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      8 | PAID         | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
      | vclient4  | INVOICED | 2021-09-01 14:37:44 | false |             5 |
      | vclient5  | INVOICED | 2021-09-01 14:37:44 | false |             6 |
      | vclient6  | INVOICED | 2021-09-01 14:37:44 | false |             7 |
      | vclient7  | INVOICED | 2021-09-01 14:37:44 | false |             8 |
      | vclient8  | OPEN     | 2021-09-01 14:37:44 | false |               |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | false |
      | CLIENT2   | SPONSOR    | false |
      | CLIENT3   | SPONSOR    | false |
      | CLIENT4   | SPONSOR    | false |
      | CLIENT5   | SPONSOR    | false |
      | CLIENT6   | SPONSOR    | false |
      | CLIENT7   | SPONSOR    | false |
      | CLIENT8   | SPONSOR    | false |
      | CLIENT9   | SPONSOR    | false |
    When I generate vouchers
    Then We get no vouchers

  Scenario Outline: Voucher25 recommended clients not trustable
    Given this client as sponsor
      | clientdni | vehiclePlate |
      | SPONSOR   | vsponsor1    |
    And the following relation of clients and vehicles
      | clientdni | vehiclePlate |
      | CLIENT1   | vclient1     |
      | CLIENT2   | vclient2     |
      | CLIENT3   | vclient3     |
      | CLIENT4   | vclient4     |
      | CLIENT5   | vclient5     |
      | CLIENT6   | vclient6     |
      | CLIENT7   | vclient7     |
      | CLIENT8   | vclient8     |
      | CLIENT9   | vclient9     |
    And the following invoices
      | number | status       | date      | used  | money | vat  |
      |      1 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      2 | NOT_YET_PAID | 2021-09-2 | false |   100 | 21.0 |
      |      3 | PAID         | 2021-09-2 | false |   100 | 21.0 |
      |      4 | NOT_YET_PAID | 2021-09-2 | false |   100 | 21.0 |
      |      5 | NOT_YET_PAID | 2021-09-2 | false |   100 | 21.0 |
    And the following workorders
      | platenum  | status   | date                | used  | invoiceNumber |
      | vsponsor1 | INVOICED | 2021-09-01 14:37:44 | false |             1 |
      | vclient1  | INVOICED | 2021-09-01 14:37:44 | false |             2 |
      | vclient2  | INVOICED | 2021-09-01 14:37:44 | false |             3 |
      | vclient3  | INVOICED | 2021-09-01 14:37:44 | false |             4 |
      | vclient4  | OPEN     | 2021-09-01 14:37:44 | false |             5 |
      | vclient5  | OPEN     | 2021-09-01 14:37:44 | false |             6 |
      | vclient6  | FINISHED | 2021-09-01 14:37:44 | false |             7 |
      | vclient7  | ASSIGNED | 2021-09-01 14:37:44 | false |             8 |
      | vclient8  | OPEN     | 2021-09-01 14:37:44 | false |               |
    And the following recommendations
      | clientdni | sponsordni | used  |
      | CLIENT1   | SPONSOR    | false |
      | CLIENT2   | SPONSOR    | false |
      | CLIENT3   | SPONSOR    | false |
      | CLIENT4   | SPONSOR    | false |
      | CLIENT5   | SPONSOR    | false |
      | CLIENT6   | SPONSOR    | false |
      | CLIENT7   | SPONSOR    | false |
      | CLIENT8   | SPONSOR    | false |
      | CLIENT9   | SPONSOR    | false |
    When I generate vouchers
    Then We get no vouchers
