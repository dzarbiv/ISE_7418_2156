package primitives;

public class Material {
    public double kD=0;
    public double kS=0;
    public int nShininess=0;

    /**
     * setter
     * @param kD
     * @return material
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter
     * @param kS
     * @return material
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return  this;
    }

    /**
     * setter
     * @param nShininess
     * @return material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
