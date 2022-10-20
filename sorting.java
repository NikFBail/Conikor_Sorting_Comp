
// Java program for implementation of Insertion Sort
// Sourced from www.programiz.com
// https://www.programiz.com/java-programming/examples/quick-sort#:~:text=Quicksort%20in%20Java&text=While%20dividing%20the%20array%2C%20the,both%20left%20and%20right%20subarrays.
public class sorting {
  // method to find the partition position
  static int partition(String array[], int low, int high) {
    
    // choose the rightmost element as pivot
    String pivot = array[high];
    
    // pointer for greater element
    int i = (low - 1);

    // traverse through all elements
    // compare each element with pivot
    for (int j = low; j < high; j++) {
      if (funny.compare(array[j], pivot) == -1) {

        // if element smaller than pivot is found
        // swap it with the greater element pointed by i
        i++;

        // swapping element at i with element at j
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }

    }

    // swap the pivot element with the greater element specified by i
    String temp = array[i + 1];
    array[i + 1] = array[high];
    array[high] = temp;

    // return the position from where partition is done
    return (i + 1);
  }

  static void quickSort(String array[], int low, int high) {
    if (low < high) {

      // find pivot element such that
      // elements smaller than pivot are on the left
      // elements greater than pivot are on the right
      int pi = partition(array, low, high);
      
      // recursive call on the left of pivot
      quickSort(array, low, pi - 1);

      // recursive call on the right of pivot
      quickSort(array, pi + 1, high);
    }
  }
};
