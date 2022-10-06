public class funny {
    public static void main(String[] args) {
        
        String potato = "0.11131111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        String salmon = "00.11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        String fraction = "1/1112";


        char[] potatobutBetter = potato.toCharArray();
        char[] salmonbutBetter = salmon.toCharArray();

        if(isNegative(potatobutBetter) && !isNegative(salmonbutBetter)) {
            System.out.println("Salmon better because it aint negative");
            return;
        }

        if(!isNegative(potatobutBetter) && isNegative(salmonbutBetter)) {
            System.out.println("Potato better because it aint negative");
            return;
        }



        
        comparisonThingy(potatobutBetter, salmonbutBetter);




        //System.out.println("If this is it, they're equal");
    }


    public static boolean isNegative(char[] number){
        if(number[0] == '-') return true;
        return false;
    }

    public static void comparisonThingy(char[] number, char[] anotherNumber){
        int len = length(number, anotherNumber);
        
        
        for (int i = 0; i < len; i++) {
                if(number[i] < anotherNumber[i]){
                    System.out.println("Salmon better");
                    return;
                }
                if(number[i] > anotherNumber[i]){
                    System.out.println("Potato better");
                    return;
                }
        }
        System.out.println();

        if(number.length - len > 0) System.out.println("Potato is better because it is longer");
        else System.out.println("Salmon is better because it is longer");
    }

    public static int length(char[] number, char[] anotherNumber) {
        if(number.length > anotherNumber.length) return number.length;
        else return anotherNumber.length;
    }




}
