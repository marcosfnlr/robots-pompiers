public enum Couleur{
 RED("#f43827"), BLUE("#1123c4"), GREEN("#20541c"), BROWN("#3d2d22"), WHITE("#ffffff"), PURPLE("#d14fc4");

 private Couleur(String color){
    this.color = color;
  }

  private String color;

  public String getColor(){
    return this.color;
  }

  public String toString(){
    return this.color;
  }
}
