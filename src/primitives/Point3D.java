package primitives;
//Foundry object in geometry - large point 3 coordinates
public class Point3D
{
    public static final Point3D ZERO = new Point3D(0, 0, 0);
    Coordinate x;
    Coordinate y;
    Coordinate z;

    public Point3D(Coordinate c1, Coordinate c2, Coordinate c3)//constructor
    {
        this.x = c1;
        this.y = c2;
        this.z = c3;
    }

    public Point3D(double x, double y, double z) //constructor
    {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    @Override
    public boolean equals(Object obj)//The operation compares the object on which it is applied to the object received as a parameter
     {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
    }

    @Override
    public String toString()//The operation returns a string with the values of all the field fields
    {
        return "Point3D{" +
                "c1=" + x.toString() +
                ", c2=" + y.toString() +
                ", c3=" + z.toString() +
                '}';
    }

    public Vector subtract(Point3D other)//Vector subtraction
    {
        return new Vector(this.x.coord - other.x.coord, this.y.coord - other.y.coord, this.z.coord - other.z.coord);
    }

    public Point3D add(Vector other)//Add a vector to a dot
    {
        return (new Point3D(this.x.coord+other.head.x.coord,this.y.coord+other.head.y.coord,this.z.coord+other.head.z.coord));
    }
    public double distanceSquared(Point3D other)
    {
        double x, y, z;
        x=(this.x.coord-other.x.coord)*(this.x.coord-other.x.coord);
        y=(this.y.coord-other.y.coord)*(this.y.coord-other.y.coord);
        z=(this.z.coord-other.z.coord)*(this.z.coord-other.z.coord);
        return x+y+z;
    }
    public double distance(Point3D other)
    {
        return Math.sqrt(this.distanceSquared(other));
    }

}
