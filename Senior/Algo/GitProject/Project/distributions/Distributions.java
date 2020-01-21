package distributions;

import java.util.Random;

public class Distributions {
  // num courses per student
  private int K;
  // num courses being offered
  private int C;

  public int[] allClasses;

  private Random rand;

  private String dist;

  // CONSTRUCTORS
  public Distributions() {

  }

  // use 99 as seed to replicate results consistent


  public Distributions(int c, int k, String distro) {
    C = c;
    K = k;
    dist = distro;
    rand = new Random();
    allClasses = new int[C];
    formatClassArray();
  }

  public int[] getDistro() {
    if(dist.toLowerCase().equals("skewed")) {
      // System.out.println("skewed");
      return skewed();
    }
    else if(dist.toLowerCase().equals("4-tiered")) {
      // System.out.println("4-tiered");
      return fourTiered();
    }
    else {
      // System.out.println("uniform");
      return uniform();
    }
  }

  // DISTRIBUTIONS
  public int[] uniform() {
    return fisherYates();
  }

  public int[] skewed() {
    formatClassArray();
    return skewedFisherYates();
  }

  public int[] fourTiered() {
    formatClassArray();
    return tieredFisherYates();
  }



  // FISHER YATES SECTION
  public int[] fisherYates() {
    int[] assigned = new int[K];
    int randVal;
    int randElement;
    // System.out.println(allClasses.length);
    // System.out.println(assigned.length);
    for(int i = 0; i < assigned.length; i++) {
      randVal = i + rand.nextInt(allClasses.length - i); // get random index greater than current index

      // get the element at the random index
      randElement = allClasses[randVal];
      // the following two lines swap the random element and current element
      allClasses[randVal] = allClasses[i];
      allClasses[i] = randElement;
      // add the random element which is now at the current index to the assigned allClasses
      assigned[i] = allClasses[i];
    }

    return assigned;
  }

  public int[] skewedFisherYates() {
    int[] assigned = new int[K];
    int randVal;
    int randElement;
    for(int i = 0; i < assigned.length; i++) {
      // get random index greater than current index, the min function linearly skews the data as needed
      randVal = i + Math.min(rand.nextInt(allClasses.length - i), rand.nextInt(allClasses.length - i));

      // get the element at the random index
      randElement = allClasses[randVal];
      // the following two lines swap the random element and current element
      allClasses[randVal] = allClasses[i];
      allClasses[i] = randElement;
      // add the random element which is now at the current index to the assigned allClasses
      assigned[i] = allClasses[i];
    }

    return assigned;
  }

    public int[] tieredFisherYates() {
    int[] assigned = new int[K];
    int randVal;
    int randElement;
    int tier;
    int firstQuarter;
    int secondQuarter;
    int thirdQuarter;
    int fourthQuarter;
    for(int i = 0; i < assigned.length; i++) {
      tier = rand.nextInt(100);
      firstQuarter = (allClasses.length - i) / 4;
      secondQuarter = ((allClasses.length - i) / 2 ) - ((allClasses.length - i) / 4);
      thirdQuarter = ((allClasses.length - i) / 2) + ((allClasses.length - i) / 4);
      fourthQuarter = (allClasses.length - i) - ((allClasses.length - i) / 4);
      if(tier < 40) {
        // need to figure out how to correctly fisher yates dis biiiish
        randVal = i + rand.nextInt(firstQuarter);
      }
      else if((40 <= tier) && (tier < 70)) {
        randVal = i + rand.nextInt(secondQuarter);
      }
      else if((70 <= tier) && (tier < 90)) {
        randVal = i + rand.nextInt(thirdQuarter);
      }
      else if((90 <= tier) && (tier < 100)) {
        randVal = i + rand.nextInt(fourthQuarter);
      }
      else {
        System.out.println("something bad happened");
        break;
      }

      // get the element at the random index
      randElement = allClasses[randVal];
      // the following two lines swap the random element and current element
      allClasses[randVal] = allClasses[i];
      allClasses[i] = randElement;
      // add the random element which is now at the current index to the assigned allClasses
      assigned[i] = allClasses[i];
    }

    return assigned;
  }

  public void formatClassArray() {
    for(int i = 0; i < allClasses.length; i++) {
      allClasses[i] = i;
    }
  }

}
