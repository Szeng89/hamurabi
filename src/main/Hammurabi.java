package hammurabi.src.main;
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
//    private Integer bushelsEatenByRat = 0;


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
            setNewLandPrice();
            System.out.println("The price of land is now " + getLandPrice() + " bushels per acre.");

            Integer acresBought = askHowManyAcresToBuy(getLandPrice(), grain);
            landAcres += acresBought;
            if (acresBought == 0) {
                landAcres -= askHowManyAcresToSell(landAcres);
            }

            // Ask how much grain to feed people
            Integer grainFedToPeople = askHowMuchGrainToFeedPeople(grain);
            grain -= grainFedToPeople; // Deduct the grain used to feed people

            askHowManyAcresToPlant(landAcres, people, grain);

            //handle starvation deaths , updating the population
            peopleStarved = starvationDeaths(people, grainFedToPeople);
            people -= peopleStarved;

            if (people <= 0) {
                System.out.println("Game Over. Your entire population has starved.");
                gameOver = true;
                break;
            }


//            people += cameToTown;

            boolean hasUprising = uprising(people, peopleStarved);
            if (hasUprising) {
                System.out.println("An uprising has occurred due to the high number of starvation deaths.");
                gameOver = true;
            }

            year++;
            yearlyMessage();

            if (year == 10) {
                gameOver = true;
                System.out.println("Game Over. You've completed 10 years of rule.");
            }
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
                        "\nYou harvested 2 bushels per acre."
//                        "\nRats ate %d bushels.\nYou now have %d bushels in store.\n"   bushelsEatenByRat


                , year,peopleStarved,cameToTown, people, landAcres, grain);
    }
    
    public Integer askHowManyAcresToBuy(Integer bushelsPerAcreValue, Integer
    grain) {
        System.out.println("How many acres would you like to buy?");

        while (true) { // Keep asking until a valid input is received
            try {
                Integer acresToBuy = scanner.nextInt(); // Read the user's input

                // Calculate the cost of acres
                Integer cost = acresToBuy * bushelsPerAcreValue;

                if (cost <= grain) {
                    // The player has enough grain to buy acres
                    return acresToBuy;
                } else {
                    // The player does not have enough grain
                    System.out.println("You do not have enough grain to buy this much land. Please try again.");
                }
            } catch (InputMismatchException e) {
                // Handle the case where the input was not an integer
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input before asking again
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
                scanner.nextLine(); // Clear the invalid input before asking again
            }
        }
    }

    public Integer askHowMuchGrainToFeedPeople(Integer grain) {
        System.out.println("How many bushels of grain would you like to feed your citizens?");

        while (true) { // Keep asking until a valid input is given
            try {
                Integer bushelsToFeed = scanner.nextInt(); // Read the user's input

                if (bushelsToFeed >= 0 && bushelsToFeed <= grain) {
                    // Check if the amount to feed is within the available grain
                    int peopleStarved = starvationDeaths(people, grain);
                    people -= peopleStarved; // Update the population after feeding

                    // Check if the starvation leads to game over
                    if (people <= 0) {
                        System.out.println("Everyone has starved. Game Over.");
                        gameOver = true;
                    }

                    return bushelsToFeed;
                } else {
                    // The user entered a number that is either negative or more than available grain
                    System.out.println("That amount is not valid. You cannot feed your citizens more grain than you have or a negative amount. Please enter a valid number.");
                }
            } catch (InputMismatchException e) {
                // The user did not enter an integer
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input before asking again
            }
        }
    }

    public void askHowManyAcresToPlant(Integer landAcres, Integer people, Integer grain) {
        System.out.println("How many acres will you plant?");

        while (true) { // Keep asking until a valid input is provided
            try {
                Integer acresToPlant = scanner.nextInt(); // Read the user's input

                // Calculate the maximum acres that can be planted by one person
                Integer maxAcresPerPerson = people * 10;

                // Calculate the grain needed for planting (assuming 2 bushels per acre)
                Integer grainNeeded = acresToPlant * 2;

                if (acresToPlant <= landAcres && acresToPlant <= maxAcresPerPerson && grainNeeded <= grain) {
                    // Player has enough land, people, and grain to plant the acres
                    return;
                } else {
                    // The player does not have enough resources
                    System.out.println("Cannot plant that many acres. You might not have enough land, people, or grain. Try again.");
                }
            } catch (InputMismatchException e) {
                // Non-integer value
                System.out.println("Invalid input. Please enter a valid number of acres.");
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
    }

    public Integer starvationDeaths(Integer people, Integer grain) {
        Integer grainPerPerson = 20; // Assuming each person needs 20 bushels to survive
        Integer peopleFed = grain / grainPerPerson;
        return Math.max(0, people - peopleFed); // Ensure we don't return a negative number
    }

    public boolean uprising(Integer people, Integer howManyPeopleStarved) {
        double percentageOfPeopleStarved = (double) howManyPeopleStarved / people * 100;
        return percentageOfPeopleStarved > 45;
    }

    public void setNewLandPrice() {
        // Generate a random price between 17 and 23
        bushelsPerAcreValue = random.nextInt((23 - 17) + 1) + 17;
    }

    public Integer getLandPrice() {
        return bushelsPerAcreValue;
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