import distributions.Distributions;
import structures.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.time.Instant;

public class Driver {
  static int c;
  static int s;
  static int k;
  static String dist;
  static int m;
  static int t;
  static int[] e;
  static String[] eTemp;
  static int[] p;
  static Distributions distObj;
  static Fullnode[] adjacencyList;
  static Fullnode[] degreeList;
  static Fullnode[] degreePointers;
  static Fullnode[] orderingList;
  static Lightnode tempLight;
  static Lightnode tempLight2;
  static Fullnode tempFull;
  static Fullnode tempFullDeleted;

  public static void main(String[] args) {

    // C = num of courses
    // S = num of students
    // K = courses per student
    // DIST = type of distribution

    if (args.length != 4) {
      System.out.println(
          "Please provide the command line arguments C, S, K, and DIST in that order when running this driver.");
      return;
    }
    c = Integer.parseInt(args[0]);
    s = Integer.parseInt(args[1]);
    k = Integer.parseInt(args[2]);
    dist = args[3];
    eTemp = new String[c];
    p = new int[c];
    distObj = new Distributions(c, k, dist);

    countConflicts();
    prepareEandP();

    e = new int[2 * m];

    readOutputFromP1();
    // smallestLast();
    welshPowell();

    // int[] temp = distro.skewed();
    // for(int i = 0; i < temp.length; i++) {
    // System.out.println(i + " \t" + temp[i]);
    // }
  }

  public static void createClasses(int s, String dist, Distributions d) throws IOException {
    try {
      FileWriter fw = new FileWriter("ClassesTaken.txt");

      int[] classesTaken;
      for (int i = 0; i < s; i++) {
        classesTaken = d.getDistro(); // get classes for a single student
        for (int k = 0; k < classesTaken.length; k++) {
          fw.write(" " + classesTaken[k]);
        }
        fw.write("\n");
      }
      fw.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static void appendStrToFile(String fileName, String str) {
    try {

      // Open given file in append mode.
      BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
      out.write(str);
      out.close();
    } catch (IOException e) {
      System.out.println("exception occoured" + e);
    }
  }

  public static void countConflicts() {
    m = 0;
    Arrays.fill(eTemp, "");
    int[] classesTaken;
    int tempClass;
    String[] conflicts;
    int conflictToCheck;
    boolean conflictExists;

    Instant start = Instant.now();
    Instant end;

    for (int i = 0; i < s; i++) {

      // FileWriter fw = new FileWriter("ClassesTaken.txt");
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println();
      classesTaken = distObj.getDistro();
      for (int k = 0; k < classesTaken.length; k++) {
        appendStrToFile("ClassesTaken.txt", (" " + classesTaken[k]));
        System.out.print(" " + classesTaken[k]);
      }
      appendStrToFile("ClassesTaken.txt", "\n");
      System.out.println();

      for (int j = 0; j < classesTaken.length; j++) {
        tempClass = classesTaken[j];
        System.out.println("tempClass: " + tempClass);
        conflicts = eTemp[tempClass].split("-");
        for (int k = 0; k < classesTaken.length; k++) {
          if (k != j) {
            System.out.println("e[tempClass] (conflicts for tempClass): " + eTemp[tempClass]);
            conflictToCheck = classesTaken[k];
            conflictExists = false;
            for (int m = 0; m < conflicts.length; m++) {
              System.out.println("conflictToCheck: " + conflictToCheck);
              System.out.println("conflicts[m]: " + conflicts[m]);
              if (conflicts[m].equals(Integer.toString(conflictToCheck))) {
                conflictExists = true;
                System.out.println("conflict already exists");
                break;
              }
            }
            if (!conflictExists) {
              System.out.println("conflict didnt exist");
              eTemp[tempClass] += "" + conflictToCheck + "-";
              eTemp[conflictToCheck] += "" + tempClass + "-";
              m++;
            }
          }
        }
        System.out.println(eTemp[tempClass]);
        System.out.println();
        System.out.println();
      }

    }
    end = Instant.now();
    long timeSpent = end.toEpochMilli() - start.toEpochMilli();
    for (int z = 0; z < eTemp.length; z++) {
      System.out.println(z + ": " + eTemp[z]);
    }
    System.out.println("Time: " + timeSpent);
  }

  public static void prepareEandP() {
    try {
      FileWriter fw = new FileWriter("OutputP1.txt");
      String[] conflicts;
      t = s * ((k * (k - 1)) / 2);
      int pIndex = 0;
      fw.write("" + c + "\n");
      fw.write("" + s + "\n");
      fw.write("" + k + "\n");
      fw.write(dist + "\n");
      fw.write("" + m + "\n");
      fw.write("" + t + "\n");
      for (int i = 0; i < eTemp.length; i++) {
        conflicts = eTemp[i].split("-");
        p[i] = pIndex;
        for (int j = 0; j < conflicts.length; j++) {
          fw.write(conflicts[j] + " ");
          pIndex++;
        }
      }
      fw.write("\n");
      for (int z = 0; z < p.length; z++) {
        fw.write("" + p[z] + " ");
      }
      fw.close();
    } catch (IOException e4) {
      System.out.println(e4);
    }
  }

  public static void readOutputFromP1() {
    File file = new File("OutputP1.txt");
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

      c = Integer.parseInt(br.readLine());
      s = Integer.parseInt(br.readLine());
      k = Integer.parseInt(br.readLine());
      dist = br.readLine();
      m = Integer.parseInt(br.readLine());
      t = Integer.parseInt(br.readLine());
      eTemp = br.readLine().split(" ");
      String[] pTemp = br.readLine().split(" ");

      for (int i = 0; i < eTemp.length; i++) {
        e[i] = Integer.parseInt(eTemp[i]);
      }
      for (int i = 0; i < pTemp.length; i++) {
        p[i] = Integer.parseInt(pTemp[i]);
      }
    } catch (IOException e4) {
      System.out.println(e4);
    }
  }

  // VERTEX ORDERINGS
  public static void smallestLast() {
    prepareColoringDataStructures();
    int minDegree = 0;
    int numOfDeletedNodes = 0;
    while (numOfDeletedNodes != c) {
      for (int i = 0; i < c; i++) {
        tempFull = degreeList[i];
        System.out.print(i + "\t");
        while (tempFull != null) {
          System.out.print(tempFull.name + "->");
          tempFull = tempFull.degreeListNext;
        }
        System.out.println();
      }
      System.out.println();
      System.out.println();
      if (degreeList[minDegree] == null) {
        System.out.println("Current Degree: " + minDegree);
        System.out.println("Nothing at this degree");
        minDegree++;
      } else {
        System.out.println("Current Degree: " + minDegree);
        System.out.println("Deleted Node: " + degreeList[minDegree].name);
        degreeList[minDegree].deleted = 1;
        if(tempFullDeleted == null) {
          tempFullDeleted = degreeList[minDegree];
        }
        else {
          tempFullDeleted.orderingListNext = degreeList[minDegree];
          degreeList[minDegree].orderingListPrev = tempFullDeleted;
          tempFullDeleted = degreeList[minDegree];
        }

        degreeList[minDegree].degreeWhenDeleted = minDegree;
        numOfDeletedNodes++;
        tempLight = degreeList[minDegree].adjListHead;
        // case 2 - first node for that degree
        if ((degreeList[minDegree].degreeListNext != null) && (degreeList[minDegree].degreeListPrev == null)) {
          System.out.println("Original node case 2");
          degreeList[minDegree].degreeListNext.degreeListPrev = null;
          degreeList[minDegree] = degreeList[minDegree].degreeListNext;
        }
        // case 4 - only node for that degree
        else if ((degreeList[minDegree].degreeListNext == null) && (degreeList[minDegree].degreeListPrev == null)) {
          System.out.println("Original node case 4");
          degreeList[minDegree] = null;
          degreePointers[minDegree] = null;
        }
        else {
          System.out.println("SOMETHING IS WRONG");
        }

        for (int i = 0; i < c; i++) {
          tempFull = degreeList[i];
          System.out.print(i + "\t");
          while (tempFull != null) {
            System.out.print(tempFull.name + "->");
            tempFull = tempFull.degreeListNext;
          }
          System.out.println();
        }
        System.out.println();
        while (tempLight != null) {
          if(tempLight.full.deleted != 1) {
            System.out.println("Now looking at: " + tempLight.full.name);
            System.out.println("Deleted field: " + tempLight.full.deleted);
            tempLight.full.degree--;
            // case 1 - middle node
            if ((tempLight.full.degreeListNext != null) && (tempLight.full.degreeListPrev != null)) {
              tempLight.full.degreeListPrev.degreeListNext = tempLight.full.degreeListNext;
              tempLight.full.degreeListNext.degreeListPrev = tempLight.full.degreeListPrev;
              // case 1.1 - there are no nodes in the new(decremented) degree
              if (degreeList[tempLight.full.degree] == null) {
                System.out.println("case 1.1");
                degreeList[tempLight.full.degree] = tempLight.full;
                tempLight.full.degreeListNext = null;
                tempLight.full.degreeListPrev = null;
              }
              // case 1.2 - there is at least one node at the new(decremented) degree, and
              // this node will be added to the head
              else {
                System.out.println("case 1.2");
                degreeList[tempLight.full.degree].degreeListPrev = tempLight.full;
                tempLight.full.degreeListNext = degreeList[tempLight.full.degree];
                tempLight.full.degreeListPrev = null;
                degreeList[tempLight.full.degree] = tempLight.full;
              }
            }
            // case 2 - first node for that degree
            else if ((tempLight.full.degreeListNext != null) && (tempLight.full.degreeListPrev == null)) {
              degreeList[tempLight.full.degree + 1] = tempLight.full.degreeListNext;
              tempLight.full.degreeListNext.degreeListPrev = null;
              // case 2.1 - there are no nodes in the new(decremented) degree
              if (degreeList[tempLight.full.degree] == null) {
                System.out.println("case 2.1");
                degreeList[tempLight.full.degree] = tempLight.full;
                tempLight.full.degreeListNext = null;
                tempLight.full.degreeListPrev = null;
              }
              // case 2.2 - there is at least one node at the new(decremented) degree, and
              // this node will be added to the head
              else {
                System.out.println("case 2.2");
                degreeList[tempLight.full.degree].degreeListPrev = tempLight.full;
                tempLight.full.degreeListNext = degreeList[tempLight.full.degree];
                tempLight.full.degreeListPrev = null;
                degreeList[tempLight.full.degree] = tempLight.full;
              }
            }
            // case 3 - last node for that degree
            else if ((tempLight.full.degreeListNext == null) && (tempLight.full.degreeListPrev != null)) {
              tempLight.full.degreeListPrev.degreeListNext = null;
              degreePointers[tempLight.full.degree + 1] = tempLight.full.degreeListPrev; // keeps degreePointers, which
                                                                                         // tracks the tail of each
                                                                                         // degreeList, current
              // case 3.1 - there are no nodes in the new(decremented) degree
              if (degreeList[tempLight.full.degree] == null) {
                System.out.println("case 3.1");

                degreeList[tempLight.full.degree] = tempLight.full;
                tempLight.full.degreeListNext = null;
                tempLight.full.degreeListPrev = null;
              }
              // case 3.2 - there is at least one node at the new(decremented) degree, and
              // this node will be added to the head
              else {
                System.out.println("case 3.2");
                System.out.println("(tempLight.full.degreeListNext == null) " + tempLight.full.degreeListNext);
                System.out.println("(tempLight.full.degreeListPrev != null).name " + tempLight.full.degreeListPrev.name);
                System.out.println("degreeList[tempLight.full.degree].name " + degreeList[tempLight.full.degree].name);
                System.out.println("tempLight.full.degree: " + tempLight.full.degree);
                System.out.println("minDegree: " + minDegree);
                degreeList[tempLight.full.degree].degreeListPrev = tempLight.full;
                tempLight.full.degreeListNext = degreeList[tempLight.full.degree];
                tempLight.full.degreeListPrev = null;
                degreeList[tempLight.full.degree] = tempLight.full;

              }
            }
            // case 4 - only node for that degree
            else if ((tempLight.full.degreeListNext == null) && (tempLight.full.degreeListPrev == null)) {
              degreeList[tempLight.full.degree + 1] = null;
              // case 4.1 - there are no nodes in the new(decremented) degree
              if (degreeList[tempLight.full.degree] == null) {
                System.out.println("case 4.1");
                degreeList[tempLight.full.degree] = tempLight.full;
                tempLight.full.degreeListNext = null;
                tempLight.full.degreeListPrev = null;
              }
              // case 4.2 - there is at least one node at the new(decremented) degree, and
              // this node will be added to the head
              else {
                System.out.println("case 4.2");
                degreeList[tempLight.full.degree].degreeListPrev = tempLight.full;
                tempLight.full.degreeListNext = degreeList[tempLight.full.degree];
                tempLight.full.degreeListPrev = null;
                degreeList[tempLight.full.degree] = tempLight.full;
              }
            } else {
              System.out.println("Something is very wrong");
            }
            for (int i = 0; i < c; i++) {
              tempFull = degreeList[i];
              System.out.print(i + "\t");
              while (tempFull != null) {
                System.out.print(tempFull.name + "->");
                tempFull = tempFull.degreeListNext;
              }
              System.out.println();
            }
            System.out.println();
          }
          tempLight = tempLight.next;



        }
        minDegree--;
      }
    }
    int[] colors = new int[c];
    int counter = 0;
    int terminalClique = 0;
    int prevColor = -1;
    boolean reachedTerminalClique = false;
    for(int i = 0; i < c; i++) {
      tempLight = tempFullDeleted.adjListHead;
      counter = 0;
      while(tempLight != null) {
        colors[counter] = 0;
        counter++;
        tempLight = tempLight.next;
      }
      tempLight = tempFullDeleted.adjListHead;
      counter = 0;
      while(tempLight != null) {
        if(tempLight.full.color != -1) {
          colors[tempLight.full.color]++;
        }
        counter++;
        tempLight = tempLight.next;
      }
      tempLight = tempFullDeleted.adjListHead;
      counter = 0;
      while(tempLight != null) {
        if(colors[counter] == 0) {
          tempFullDeleted.color = counter;
          break;
        }
        counter++;
        tempLight = tempLight.next;
      }
      if(tempFullDeleted.color == -1) {
        tempFullDeleted.color = colors[counter];
      }

      if((tempFullDeleted.color == (prevColor + 1)) && (!reachedTerminalClique)) {
        terminalClique++;
      }
      else {
        reachedTerminalClique = true;
      }
      System.out.println((c - i) + " name: " + tempFullDeleted.name + " color: " + tempFullDeleted.color + " counter: " + counter);
      prevColor = tempFullDeleted.color;
      tempFullDeleted = tempFullDeleted.orderingListPrev;
    }
    System.out.println("Terminal Clique: " + terminalClique);

  }


  public static void welshPowell() {
    prepareColoringDataStructures();
    int maxDegree =  c - 1;
    int numOfDeletedNodes = 0;

    int[] colors = new int[c];
    int counter = 0;

    while (numOfDeletedNodes != c) {
      for (int i = 0; i < c; i++) {
        tempFull = degreeList[i];
        System.out.print(i + "\t");
        while (tempFull != null) {
          System.out.print(tempFull.name + "->");
          tempFull = tempFull.degreeListNext;
        }
        System.out.println();
      }

      System.out.println();
      System.out.println();
      if (degreeList[maxDegree] == null) {
        System.out.println("Current Degree: " + maxDegree);
        System.out.println("Nothing at this degree");
        maxDegree--;
      }
      else {
        System.out.println("Current Degree: " + maxDegree);
        System.out.println("Deleted Node: " + degreeList[maxDegree].name);

        // degreeList[minDegree].degreeWhenDeleted = minDegree;
        numOfDeletedNodes++;


        tempLight = degreeList[maxDegree].adjListHead;
        counter = 0;
        while (tempLight != null) {
          colors[counter] = 0;
          counter++;
          tempLight = tempLight.next;
        }

        tempLight = degreeList[maxDegree].adjListHead;
        counter = 0;
        while(tempLight != null) {
          if(tempLight.full.color != -1) {
            colors[tempLight.full.color]++;
          }
          counter++;
          tempLight = tempLight.next;
        }

        tempLight = degreeList[maxDegree].adjListHead;
        counter = 0;
        while(tempLight != null) {
          if(colors[counter] == 0) {
            degreeList[maxDegree].color = counter;
            break;
          }
          counter++;
          tempLight = tempLight.next;
        }

        if(degreeList[maxDegree].color == -1) {
          degreeList[maxDegree].color = colors[counter];
        }

        System.out.println(maxDegree + " name: " + degreeList[maxDegree].name + " color: " + degreeList[maxDegree].color + " counter: " + counter);

        if ((degreeList[maxDegree].degreeListNext != null) && (degreeList[maxDegree].degreeListPrev == null)) {
          System.out.println("Original node case 2");
          degreeList[maxDegree].degreeListNext.degreeListPrev = null;
          degreeList[maxDegree] = degreeList[maxDegree].degreeListNext;
        }
        // case 4 - only node for that degree
        else if ((degreeList[maxDegree].degreeListNext == null) && (degreeList[maxDegree].degreeListPrev == null)) {
          System.out.println("Original node case 4");
          degreeList[maxDegree] = null;
          degreePointers[maxDegree] = null;
        }
        else {
          System.out.println("SOMETHING IS WRONG");
        }


      }
    }
  }


  public static void prepareColoringDataStructures() {
    adjacencyList = new Fullnode[c];
    degreeList = new Fullnode[c];
    int tempDegree;
    // initialize full nodes
    for (int i = 0; i < c; i++) {
      adjacencyList[i] = new Fullnode(i);
    }

    // populate adjacency list
    for (int i = 0; i < c; i++) { // go through each vertex

      if (i != c - 1) { // handles edge case of p[i + 1] being out of range
        for (int j = p[i]; j < p[i + 1]; j++) { // go through edges of vertex
          if (j == p[i]) { // first case is a special case
            tempLight = new Lightnode(adjacencyList[e[j]]);
            adjacencyList[i].adjListHead = tempLight; // make the head of the adjacency list the first edge
            adjacencyList[i].degree++;
          } else {
            tempLight.next = new Lightnode(adjacencyList[e[j]]);
            tempLight = tempLight.next;
            adjacencyList[i].degree++;
          }
        }
      } else { // handles edge case of p[i + 1] being out of range
        for (int j = p[p.length - 1]; j < e.length; j++) {
          if (j == p[p.length - 1]) { // go through edges of vertex
            tempLight = new Lightnode(adjacencyList[e[j]]);
            adjacencyList[i].adjListHead = tempLight; // make the head of the adjacency list the first edge
            adjacencyList[i].degree++;
          } else {
            tempLight.next = new Lightnode(adjacencyList[e[j]]);
            tempLight = tempLight.next;
            adjacencyList[i].degree++;
          }
        }
      }
    }

    degreePointers = new Fullnode[c]; // holds the tail for each degree so that there is no need to iterate through
                                      // each node in the degree to find the tail

    for (int i = 0; i < c; i++) {
      tempDegree = adjacencyList[i].degree;
      if (degreeList[tempDegree] == null) {
        degreeList[tempDegree] = adjacencyList[i];
        degreePointers[tempDegree] = degreeList[tempDegree];
      } else {
        degreePointers[tempDegree].degreeListNext = adjacencyList[i];
        adjacencyList[i].degreeListPrev = degreePointers[tempDegree];
        degreePointers[tempDegree] = adjacencyList[i];
      }
    }

    // PRINTING DEGREE LIST
    // for(int i = 0; i < c; i++) {
    // tempFull = degreeList[i];
    // System.out.print(i + "\t");
    // while(tempFull != null) {
    // System.out.print(tempFull.name + "->");
    // tempFull = tempFull.degreeListNext;
    // }
    // System.out.println();
    // }

    // PRINTING ADJACENCY LIST
    // for(int i = 0; i < c; i++) { // go through each vertex
    // System.out.print(adjacencyList[i].degree + "\t");
    // System.out.print(adjacencyList[i].name + "->");
    // if(i != c - 1) { // handles edge case of p[i + 1] being out of range
    // for(int j = p[i]; j < p[i + 1]; j++) { // go through edges of vertex
    // if(j == p[i]) { // first case is a special case
    // // temp = new Lightnode(adjacencyList[e[j]]);
    // // adjacencyList[i].adjListHead = temp; // make the head of the adjacency
    // list the first edge
    // tempLight = adjacencyList[i].adjListHead;
    // System.out.print(tempLight.full.name + "->");
    // }
    // else {
    // // temp.next = new Lightnode(adjacencyList[e[j]]);
    // tempLight = tempLight.next;
    // System.out.print(tempLight.full.name + "->");

    // }
    // }
    // }
    // else { // handles edge case of p[i + 1] being out of range
    // for(int j = p[p.length - 1]; j < e.length; j++) {
    // if(j == p[p.length - 1]) { // go through edges of vertex
    // // temp = new Lightnode(adjacencyList[e[j]]);
    // // adjacencyList[i].adjListHead = temp; // make the head of the adjacency
    // list the first edge
    // tempLight = adjacencyList[i].adjListHead;
    // System.out.print(tempLight.full.name + "->");
    // }
    // else {
    // // temp.next = new Lightnode(adjacencyList[e[j]]);
    // // temp = temp.next;
    // tempLight = tempLight.next;
    // System.out.print(tempLight.full.name + "->");
    // }
    // }
    // }
    // System.out.println();
    // }

  }
}
