import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class provides functionality to save and load data from ArrayLists to files.
 * It implements the concept of Serialization.
 * Currently used to save and load Users, Listings, and Tickets.
 * 
 * @author Muhammad Daud
 */
public class FileManagement {
    private ArrayList<?>[] dataList;
    private String[] fileNames;

    /**
     * Constructs a FileManagement object with the specified ArrayLists and file names.
     * 
     * @param dataList an array of ArrayLists to be managed
     * @param fileNames an array of file names corresponding to the ArrayLists
     */
    public FileManagement(ArrayList<?>[] dataList, String[] fileNames) {
        this.dataList = dataList;
        this.fileNames = fileNames;
    }

    /**
     * Saves the data from ArrayLists to the corresponding files.
     */
    public void saveData() {
        for (int i = 0; i < dataList.length; i++) {
            try {
                FileOutputStream fileOut = new FileOutputStream(fileNames[i]);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(dataList[i]);
                out.close();
                fileOut.close();
                System.out.println("Data saved successfully to " + fileNames[i]);
            } catch (Exception e) {
                System.out.println("Error saving " + fileNames[i] + ": " + e);
            }
        }
    }

    /**
     * Loads the data from files into ArrayLists, which is then fed back.
     * 
     * @return an array of ArrayLists containing the loaded, deserialized data
     */
    public ArrayList<?>[] loadData() {
        ArrayList<?>[] loadedData = new ArrayList<?>[dataList.length];
        for (int i = 0; i < dataList.length; i++) {
            try {
                FileInputStream fileIn = new FileInputStream(fileNames[i]);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                loadedData[i] = (ArrayList<?>) in.readObject();
                in.close();
                fileIn.close();
                System.out.println("Data loaded successfully from " + fileNames[i]);
            } catch (Exception e) {
                System.out.println("Error loading " + fileNames[i] + ": " + e);
            }
        }
        return loadedData;
    }
}
