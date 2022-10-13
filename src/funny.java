import java.util.Arrays;
import java.util.Comparator;

public class funny  implements Comparator<String> {
    public static void main(String[] args) {

        funny thing = new funny();
        
        String potato = "0.33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333";
        String salmon = "-0.21111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        String fracOne = "1/3";
        String fracTwo = "-1/2";


        System.out.println("Result of Fraction compare: " + thing.compareFractions(fracTwo, fracOne));
        System.out.println("Result of Decimal compare: " + thing.compareDecimals(potato, salmon));
        System.out.println("Result of Decimal/Fraction compare: " + thing.compareFractionAndDecimal(potato, fracOne)); //fracOne should be bigger
        funny.runTests();

    }

    

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


    public int compareDecimals(String decimalOne, String decimalTwo) {
        int isNegative = 1;
        char[] decOne = decimalOne.toCharArray();
        char[] decTwo = decimalTwo.toCharArray();

        if(imposterCheck(decimalOne, decimalTwo) != 0 && imposterCheck(decimalOne, decimalTwo) != 2) return imposterCheck(decimalOne, decimalTwo);
        if(imposterCheck(decimalOne, decimalTwo) == 2) isNegative = -1; 
        int len = Math.min(decOne.length, decTwo.length);
        for (int i = 0; i < len; i++) {
                if(decOne[i] < decTwo[i]) {
                    return -1*isNegative;
                }
                if(decOne[i] > decTwo[i]) {
                    return 1*isNegative;
                }
        }

        if(decOne.length < decTwo.length) return -1*isNegative;
        else return 1*isNegative; // Equal and less than return the same thing, because we're funny
    }


    public int compareFractions(String fractionOne, String fractionTwo) {
        int isNegative = 1;
        if(imposterCheck(fractionOne, fractionTwo) != 0 && imposterCheck(fractionOne, fractionTwo) != 2) return imposterCheck(fractionOne, fractionTwo);
        if(imposterCheck(fractionOne, fractionTwo) == 2) isNegative = -1;

        int[] fracOne = fracToInt(fractionOne);
        int[] fracTwo = fracToInt(fractionTwo);

            if(fracOne[0]*fracTwo[1] > fracTwo[0]*fracOne[1]){
                return 1 * isNegative;
            } else {
                return -1 * isNegative; // If they're the same it doesn't matter plus if the first one isn't true then its the opposite
            }
    }

    public int compareFractionAndDecimal(String Fraction, String Decimal) {

        int[] fracityFrac = fracToInt(Fraction);
        String deciFrac = Double.toString((double)fracityFrac[0]/(double)fracityFrac[1]);

        return compareDecimals(deciFrac, Decimal);
    }
    

    // Helper function section

    public boolean isNegative(String decimalOne) {
        if(decimalOne.contains("-")) return true;
        return false;
    }

    public int imposterCheck(String one, String two){
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

    public int[] fracToInt(String fraction) {
        int[] arr = new int[2];
        int num = 0;
        int denom = 0;
        for(int i = 0; i < fraction.length(); i++) {
            if(fraction.charAt(i) == '/') {
                num = Integer.valueOf(fraction.substring(0, i));
                denom = Integer.valueOf(fraction.substring(i + 1));
            }
        }

        arr[0] = num;
        arr[1] = denom;
        return arr;
    }


    private static void runTests() {
		funny comp = new funny();
		
		// Two fractions, positive and negative
		System.out.println("-1/2 and 1/4:" + comp.compareFractions("-1/2","1/4"));
		System.out.println("1/2 and 1/3:" + comp.compareFractions("1/2","1/3"));
		System.out.println("1/2 and 2/4:" + comp.compareFractions("1/2","2/4"));
		System.out.println("-1/2 and -2/4:" + comp.compareFractions("-1/2","-2/4"));
		
		// Fraction and a decimal
		System.out.println("1/4 and 0.5:" + comp.compareFractionAndDecimal("1/4","0.5"));
		System.out.println("2/4 and 1.5:" + comp.compareFractionAndDecimal("2/4","1.5"));
		System.out.println("-2/4 and -1.5:" + comp.compareFractionAndDecimal("-2/4","-1.5"));
		System.out.println("-2/4 and 0:" + comp.compareFractionAndDecimal("-2/4","0"));
		System.out.println("1/2 and -0.5:" + comp.compareFractionAndDecimal("1/2","-0.5"));
		System.out.println("1/2 and 0.5:" + comp.compareFractionAndDecimal("1/2","0.5"));
		System.out.println("-1/2 and -0.5:" + comp.compareFractionAndDecimal("-1/2","-0.5"));
		System.out.println("1/3 and -0.5:" + comp.compareFractionAndDecimal("1/3","-0.5"));
		
		System.out.println("-4.9999999999999999999999999999999999 and -5:" + comp.compareDecimals("-4.9999999999999999999999999999999999", "-5"));
		System.out.println("4.9999999999999999999999999999999999 and 5:" + comp.compareDecimals("4.9999999999999999999999999999999999", "5"));
		
	}




}
