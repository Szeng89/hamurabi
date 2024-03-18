package hammurabi.src.main;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.processing.Processor;
import java.io.ByteArrayInputStream;

public class HammurabiTest {

    @Test
    public void askHowManyAcresToBuy() {

        // Given
        Hammurabi hammurabi = new Hammurabi();

        Integer bushelsPerAcreValue = 19;
        Integer grain = 19;

        Integer expectedAcresBought = bushelsPerAcreValue / grain;

        //when
        Integer actualAcresBought = hammurabi.askHowManyAcresToBuy(bushelsPerAcreValue,grain);

        //then
        Assert.assertEquals(expectedAcresBought, actualAcresBought);
    }
    @Test
    public void askHowManyAcresToBuy2() {
        // Given
        Hammurabi hammurabi = new Hammurabi();
        String data = "30";
        // This is taking the testStringInput and putting it into the system for the scanner to read thereby mocking the data
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Integer bushelsPerAcreValue = 19;
        Integer grain = 19;

        Integer expectedAcresBought = bushelsPerAcreValue / grain;

        //when
        Integer actualAcresBought = hammurabi.askHowManyAcresToBuy(bushelsPerAcreValue,grain);

        //then
        Assert.assertEquals(expectedAcresBought, actualAcresBought);
    }


    @Test
    public void askHowManyAcresToSell() {
    }

    @Test
    public void askHowMuchGrainToFeedPeople() {
    }

    @Test
    public void askHowManyAcresToPlant() {
    }
}