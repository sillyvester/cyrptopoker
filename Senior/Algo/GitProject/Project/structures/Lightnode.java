package structures;

public class Lightnode {

  public Fullnode full;
  public Lightnode next;

  public Lightnode() {

  }

  public Lightnode(Fullnode full) {
    this.full = full;
  }

  public Lightnode(Fullnode full, Lightnode next) {
    this.full = full;
    this.next = next;
  }


}
