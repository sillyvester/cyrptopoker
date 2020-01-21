package distributions;

import java.util.Random;

public class Distributions {
  // num courses per student
  private int K;
  // num courses being offered
  private int C;

  private int[] allClasses;

  private Random rand;

  private String dist;

  // CONSTRUCTORS
  public Distributions() {

  }

  public Distributions(int c, int k, String distro) {
    C = c;
    K = k;
    dist = distro;
    rand = new Random(99); // using 87 as seed to keep results consistent
    allClasses = new int[C];
    for(int i = 0; i < allClasses.length; i++) {
      allClasses[i] = i;
    }
  }

  public int[] getDistro() {
    if(dist.toLowerCase() == "skewed") {
      return skewed();
    }
    else if(dist.toLowerCase() == "4-tiered") {
      return fourTiered();
    }
    else if(dist.toLowerCase() == "gaussian") {
      return gaussian();
    }
    else {
      return uniform();
    }
  }

  // DISTRIBUTIONS
  public int[] uniform() {
    return fisherYates(allClasses);
  }

  public int[] skewed() {
    return skewedFisherYates(allClasses);
  }

  public int[] fourTiered() {
    return tieredFisherYates(allClasses);
  }

  public int[] gaussian() {
    return gaussianFisherYates(allClasses);
  }


  // FISHER YATES SECTION
  public int[] fisherYates(int[] classes) {
    int[] assigned = new int[K];
    int randVal;
    int randElement;
    // System.out.println(classes.length);
    // System.out.println(assigned.length);
    for(int i = 0; i < assigned.length; i++) {
      randVal = i + rand.nextInt(classes.length - i); // get random index greater than current index

      // get the element at the random index
      randElement = classes[randVal];
      // the following two lines swap the random element and current element
      classes[randVal] = classes[i];
      classes[i] = randElement;
      // add the random element which is now at the current index to the assigned classes
      assigned[i] = classes[i];
    }

    return assigned;
  }

  public int[] skewedFisherYates(int[] classes) {
    int[] assigned = new int[K];
    int randVal;
    int randElement;
    for(int i = 0; i < assigned.length; i++) {
      randVal = i + Math.min(rand.nextInt(classes.length - i), rand.nextInt(classes.length - i)); // get random index greater than current index, the min function linearly skews the data as needed

      // get the element at the random index
      randElement = classes[randVal];
      // the following two lines swap the random element and current element
      classes[randVal] = classes[i];
      classes[i] = randElement;
      // add the random element which is now at the current index to the assigned classes
      assigned[i] = classes[i];
    }

    return assigned;
  }

    public int[] tieredFisherYates(int[] classes) {
    int[] assigned = new int[K];
    int randVal;
    int randElement;
    int tier;
    int quarter;
    for(int i = 0; i < assigned.length; i++) {
      tier = rand.nextInt(100);
      quarter = (classes.length - i) / 4;
      if(tier < 40) {
        // need to figure out how to correctly fisher yates dis biiiish
        randVal = i + rand.nextInt();
      }
      else if((40 <= tier) && (tier < 70)) {
        randVal = i + rand.nextInt(quarter);
      }
      else if((70 <= tier) && (tier < 90)) {
        randVal = i + rand.nextInt(classes.length - i);
      }
      else if((90 <= tier) && (tier < 100)) {
        randVal = i + rand.nextInt(classes.length - i);
      }
      else {
        System.out.println("something bad happened");
        break;
      }

      // get the element at the random index
      randElement = classes[randVal];
      // the following two lines swap the random element and current element
      classes[randVal] = classes[i];
      classes[i] = randElement;
      // add the random element which is now at the current index to the assigned classes
      assigned[i] = classes[i];
    }

    return assigned;
  }



  public int[] gaussianFisherYates(int[] classes) {
    int[] temp = {0};
    return temp;
  }



}




  // public int[] fisherYates(int[] classes) {
  //   int[] assigned = new int[K];
  //   int randVal;
  //   int randElement;
  //   System.out.println(classes.length);
  //   System.out.println(assigned.length);
  //   for(int i = 0; i < assigned.length; i++) {
  //     randVal = i + rand.nextInt(classes.length - i); // get random index greater than current index

  //     // get the element at the random index
  //     randElement = classes[randVal];
  //     // the following two lines swap the random element and current element
  //     classes[randVal] = classes[i];
  //     classes[i] = randElement;
  //     // add the random element which is now at the current index to the assigned classes
  //     assigned[i] = classes[i];
  //   }

  //   return assigned;
  // }
