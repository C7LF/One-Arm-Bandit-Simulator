package Bandit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import javax.swing.Timer;

public class Bandit {
	 static BigDecimal credit = new BigDecimal(0.00);
	 static BigDecimal WinningCredit = new BigDecimal(0.00);
	 static Random rand = new Random();
	 
	 public static BigDecimal setCredit() {
		 return credit;
	 }
	
	public static BigDecimal getCredit() {
		credit = credit.add(new BigDecimal(1.0));
		return credit.setScale(2, RoundingMode.HALF_DOWN); 
	}
	
	public static BigDecimal SpinRemoveCredit() {
		credit = credit.subtract(new BigDecimal(0.2));
		return credit.setScale(2, RoundingMode.HALF_DOWN);
	}
	public static int GenerateNumber1(int one) {
		if (one == 1) {
			int rNumber1 = 1 + rand.nextInt(8);
			return rNumber1;
		};
		if (one == 2) {
			int rNumber2 = 1 + rand.nextInt(8);
			return rNumber2;
		}
		if (one == 3) {
			int rNumber3 = 1 + rand.nextInt(8);
			return rNumber3;
		}
		return one;
	}
	public static BigDecimal WinAmount(int wa) {
		if (wa == 3) {
			WinningCredit = WinningCredit.add(new BigDecimal(1.00));
			return WinningCredit.setScale(2, RoundingMode.HALF_DOWN);
		}
		if (wa == 2) {
			WinningCredit = WinningCredit.add(new BigDecimal(0.50));
			return WinningCredit.setScale(2, RoundingMode.HALF_DOWN);
		}
		if (wa == 1) {
			WinningCredit = WinningCredit.add(new BigDecimal(0.20));
			return WinningCredit.setScale(2, RoundingMode.HALF_DOWN);
		}
		return WinningCredit;
	}
	public static BigDecimal setWinningCredit() {
		return WinningCredit.setScale(2, RoundingMode.HALF_DOWN);
	}
	
	public static void TranferWinning() {
		credit = credit.add(WinningCredit).setScale(2, RoundingMode.HALF_DOWN);;
		WinningCredit = new BigDecimal(0.00);
	}
	public static void cashOut() {
		WinningCredit = new BigDecimal(0.00);
	}
	public static BigDecimal getCurrentWinningCredit() {
		return WinningCredit;
	}
}
