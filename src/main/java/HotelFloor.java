import java.util.ArrayList;

public class HotelFloor {
    protected final int numberOfMainCorridors;
    protected final int numberOfSubCorridors;
    protected ArrayList<Boolean> mainCorridorAC;
    protected ArrayList<Boolean> subCorridorAC;
    protected ArrayList<Boolean> mainCorridorsLight;
    protected ArrayList<Boolean> subCorridorsLight;
    protected int totalElectricityConsumption;
    protected int electricityConsumptionLimit;


    public HotelFloor(int numberOfMainCorridors, int numberOfSubCorridors) {
        this.numberOfMainCorridors = numberOfMainCorridors;
        this.numberOfSubCorridors = numberOfSubCorridors;
        totalElectricityConsumption = 0;
        electricityConsumptionLimit = (numberOfMainCorridors * 15 + numberOfSubCorridors * 10);
        mainCorridorsLight = new ArrayList<>();
        subCorridorsLight = new ArrayList<>();
        mainCorridorAC = new ArrayList<>();
        subCorridorAC = new ArrayList<>();
        for (int i = 0; i < numberOfSubCorridors; i++) {
            subCorridorsLight.add(false);
            subCorridorAC.add(true);
        }

        for (int i = 0; i < numberOfMainCorridors; i++) {
            mainCorridorsLight.add(true);
            mainCorridorAC.add(true);
        }
    }

    public boolean checkWhetherAcAreSwitchedOn() {
        return !(mainCorridorAC.contains(false) || subCorridorAC.contains(false));

    }


    public boolean checkWhetherSubCorridorsLightsAreOff() {
        return !subCorridorsLight.contains(true);
    }


    public boolean checkWhetherMainCorridorsLightsAreTurnedOn() {
        return !mainCorridorsLight.contains(false);
    }

    public void movementDetected(int subCorridor) {
        subCorridorsLight.set(subCorridor - 1, true);
    }

    public boolean isLightTurnedOn(int subCorridor) {

        return (subCorridorsLight.get(subCorridor - 1));
    }

    public void normalizeElectricityConsumption(int subCorridor) {
        for (Boolean subCorridorsLightTurnedOn : subCorridorsLight)
            if (subCorridorsLightTurnedOn) totalElectricityConsumption += Appliance.LIGHT.getUnitConsumption();
        for (Boolean mainCorridorLightTurnedOn : mainCorridorsLight)
            if (mainCorridorLightTurnedOn) totalElectricityConsumption += Appliance.LIGHT.getUnitConsumption();
        for (Boolean subCorridorsACTurnedOn : subCorridorAC)
            if (subCorridorsACTurnedOn) totalElectricityConsumption += Appliance.AC.getUnitConsumption();
        for (Boolean mainCorridorACTurnedOn : mainCorridorAC)
            if (mainCorridorACTurnedOn) totalElectricityConsumption += Appliance.AC.getUnitConsumption();
        if (checkWhetherPowerConsumptionExceeded()) {

            while (checkWhetherPowerConsumptionExceeded()) {

                for (int i = 0; i < numberOfSubCorridors; i++) {
                    if (i == (subCorridor-1)) continue;
                    if (subCorridorAC.get(i-1)) {
                        subCorridorAC.set(i-1, false);
                        totalElectricityConsumption -= Appliance.AC.getUnitConsumption();
                        if (checkWhetherPowerConsumptionExceeded()) break;
                    }
                }

            }
        }

    }

    private boolean checkWhetherPowerConsumptionExceeded() {
        return (totalElectricityConsumption > electricityConsumptionLimit);

    }

}



