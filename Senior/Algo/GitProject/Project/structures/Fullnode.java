package structures;


public class Fullnode {

  public int originalDegree;
  public int degree;
  public int name;
  public Lightnode adjListHead;
  public Fullnode degreeListPrev;
  public Fullnode degreeListNext;
  public int deleted;
  public int color;
  public Fullnode orderingListNext;
  public Fullnode orderingListPrev;
  public int degreeWhenDeleted;

  public Fullnode() {

  }

  public Fullnode(int name) {
    this.name = name;
    this.deleted = 0;
    this.color = -1;
    this.degreeListNext = null;
    this.degreeListPrev = null;
    this.degreeWhenDeleted = -1;
    this.adjListHead = null;
    this.orderingListNext = null;
    this.orderingListPrev = null;
  }

  public Fullnode(int degree, int name,  Lightnode adjListHead, Fullnode degreeListPrev, Fullnode degreeListNext, Fullnode orderingListNext) {
    this.degree = degree;
    this.name = name;
    this.adjListHead = adjListHead;
    this.degreeListPrev = degreeListPrev;
    this.degreeListNext = degreeListNext;
    this.deleted = 0;
    this.color = 0;
    this.orderingListNext = orderingListNext;
  }

}
