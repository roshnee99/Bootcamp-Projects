package c_restaurant.main;

import c_restaurant.bean.Party;
import c_restaurant.bean.Person;
import c_restaurant.constant.Config;
import c_restaurant.util.RestaurantUtility;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class PartyGenerator {

    private static Map<Integer, Double> partySizeDistribution;
    private static List<String> randomPersonNames;

    /**
     * Every minute, call this method.
     * It will generate a party if it shows up, taking probability into account
     * @return
     */
    public static Optional<Party> generateParty() {
        // if no party coming in at that minute, then return empty
        if (!RestaurantUtility.weightedCoinFlip(Config.PROBABILITY_PARTY_PER_MIN)) {
            return Optional.empty();
        }
        int numMembers = RestaurantUtility.pickItemFromIntMap(getPartySizeDistribution());
        return Optional.of(generatePartyOfSize(numMembers));
    }

    private static Party generatePartyOfSize(int numMembers) {
        List<String> randomNames = getRandomPersonNames();
        List<Person> partyMembers = new ArrayList<>();
        for (int i = 0; i < numMembers; i++) {
            Person person = new Person(generateRandomName(randomNames));
            partyMembers.add(person);
        }
        return new Party(partyMembers);
    }

    private static Map<Integer, Double> getPartySizeDistribution() {
        if (partySizeDistribution == null) {
            partySizeDistribution = new HashMap<>();
            partySizeDistribution.put(1, Config.PROBABILITY_ONE_PERSON_PARTY);
            partySizeDistribution.put(2, Config.PROBABILITY_TWO_PERSON_PARTY);
            partySizeDistribution.put(3, Config.PROBABILITY_THREE_PERSON_PARTY);
            partySizeDistribution.put(4, Config.PROBABILITY_FOUR_PERSON_PARTY);
            partySizeDistribution.put(5, Config.PROBABILITY_FIVE_PERSON_PARTY);
            partySizeDistribution.put(6, Config.PROBABILITY_SIX_PERSON_PARTY);
        }
        return partySizeDistribution;
    }

    private static List<String> getRandomPersonNames() {
        if (randomPersonNames == null) {
            try {
                randomPersonNames = Files.readAllLines(new File("src/main/java/c_restaurant/resources/name.txt").toPath(), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return randomPersonNames;
    }

    private static String generateRandomName(List<String> names) {
        return RestaurantUtility.getRandomObjectFromList(names);
    }

}
