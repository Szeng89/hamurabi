package hammurabi.docs.matuszek;

import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();  // this is an instance variable
    Scanner scanner = new Scanner(System.in);
    int totalDeaths = 0, percentDied = 0, year = 0, population = 95, stores = 2800, immigrants = 5, deaths,
            harvest = 3000, yeild = 3, acres = harvest / yeild, eaten = harvest - stores, landPrice, fullPeople, temp;

    public static void main(String[] args) { // required in every Java program
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        // statements go after the declations
        do {
            newYear();
            acres += askHowManyAcresToBuy(landPrice, stores);
            acres -= askHowManyAcresToSell(acres);
            stores -= askHowGrainToFeedPeople(stores);
        }while(this.year < 10);

    }

    public int askHowManyAcresToBuy(int price, int bushels){
        return 0;
    }

    public int askHowManyAcresToSell(int acresOwned){
        return 0;
    }

    public int askHowGrainToFeedPeople(int bushels) {
        return 0;
    }

    public int askHowManyAcresToPlay(int acresOwned, int population, int bushels) {

        return 0;
    }

    private void newYear() {
        year += 1;
        landPrice = (int) (10 * Math.random() + 17);
        System.out.println(report());
    }



    public int starvationDeaths(int population, int bushelsFedToPeople){
        return 0;
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        return false;
    }

    public int harvest (int acres, int bushelsUsedAsSeed){
        return 0;
    }

    public int landPrice (){
        return 0;
    }


}
