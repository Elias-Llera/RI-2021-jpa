Feature: Settle an invoice with one or multiple charges
  As a Cashier
  I want to settle an invoice  
  Because I want to get money

  Scenario: Settle an invoice for one charge with cash
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    And the following charges for the invoice
      | type | amount |
      | CASH |   1210 |
    # money 1000 plus VAT = 1210
    When I settle the invoice
    Then the invoice is PAID
    And payment means are updated
    And the following charges are created
      | paymentMean | invoiceNumber | amount |
      | CASH        |             1 |   1210 |

  Scenario: Settle a PAID invoice
    Given a client registered
    And the following invoices
      | number | money | status | use   |
      |      1 |  1000 | PAID   | false |
    And the following charges for the invoice
      | type | amount |
      | CASH |   1210 |
    # money 1000 plus VAT = 1210
    And I try to settle the invoice
    Then an error happens with an explaining message

  Scenario: Settle an invoice twice
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    And the following charges for the invoice
      | type | amount |
      | CASH |   1210 |
    # money 1000 plus VAT = 1210
    When I settle the invoice
    And I try to settle the invoice
    Then an error happens with an explaining message

  Scenario: Settle an invoice for one charge with credit card
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following credit cards
      | number | type | validthru  |
      | one    | VISA | 2022-12-31 |
    And the following charges for the invoice
      | type       | payident | amount |
      | CREDITCARD | one      |   1210 |
    When I settle the invoice
    Then the invoice is PAID
    And payment means are updated
    And the following charges are created
      | paymentMean | payident | invoiceNumber | amount |
      | CREDITCARD  | one      |             1 |   1210 |

  Scenario: Trying to settle an invoice for one charge with an expired credit card
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following credit cards
      | number | type | validthru  |
      | one    | VISA | 2012-12-31 |
    And the following charges for the invoice
      | type       | payident | amount |
      | CREDITCARD | one      |   1210 |
    When I try to settle the invoice
    Then an error happens with an explaining message

  Scenario: Settle an invoice for one charge with voucher
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following vouchers
      | code | money | description |
      | one  |  1500 | voucher     |
    And the following charges for the invoice
      | type    | payident | amount |
      | VOUCHER | one      |   1210 |
    When I settle the invoice
    Then the invoice is PAID
    And payment means are updated
    And the following charges are created
      | paymentMean | payident | invoiceNumber | amount |
      | VOUCHER     | one      |             1 |   1210 |

  Scenario: Settle an invoice for one charge with voucher, NOT ENOUGH MONEY
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following vouchers
      | code | money | description |
      | one  |   500 | voucher     |
    And the following charges for the invoice
      | type    | payident | amount |
      | VOUCHER | one      |   1210 |
    When I try to settle the invoice
    Then an error happens with an explaining message

  Scenario: Settle an invoice for several charges with cash and voucher
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following vouchers
      | code | money | description |
      | one  |   200 | voucher     |
    And the following charges for the invoice
      | type    | payident | amount |
      | VOUCHER | one      |    200 |
      | CASH    |          |   1010 |
    When I settle the invoice
    Then the invoice is PAID
    And payment means are updated
    And the following charges are created
      | paymentMean | payident | invoiceNumber | amount |
      | VOUCHER     | one      |             1 |    200 |
      | CASH        |          |             1 |   1010 |

  Scenario: Settle an invoice for one charge with cash and credit card
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following credit cards
      | number | type | validthru  |
      | one    | VISA | 2022-12-31 |
    And the following charges for the invoice
      | type       | payident | amount |
      | CREDITCARD | one      |    200 |
      | CASH       |          |   1010 |
    When I settle the invoice
    Then the invoice is PAID
    And payment means are updated
    And the following charges are created
      | paymentMean | payident | invoiceNumber | amount |
      | CREDITCARD  | one      |             1 |    200 |
      | CASH        |          |             1 |   1010 |

  Scenario: Settle an invoice for one charge with cash, voucher and credit card
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  1000 | NOT_YET_PAID | false |
    # money 1000 plus VAT = 1210
    And the following credit cards
      | number | type | validthru  |
      | one    | VISA | 2022-12-31 |
    And the following vouchers
      | code | money | description |
      | one  |   200 | voucher     |
    And the following charges for the invoice
      | type       | payident | amount |
      | VOUCHER    | one      |    200 |
      | CREDITCARD | one      |   1000 |
      | CASH       |          |     10 |
    When I settle the invoice
    Then the invoice is PAID
    And payment means are updated
    And the following charges are created
      | paymentMean | payident | invoiceNumber | amount |
      | CREDITCARD  | one      |             1 |   1000 |
      | CASH        |          |             1 |     10 |
      | VOUCHER     | one      |             1 |    200 |

  Scenario: Settle an invoice for one charge with cash, voucher and credit card -> partially paid invoice
    Given a client registered
    And the following invoices
      | number | money | status       | use   |
      |      1 |  2000 | NOT_YET_PAID | false |
    # money 2000 plus VAT = 2400
    And the following credit cards
      | number | type | validthru  |
      | one    | VISA | 2022-12-31 |
    And the following vouchers
      | code | money | description |
      | one  |  2200 | voucher     |
    And the following charges for the invoice
      | type       | payident | amount |
      | VOUCHER    | one      |    200 |
      | CREDITCARD | one      |   1000 |
      | CASH       |          |     10 |
    When I try to settle the invoice
    Then an error happens with an explaining message
