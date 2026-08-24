package za.ac.cput.aquavoltsa.Domain;

/**
 * Student Number: 251239853
 *
 * @author Aphelele Shange
 */
public class Utility {

    int utilityID;
    String utilityName;

    public Utility(int utilityID, String utilityName) {
        this.utilityID = utilityID;
        this.utilityName = utilityName;
    }

    public int getUtilityID() {
        return utilityID;
    }

    public void setUtilityID(int utilityID) {
        this.utilityID = utilityID;
    }

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

}
