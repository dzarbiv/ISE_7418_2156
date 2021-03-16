package primitives;
//A foundational object in geometry with direction and size, defined by the end point
public class Vector {
    protected Point3D head;

    public Vector(Point3D head) /**constructor*/
    {
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Vector 0 was inserted");
        this.head = head;

    }

    public Vector(double x, double y, double z) /***constructor*/
    {
        this.head.x = new Coordinate(x);
        this.head.y = new Coordinate(y);
        this.head.z = new Coordinate(z);
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Vector 0 was inserted");

    }

    public Vector(Coordinate x, Coordinate y, Coordinate z)//constructor
    {
        this.head.x = x;
        this.head.y = y;
        this.head.z = z;
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Vector 0 was inserted");

    }

    public Point3D getHead()//getter
    {
        return head;
    }

    @Override
    public boolean equals(Object obj)/***The operation compares the object on which it is applied to the object received as a parameter*/
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return this.head.equals(other.head);
    }

    @Override
    public String toString() /**The operation returns a string with the values of all the field fields*/
    {
        return "Vector{" +
                "head=" + head.toString() +
                '}';
    }

    public Vector add(Vector other)/**Connecting vectors*/
    {
        return (new Vector(this.head.x.coord + other.head.x.coord, this.head.y.coord + other.head.y.coord, this.head.z.coord + other.head.z.coord));
    }

    public Vector subtract(Vector other)/**Subtraction of vectors*/
    {
        return (new Vector(this.head.x.coord - other.head.x.coord, this.head.y.coord - other.head.y.coord, this.head.z.coord - other.head.z.coord));
    }

    public Vector scale(double num)/**Vector multiplication by number*/
    {
        return (new Vector(this.head.x.coord * num, this.head.y.coord * num, this.head.z.coord * num));
    }

    public double dotProduct(Vector other)/**Scalar product*/
    {
        return ((this.head.x.coord * other.head.x.coord) + (this.head.y.coord * other.head.y.coord) + (this.head.z.coord * other.head.z.coord));
    }

    public Vector crossProduct(Vector other) /**Vector multiplication - Returns a new vector that stands for the two existing vectors*/
    {
        double x, y, z;
        x = (((this.head.y.coord) * (other.head.z.coord)) - ((this.head.z.coord) * (other.head.y.coord)));
        y = (((this.head.z.coord) * (other.head.x.coord)) - ((this.head.x.coord) * (other.head.z.coord)));
        z = (((this.head.x.coord) * (other.head.y.coord)) - ((this.head.y.coord) * (other.head.x.coord)));
        return (new Vector(x, y, z));
    }

    public double lengthSquared()/** Calculate the length of the vector squared*/
    {
       return this.dotProduct(this);
    }
    public double length()/**Calculation of the length of the vector*/
    {
        return Math.sqrt(lengthSquared());
    }
    public Vector normalize()/**The vector normalization action that will change the vector itself*/
    {
        double x, y ,z;
        x=(head.x.coord)/this.length();
        y=(head.y.coord)/this.length();
        z=(head.z.coord)/this.length();
        head=new Point3D(x,y,z);
        return this;
    }
    public Vector normalized()/**A normalization operation that returns a new normalized vector in the same direction as the original vector*/
    {
        /***Vector v=this;
        return v.normalize();*/
        Vector v=new Vector(this.normalize().head);
        return v;
    }

}