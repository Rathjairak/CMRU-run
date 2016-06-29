package cmru.jairak.rath.cmrurun;

/**
 * Created by User on 28/6/2559.
 */
public class MyData {

    //Explicit
    private int [] avataInts = new int[] {R.drawable.bird48,
    R.drawable.doremon48, R.drawable.kon48, R.drawable.nobita48, R.drawable.rat48};


    private double [] latStationDoubles = new double[] {18.805809, 18.807525,18.807619, 18.805823};
    private double[] lngStationDoubles = new double[]{98.986449,98.986379,98.987197,98.987367};

    private int[] iconStationInts = new int[]{R.drawable.build1, R.drawable.build2, R.drawable.build3, R.drawable.build4};

    public int[] getIconStationInts() {
        return iconStationInts;
    }

    public int[] getAvataInts() {
        return avataInts;
    }

    public double[] getLatStationDoubles() {
        return latStationDoubles;
    }

    public double[] getLngStationDoubles() {
        return lngStationDoubles;
    }
} //Main Class
