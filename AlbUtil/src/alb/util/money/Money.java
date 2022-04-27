package alb.util.money;

import java.util.Currency;
import java.math.BigDecimal;

/*
 * Created on Jun 24, 2004
 *
 * A class for handling numeric calculation, rounding and currency.
 * This class is extracted from "Patterns of Enterprise Application Architecture" by Martin Fowler.
 * http://www.martinfowler.com/eaaCatalog/money.html
 */
public class Money {
	private static final int[] cents = new int[] { 1, 10, 100, 1000 };

	private long amount;
	private Currency currency;

	Money() { }

	public Money(double amount, Currency currency) {
		this.currency = currency;
		this.amount = Math.round(amount * centFactor());
	}

	public Money(BigDecimal amount, Currency currency) {
		this.currency = currency;
		BigDecimal myAmount = amount.multiply( new BigDecimal( centFactor() ) );
		this.amount = Math.round( myAmount.longValue() );
	}

	public Money(long amount, Currency currency) {
		this.currency = currency;
		this.amount = Math.round(amount * centFactor());
	}

	// helper constructors for frequently used currency
	public static Money dollars(double amount) {
		return new Money(amount, Currency.getInstance("USD"));
	}

	public static Money euros(double amount) {
		return new Money(amount, Currency.getInstance("EUR"));
	}

	private int centFactor() {
		return cents[ currency.getDefaultFractionDigits() ];
	}

	public BigDecimal amount() {
		return BigDecimal.valueOf(amount, currency.getDefaultFractionDigits());
	}

	public Currency currency() {
		return currency;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Money))
			return false;
		Money other = (Money) obj;
		if (amount != other.amount)
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		return true;
	}

	public boolean equals(Money other) {
		return currency.equals(other.currency) && (amount == other.amount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (amount ^ (amount >>> 32));
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		return result;
	}

	public Money add(Money other) {
		assertSameCurrencyAs(other);
		return newMoney(amount + other.amount);
	}

	public Money subtract(Money other) {
		assertSameCurrencyAs(other);
		return newMoney(amount - other.amount);
	}

	public int compareTo(Object other) {
		return compareTo((Money) other);
	}

	public int compareTo(Money other) {
		assertSameCurrencyAs(other);
		return (amount < other.amount) ? -1 : ((amount == other.amount) ? 0 : 1);
}

	public boolean greaterThan(Money other) {
		return (compareTo(other) > 0);
	}

	public Money multiply(double factor) {
		return multiply(new BigDecimal(factor));
	}

	public Money multiply(long factor) {
		return multiply(new BigDecimal(factor));
	}

	public Money multiply(int factor) {
		return multiply(new BigDecimal(factor));
	}

	public Money multiply(BigDecimal factor) {
		return new Money(amount().multiply(factor), currency);
	}

	/** 
	 * These two methods allocate money among multiple targets without loss
	 * 
	 * As an example, you want to distribute 100 € among three people:
	 * 
	 *   Money[] result = Money.euros( 100 ).allocate( 3 )
	 *   result[0] --> 33.33 €
	 *   result[1] --> 33.33 €
	 *   result[2] --> 33.34 €  <-- There is no loss
	 *   			----------
	 *   	Total  -> 100.00 €
	 *   
	 * @param targets
	 * @return
	 */
	public Money[] allocate(int targets) {
		Money lowResult = newMoney(amount / targets);
		Money highResult = newMoney(lowResult.amount + 1);
		Money[] results = new Money[targets];
		int remainder = (int) amount % targets;
		for (int i = 0; i < remainder; i++)
			results[i] = highResult;
		for (int i = remainder; i < targets; i++)
			results[i] = lowResult;
		return results;
	}

	/** 
	 * This allocation algorithm can handle any ratio
	 * 
	 * @param ratios
	 * @return
	 */
	public Money[] allocate(long[] ratios) {
		long total = 0;
		for (int i = 0; i < ratios.length; i++)
			total += ratios[i];
		long remainder = amount;
		Money[] results = new Money[ratios.length];
		for (int i = 0; i < results.length; i++) {
			results[i] = newMoney(amount * ratios[i] / total);
			remainder -= results[i].amount;
		}
		for (int i = 0; i < remainder; i++) {
			results[i].amount++;
		}
		return results;
	}

	private void assertSameCurrencyAs(Money m) {
		if (currency.equals(m.currency)) return;
		throw new RuntimeException("Not same currency: " + currency + " <> " + m.currency);
	}

	private Money newMoney(long amount) {
		Money money = new Money();
		money.currency = this.currency;
		money.amount = amount;
		return money;
	}

	public Money min(Money other) {
		return compareTo(other) >= 1 ? this : other;
	}

	public Money max(Money other) {
		return compareTo(other) <= 1 ? this : other;
	}
}