public class funny{
    
// Stole this from group zero
		public static int compare(String o1, String o2) {
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

        // If they are both equal for every digit in the range len, we double check that one isn't longer.
        // However, this doesn't work well when we convert fractions, because the type double doesn't hold enough digits in some cases

        // If the length of the first is less than the second, we say the second is longer.
        // For the second case, we assume that in the case of two decimals the order doesn't matter,
        // but we also assume the first one will be a converted fraction, so we have the first one defaulted as
        // "bigger" for the case of equality.
        if(decOne.length < decTwo.length) return -1*isNegative;
        else return 1*isNegative; 
    }


    public static int compareFractions(String fractionOne, String fractionTwo) {
        // We set this up, because if both of the inputs are negative technically the "bigger" value is smaller
        int isNegative = 1;
        // Imposter check is listed below
        if(imposterCheck(fractionOne, fractionTwo) != 0 && imposterCheck(fractionOne, fractionTwo) != 2) return imposterCheck(fractionOne, fractionTwo);
        if(imposterCheck(fractionOne, fractionTwo) == 2) isNegative = -1;

        // fracToInt is listed below
        double[] fracOne = fracToLong(fractionOne);
        double[] fracTwo = fracToLong(fractionTwo);

        // First numerator * second denominator and second numerator * first denominator should
        // represent the fractions with equal denominators, and we do our comparisons with that.
        if(fracOne[0]*fracTwo[1] > fracTwo[0]*fracOne[1]) {
            return 1 * isNegative;
        } 
        if(fracOne[0]*fracTwo[1] < fracTwo[0]*fracOne[1]) {
            return -1 * isNegative;
        }
        // If they happen to be equal, check which one has the larger denominator and return that as bigger
        if(fracOne[1] > fracTwo[1]) {
            return 1 * isNegative;
        }
        else {
            return -1 * isNegative;
        }
    }

    public static int compareFractionAndDecimal(String Fraction, String Decimal) {
        // We set this up, because if both of the inputs are negative technically the "bigger" value is smaller
        double isNegative = 1;
        // fracToInt is listed below
        double[] fracityFrac = fracToLong(Fraction);
        // isNegative is listed below
        // This is still helpful here to keep the negative value, since fracToInt gets rid of the negative
        if(isNegative(Fraction)) isNegative = -1;
        // Convert the fraction into a decimal
        // This currently has the weakness of not being able to convert things like 1/3 accurately
        // The commented out line makes it so the division is more accurate, but for now we use the less
        // accurate line since it makes this a lot quicker.
        //String deciFrac = String.valueOf(BigDecimal.valueOf(fracityFrac[0]*isNegative).divide(BigDecimal.valueOf(fracityFrac[1]), MathContext.DECIMAL128));
        String deciFrac = Double.toString(((double)fracityFrac[0]/(double)fracityFrac[1])*isNegative);

        // Then we use out compareDecimals method with the fraction in the first input spot.
        // We specify why we do this in the compareDecimals method.
        return compareDecimals(deciFrac, Decimal);
    }
    

    // Helper function section

    // Simple helper for return true or false if the first position in the string is a - sign
    public static boolean isNegative(String num) {
        if(num.contains("-")) return true;
        return false;
    }

    // ImposterCheck checks for if one, both or neither are negative. It will return 1 if the second is negative, -1 if the first is negative, 0 if both are positive,
    // 2 if both are negative
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

    // Converts a fractions into an int array,
    // arr[0] contains the numerator and
    // arr[1] contains the denominator
    // This method doesn't keep the negative sign, if there is any
    // so we will have to compensate for that in other methods
    public static double[] fracToLong(String fraction) {
        double[] arr = new double[2];
        int num = 0;
        int denom = 0;
        for(int i = 0; i < fraction.length(); i++) {
            if(fraction.charAt(i) == '/') {
                if(fraction.charAt(0) == '-') num = Integer.valueOf(fraction.substring(1, i));
                else num = Integer.valueOf(fraction.substring(0, i));
                denom = Integer.valueOf(fraction.substring(i + 1));
            }
        }

        arr[0] = num;
        arr[1] = denom;
        return arr;
    }

public static void main(String[] args) {

    // Two fractions, positive and negative
		System.out.println("-1/2 and 1/4:" + funny.compareFractions("-1/2","1/4"));
		System.out.println("1/2 and 1/3:" + funny.compareFractions("1/2","1/3"));
		System.out.println("1/2 and 2/4:" + funny.compareFractions("1/2","2/4"));
		System.out.println("-1/2 and -2/4:" + funny.compareFractions("-1/2","-2/4"));
		System.out.println("-2/4 and -1/2:" + funny.compareFractions("-2/4","-1/2"));
        System.out.println("-4/8 and -2/4:" + funny.compareFractions("-4/8","-2/4"));
		
        // Fraction and a decimal
		System.out.println("1/4 and 0.5:" + funny.compareFractionAndDecimal("1/4","0.5"));
		System.out.println("2/4 and 1.5:" + funny.compareFractionAndDecimal("2/4","1.5"));
		System.out.println("-2/4 and -1.5:" + funny.compareFractionAndDecimal("-2/4","-1.5"));
		System.out.println("-2/4 and 0:" + funny.compareFractionAndDecimal("-2/4","0"));
		System.out.println("1/2 and -0.5:" + funny.compareFractionAndDecimal("1/2","-0.5"));
		System.out.println("1/2 and 0.5:" + funny.compareFractionAndDecimal("1/2","0.5"));
		System.out.println("-1/2 and -0.5:" + funny.compareFractionAndDecimal("-1/2","-0.5"));
		System.out.println("1/3 and 0.33333333333333333333333333333333333333333:" + funny.compareFractionAndDecimal("1/3","0.33333333333333333333333333333333333333333"));
		
		System.out.println("-4.9999999999999999999999999999999999 and -5:" + funny.compareDecimals("-4.9999999999999999999999999999999999", "-5"));
		System.out.println("4.9999999999999999999999999999999999 and 5:" + funny.compareDecimals("4.9999999999999999999999999999999999", "5"));
    
}




}
