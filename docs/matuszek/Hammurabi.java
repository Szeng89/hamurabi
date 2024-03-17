package hammurabi.docs.matuszek;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    private Integer people = 100;
    private Integer grain = 2800;
    private Integer landAcres = 1000;
    private Integer bushelsPerAcreValue = 0;
    private boolean gameOver = false;
    private Integer year = 0;
    private Integer peopleStarved = 0;
    private Integer cameToTown = 0;
    private Integer bushelsEatenByRat = 0;


    Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    public Hammurabi() {
    }

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }
    void playGame() {
        startGameMessage();
        while (!gameOver) {
            // Set the new land price at the beginning of each year
            setNewLandPrice();

            // Inform the player of the new land price
            System.out.println("The price of land is now " + getLandPrice() + " bushels per acre.");

            // Ask how many acres to buy and get the number of acres bought
            Integer acresBought = askHowManyAcresToBuy(getLandPrice(), grain);
            landAcres += acresBought;

            // Only ask how many acres to sell if no acres were bought
            if (acresBought == 0) {
                landAcres -= askHowManyAcresToSell(landAcres);
            }

            grain -= askHowMuchGrainToFeedPeople(grain);
            askHowManyAcresToPlant(landAcres, people, grain); // Adjusted to use updated method parameters
            year++;
            yearlyMessage();
            // Additional logic to check game over conditions
        }
    }



    public void startGameMessage() {
        System.out.println("O great Hammurabi!\n" +
                "You are in year 1 of your ten year rule.\n" +
                "In the previous year 0 people starved to death.\n" +
                "In the previous year 5 people entered the kingdom.\n" +
                "The population is now 100.\n" +
                "We harvested 3000 bushels at 3 bushels per acre.\n" +
//                "Rats destroyed 200 bushels, leaving 2800 bushels in storage.\n" +
                "The city owns 1000 acres of land.\n");
    }

    public void yearlyMessage() {
        System.out.printf("Hamurabi:\nI beg to report to you in year %d, %d people starved, %d came to the city." +
                        "\nPopulation is now %d." +
                        "\nThe city now owns %d acres." +
                        "\nYou harvested 2 bushels per acre." +
                        "\nRats ate %d bushels.\nYou now have %d bushels in store.\n"
                , year, peopleStarved, cameToTown, people, landAcres, bushelsEatenByRat, grain);
    }

    public Integer askHowManyAcresToBuy(Integer bushelsPerAcreValue, Integer grain) {
        System.out.println("How many acres would you like to buy?");

        while (true) { // Keep asking until a valid input is received
            try {
                Integer acresToBuy = scanner.nextInt(); // Attempt to read the user's input

                // Calculate the cost of the requested acres
                Integer cost = acresToBuy * bushelsPerAcreValue;

                if (cost <= grain) {
                    // The player has enough grain to buy the requested acres
                    return acresToBuy;
                } else {
                    // The player does not have enough grain
                    System.out.println("You do not have enough grain to buy this much land. Please try again.");
                }
            } catch (InputMismatchException e) {
                // Handle the case where the input was not an integer
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input before trying again
            }
        }
    }

    public Integer askHowManyAcresToSell(Integer landAcres) {
        System.out.println("How many acres would you like to sell?");

        while (true) { // Loop until a valid input is received
            try {
                Integer acresToSell = scanner.nextInt(); // Read user input

                // Check if the input is a valid number of acres to sell
                if (acresToSell >= 0 && acresToSell <= landAcres) {
                    // User has entered a valid number, so return this number
                    return acresToSell;
                } else {
                    // User's input is not valid (trying to sell more land than owned or a negative number)
                    System.out.println("Invalid amount. You can't sell more acres than you own or a negative number. Please enter a valid number.");
                }
            } catch (InputMismatchException e) {
                // Catch if the user does not enter an integer
                System.out.println("That's not a number. Please enter a valid number.");
                scanner.nextLine(); // Clear the scanner's input buffer to handle the next input correctly
            }
        }
    }
    

    //UNUSED GOT FROM NICk

//    public boolean uprising(int population, int howManyPeopleStarved) {
//        Double uprising = 0.45;
//        Double percentageOfPeopleStarved = (double) howManyPeopleStarved / population;
//        return percentageOfPeopleStarved >= uprising;
//    }
//
//    public Integer immigrants(int population, int acresOwned, int grainInStorage) {
//        Integer immigrantsImmigrating = (20 * acresOwned + grainInStorage) / (100 * population) + 1;
//        return immigrantsImmigrating;
//    }
//
//    public Integer harvest(int acres, int bushelsUsedAsSeed) {
//        Integer yield = random.nextInt(6) +1;
//        Integer harvested = acres * yield;
//        return harvested;
//    }
//
//    public Integer grainEatenByRats(int bushels) {
//        Integer grainEaten = 0;
//        Double grainEatenProbability = .10 + random.nextDouble() * .20;
//        if(random.nextDouble() <= .40) {
//            grainEaten = (int) (bushels * grainEatenProbability);
//        }
//        return grainEaten;
//    }
//
//    public Integer newCostOfLand(){
//        Integer newCost = random.nextInt(7) + 17;
//        return newCost;
//    }
//    public Integer plagueDeaths(int population) {
//        population = people /2;
//        return population;
//    }


}