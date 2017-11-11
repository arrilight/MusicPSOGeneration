public class Point3D {
    /**
     * This class represents a simple point in a 3-dimensional space.
     */
    private int x, y, z; // coordinates

    Point3D (int x, int y, int z) { // create the point
        this.x = x;
        this.y = y;
        this.z = z;
    }

    int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}
