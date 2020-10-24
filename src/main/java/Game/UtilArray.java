package Game;

import Model.Country;

import java.util.Arrays;
/**
 * @author      Hasan Issa
 *
 * Java program to remove an element from a specific index from an array.
 * In our situation, it handles removing a country from an array.
 *
 */
public class UtilArray {

    // Function to remove the element
    public static Country[] removeTheElement(Country[] arr,
                                         int index)
    {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        Country[] anotherArray = new Country[arr.length - 1];

        // Copy the elements except the index
        // from original array to the other array
        for (int i = 0, k = 0; i < arr.length; i++) {


            // if the index is
            // the removal element index
            if (i == index) {
                continue;
            }

            // if the index is not
            // the removal element index
            anotherArray[k++] = arr[i];
        }

        // return the resultant array
        return anotherArray;
    }
}
