package presentation.models;

public class PersonModel {
    private double[] faceFeatureArray;
    private String personName;
    public PersonModel(String personName, double[] faceFeatureArray) {
        personName = personName;
        faceFeatureArray = faceFeatureArray;
    }

    public double[] getFaceFeatureArray() {
        return faceFeatureArray;
    }

    public void setFaceFeatureArray(double[] faceFeatureArray) {
        this.faceFeatureArray = faceFeatureArray;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
