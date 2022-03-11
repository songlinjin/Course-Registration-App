package model;

import java.util.ArrayList;


import java.util.List;

// Representing list of CourseWorkList with the total credits from all WorkList(term) from a degree process perspective
public class Terms {
    public static final int GRADUATION_REQUIREMENT = 120;

    public List<CourseWorkList> termsList;
    public int sumOfAllCredits;
    public int singleTermCredits;
    public int remainingCredits; // represents credits remaining to graduate
    public int honorBonus; // represents credits that a student complete beyond the graduation requirement




    // EFFECTS: initiate a empty term list
    public Terms() {
        termsList = new ArrayList<CourseWorkList>();

    }

    // EFFECTS: return true if the termsList contains a specific CourseWorkList, otherwise return false
    public boolean containInTermsList(CourseWorkList w) {
        return termsList.contains(w);
    }

    // MODIFIES: this
    // EFFECTS: add a CourseWorkList w to the termsList adn return true if w is not already added, otherwise return
    //          false
    public boolean addTerm(CourseWorkList w) {
        if (! containInTermsList(w)) {
            termsList.add(w);
            return true;
        } else {
            return false;

        }
    }


    // EFFECTS: get the number of CourseWorkList in TermsList
    public int getNumberOfTerms() {
        return termsList.size();
    }

    // MODIFIES: this
    // EFFECTS: return the sum of all credits from all terms
    public int sumCredits() {
        sumOfAllCredits = 0;
        for (CourseWorkList w: termsList) {
            singleTermCredits = w.getTotalRegisteredCredits();
            sumOfAllCredits += singleTermCredits;
        }
        return sumOfAllCredits;
    }


    // MODIFIES: this
    // EFFECTS: calculate the remaining credits to graduate if the credits earned so far is <= GRADUATION_REQUIREMENT
    //          otherwise, the remaining credits to graduate is zero and calculate the honor bonus credits
    public int remainingCreditsToGraduate() {
        if (sumOfAllCredits == 0) {
            remainingCredits = GRADUATION_REQUIREMENT;
            return remainingCredits;
        }
        if (sumOfAllCredits < GRADUATION_REQUIREMENT) {
            remainingCredits = GRADUATION_REQUIREMENT - sumOfAllCredits;
            return remainingCredits;
        }

        if (sumOfAllCredits == GRADUATION_REQUIREMENT) {
            remainingCredits = 0;
            return remainingCredits;
        } else {
            remainingCredits = 0;
            honorBonus = sumOfAllCredits - GRADUATION_REQUIREMENT;
            return remainingCredits;
        }

    }

    // EFFECTS: return the credits earned exceed the GRADUATION_REQUIREMENT(i.e. honor bonus credits)
    public int getBonus() {
        return honorBonus;
    }


}
