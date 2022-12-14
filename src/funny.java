import java.math.BigInteger;
import java.util.Comparator;

public class funny implements Comparator<String>{
    
// Stole this from group zero
        @Override
		public int compare(String o1, String o2) {
			if(o1.contains("/")){
				if(o2.contains("/")){
					return compareFractions(o1, o2);
				}else{
					return compareFractionAndDecimal(o1, o2);
				}

			}else{
				if(o2.contains("/")){
					return -compareFractionAndDecimal(o2, o1);
				}else{
					return compareDecimals(o1,o2);
				}
			}

		}

    // Comparing two decimals in string form
    public static int compareDecimals(String decimalOne, String decimalTwo) {
        // We set this up, because if both of the inputs are negative technically the "bigger" value is smaller
        int isNegative = 1;
        char[] decOne = decimalOne.toCharArray();
        char[] decTwo = decimalTwo.toCharArray();

        // imposterCheck is a helper function listed below (in helper function section)
        if(imposterCheck(decimalOne, decimalTwo) != 0 && imposterCheck(decimalOne, decimalTwo) != 2) return imposterCheck(decimalOne, decimalTwo);
        if(imposterCheck(decimalOne, decimalTwo) == 2) isNegative = -1;

        // Gets the minimum length so the index doesn't go out of bounds for either decimal
        int len = Math.min(decOne.length, decTwo.length);

        // This cycles through checking each digit against each other
        // Technically, starting on the left, each digit is more "valuable," so we use this as a shortcut
        // However, this isn't great if they're equal
        for (int i = 0; i < len; i++) {
                if(decOne[i] < decTwo[i]) {
                    return -1*isNegative;
                }
                if(decOne[i] > decTwo[i]) {
                    return 1*isNegative;
                }
        }

        // If the length of the first is less than the second, we say the second is longer.
        // For the second case, we assume that in the case of two decimals the order doesn't matter,
        // but we also assume the first one will be a converted fraction, so we have the first one defaulted as
        // "bigger" for the case of equality.
        if(decOne.length < decTwo.length) return -1*isNegative;
        else return 1*isNegative; 
    }


    // Comparing fractions in string form
    public static int compareFractions(String fractionOne, String fractionTwo) {
        // We set this up, because if both of the inputs are negative technically the "bigger" value is smaller
        int isNegative = 1;

        // Imposter check is listed below
        if(imposterCheck(fractionOne, fractionTwo) != 0 && imposterCheck(fractionOne, fractionTwo) != 2) return imposterCheck(fractionOne, fractionTwo);
        if(imposterCheck(fractionOne, fractionTwo) == 2) isNegative = -1;

        // fracToInt is listed below
        long[] fracOne = fracToLong(fractionOne);
        long[] fracTwo = fracToLong(fractionTwo);

        // Cross multiply so the resulting fractions have the same denominator
        // Easier to compare
        if(fracOne[0]*fracTwo[1] > fracTwo[0]*fracOne[1]) {
            return 1 * isNegative;
        } 
        if(fracOne[0]*fracTwo[1] < fracTwo[0]*fracOne[1]) {
            return -1 * isNegative;
        }

        // If they happen to be equal, check which one has the larger denominator and return that as bigger
        if(fracOne[1] < fracTwo[1]) {
            return -1 * isNegative;
        }
        else if (fracOne[1] > fracTwo[1]) {
            return 1 * isNegative;
        } else return 0;
    }

    // We gave up, couldn't convert fraction to decimal and keep enough precision
    // and we also tried using a convert decimal to fraction function that didn't keep enough precision either
    public static int compareFractionAndDecimal(String fraction, String decimal) {
        String[] saFrac = fraction.split("/");
        BigInteger numerator1 = new BigInteger(saFrac[0]);
        BigInteger denominator1 = new BigInteger(saFrac[1]);
        
        //find the length of the decimal's fractional part
        String[] saDec = decimal.split("\\."); // need \\ because . is a special symbol in regex
        
        BigInteger numerator2 = null;
        BigInteger denominator2 = null; 				
        
        if (saDec.length == 1) { // an integer, positive or negative
            numerator2 = new BigInteger(saDec[0]);
            denominator2 = new BigInteger("1");
        } else {
            // find the length of the decimal part
            int n = saDec[1].length();
            denominator2 = new BigInteger("1"); 
            // raising 10 to the power n
            for (int i = 0; i < n; ++i) {
                denominator2 = denominator2.multiply(new BigInteger("10"));
            }
            numerator2 = new BigInteger(saDec[1]);
            // adding the integer part
            BigInteger intPart = new BigInteger(saDec[0]);
            if (saDec[0].charAt(0) == '-') { // the number is negative 
                numerator2 = (intPart.multiply(denominator2)).subtract(numerator2);
            } else {
                numerator2 = numerator2.add(intPart.multiply(denominator2));
            }
        }
        
        BigInteger crossMult1 = numerator1.multiply(denominator2);
        BigInteger crossMult2 = numerator2.multiply(denominator1);
        
        int res = crossMult1.compareTo(crossMult2);
        
        if (res != 0) return res;
        
        if (numerator1.compareTo(new BigInteger("0")) >= 0) {
            return 1; // for positive, the decimal is smaller
        } else {
            return -1; // for negative, the decimal is larger
        }
    }

    /* Helper Function Section */

    // Simple helper for return true or false if the first position in the string is a - sign
    public static boolean isNegative(String num) {
        if(num.contains("-")) return true;
        return false;
    }

    /* ImposterCheck checks for if one, both or neither are negative. 
     * It will return 1 if the second is negative, -1 if the first is negative, 
     * 0 if both are positive, 2 if both are negative
    */
    public static int imposterCheck(String one, String two){
        if(isNegative(one) && !isNegative(two)) {
            return -1;
        }

        if(!isNegative(one) && isNegative(two)) {
            return 1;
        } 
        if(isNegative(one) && isNegative(two)) {
            return 2;
        }
        return 0;
    }

    /*Converts a fractions into an int array,
     * arr[0] contains the numerator and
     * arr[1] contains the denominator
     * This method doesn't keep the negative sign, if there is any,
     * so we will have to compensate for that in other methods
    */
    public static long[] fracToLong(String fraction) {
        long[] arr = new long[2];
        String[] temp = fraction.split("\\/");
        int removeNegative = 1;
        if(funny.isNegative(fraction)) removeNegative = -1;
        arr[0] = Long.parseLong(temp[0])*removeNegative;
        arr[1] = Long.parseLong(temp[1]);
        return arr;
    }

    // Testing
    public static void main(String[] args) {
		
		// Two fractions, positive and negative
		System.out.println("-1/2 and 1/4:" + funny.compareFractions("-1/2","1/4"));
		System.out.println("1/2 and 1/3:" + funny.compareFractions("1/2","1/3"));
		System.out.println("1/2 and 2/4:" + funny.compareFractions("1/2","2/4"));
		System.out.println("-1/2 and -2/4:" + funny.compareFractions("-1/2","-2/4"));
		
		// Fraction and a decimal
		System.out.println("1/4 and 0.5:" + funny.compareFractionAndDecimal("1/4","0.5"));
		System.out.println("2/4 and 1.5:" + funny.compareFractionAndDecimal("2/4","1.5"));
		System.out.println("-2/4 and -1.5:" + funny.compareFractionAndDecimal("-2/4","-1.5"));
		System.out.println("-2/4 and 0:" + funny.compareFractionAndDecimal("-2/4","0"));
		System.out.println("1/2 and -0.5:" + funny.compareFractionAndDecimal("1/2","-0.5"));
		System.out.println("1/2 and 0.5:" + funny.compareFractionAndDecimal("1/2","0.5"));
		System.out.println("-1/2 and -0.5:" + funny.compareFractionAndDecimal("-1/2","-0.5"));
		System.out.println("1/3 and -0.5:" + funny.compareFractionAndDecimal("1/3","-0.5"));
		
        // Testing precision
		System.out.println("-4.9999999999999999999999999999999999 and -5:" + funny.compareDecimals("-4.9999999999999999999999999999999999", "-5"));
		System.out.println("4.9999999999999999999999999999999999 and 5:" + funny.compareDecimals("4.9999999999999999999999999999999999", "5"));
    }
}
