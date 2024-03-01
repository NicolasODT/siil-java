package com.siil.app.detection;

	public class BoundingBox {
	    int xmin, ymin, xmax, ymax;
	    String label;

	    public BoundingBox(int xmin, int ymin, int xmax, int ymax, String label) {
	        this.xmin = xmin;
	        this.ymin = ymin;
	        this.xmax = xmax;
	        this.ymax = ymax;
	        this.label = label;
	    }

    // Getters
    public String getLabel() {
        return label;
    }

    public int getXmin() {
        return xmin;
    }

    public int getYmin() {
        return ymin;
    }

    public int getXmax() {
        return xmax;
    }

    public int getYmax() {
        return ymax;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "label='" + label + '\'' +
                ", xmin=" + xmin +
                ", ymin=" + ymin +
                ", xmax=" + xmax +
                ", ymax=" + ymax +
                '}';
    }
}
