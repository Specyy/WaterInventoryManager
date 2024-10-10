package ca.purification.inventory.viewmodel;

import ca.purification.inventory.model.ModelId;
import ca.purification.inventory.model.PurificationUnit;
import ca.purification.inventory.model.PurificationUnitManager;
import ca.purification.inventory.model.SerialNumber;
import ca.purification.inventory.model.serialization.LocalDateAdapter;
import ca.purification.inventory.model.serialization.ModelIdAdapter;
import ca.purification.inventory.model.serialization.SerialNumberAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;

/**
 * The {@code ReadFileViewModel} class provides functionality for reading 
 * and loading {@code PurificationUnit} instances from a JSON file into 
 * a {@code PurificationUnitManager}. It uses the Gson library to 
 * deserialize JSON data into Java objects and manage the unit data 
 * accordingly.
 *
 * <p>This class allows for the easy loading of purification units from 
 * a specified JSON file. It handles the conversion of various types 
 * including {@code LocalDate}, {@code SerialNumber}, and {@code ModelId}
 * through registered type adapters.</p>
 *
 * <p>The loaded units replace any existing units in the manager, ensuring 
 * that the manager contains the most up-to-date data.</p>
 *
 * @see PurificationUnit
 * @see PurificationUnitManager
 * @see LocalDateAdapter
 * @see SerialNumberAdapter
 * @see ModelIdAdapter
 * @see ViewModel
 */

public class ReadFileViewModel implements ViewModel {
    private final Gson gsonUnitLoader = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(SerialNumber.class, new SerialNumberAdapter())
            .registerTypeAdapter(ModelId.class, new ModelIdAdapter())
            .setPrettyPrinting()
            .create();

    private File unitFile;
    private int unitCount;

    private PurificationUnitManager unitManager;

    public ReadFileViewModel(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }

    private int loadUnitsFromJson(File file) throws IOException {
        Reader reader = new FileReader(file);
        PurificationUnit[] units = gsonUnitLoader.fromJson(reader, PurificationUnit[].class);

        unitManager.removeAllUnits();
        unitManager.putUnits(units);

        reader.close();
        return units.length;
    }

    public File getUnitFile() {
        return unitFile;
    }

    public void setUnitFile(File unitFile) throws IOException {
        this.unitCount = 0;
        this.unitFile = unitFile;

        this.unitCount = loadUnitsFromJson(unitFile);
    }

    public int getUnitCount() {
        return unitCount;
    }

    public PurificationUnitManager getUnitManager() {
        return unitManager;
    }

    public void setUnitManager(PurificationUnitManager unitManager) {
        this.unitManager = unitManager;
    }
}
