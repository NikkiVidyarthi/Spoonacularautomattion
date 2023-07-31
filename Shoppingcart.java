package datamodel;

public class Shoppingcart {
    int id;
    String title;
    String image;
    int likes;
    double cups;
    int gramsPerCup;


    public int getId() {return id;}
    public void setId(int Id) { this.id = id;}


    public String getTitle() {return title;}
    public void setTitle( String title){ this.title = title;}

    public String getImage() {return image;}
    public void setImage( String image) { this.image = image;}

    public int getLikes() {return likes;}
    public void setLikes(int likes){this.likes = likes;}

    public double getCups() {return cups;}
    public void setCups( double cups) {  this.cups = cups;}

    public int getGramsPerCup() {return gramsPerCup;}
    public void setGramsPerCup( int gramsPerCup){  this.gramsPerCup = gramsPerCup;}

    public double getTotalGrams() {
        return cups * gramsPerCup;
    }


    @Override
    public String toString() {
        return "ID: " + id + "\nTitle: " + title + "\nImage: " + image + "\nLikes: " + likes +
                "\nCUPS(1 cup eq to 100 gm): " + cups + "\nGRAMS: " + getTotalGrams() + "\n";
    }
}

