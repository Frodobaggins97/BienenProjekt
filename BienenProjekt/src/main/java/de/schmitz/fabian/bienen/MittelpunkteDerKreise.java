package de.schmitz.fabian.bienen;

/**
 * Created by fabian on 23.06.15.
 */
public class MittelpunkteDerKreise {

    private float xcenter1, xcenter2, xcenter3, xcenter4;
    private float ycenter1, ycenter2, ycenter3, ycenter4;

    public MittelpunkteDerKreise(float xcenter1, float xcenter2, float xcenter3, float xcenter4, float ycenter1, float ycenter2, float ycenter3,float ycenter4)
    {
        this.xcenter1 = xcenter1;
        this.xcenter2 = xcenter2;
        this.xcenter3 = xcenter3;
        this.xcenter4 = xcenter4;

        this.ycenter1 = ycenter1;
        this.ycenter2 = ycenter2;
        this.ycenter3 = ycenter3;
        this.ycenter4 = ycenter4;
    }

    public float getXcenter1()
    {
        return this.xcenter1;
    }

    public float getXcenter2()
    {
        return this.xcenter2;
    }

    public float getXcenter3()
    {
        return this.xcenter3;
    }

    public float getXcenter4()
    {
        return this.xcenter4;
    }

    public float getYcenter1()
    {
        return this.ycenter1;
    }

    public float getYcenter2()
    {
        return this.ycenter2;
    }

    public float getYcenter3()
    {
        return this.ycenter3;
    }

    public float getYcenter4()
    {
        return this.ycenter4;
    }




    public void setXcenter1(float w)
    {
        xcenter1 = w;
    }

    public void setXcenter2(float w)
    {
        xcenter2 = w;
    }

    public void setXcenter3(float w)
    {
        xcenter3 = w;
    }

    public void setXcenter4(float w)
    {
        xcenter4 = w;
    }

    public void setYcenter1(float w)
    {
        ycenter1 = w;
    }

    public void setYcenter2(float w)
    {
        ycenter2 = w;
    }

    public void setYcenter3(float w)
    {
        ycenter3 = w;
    }

    public void setYcenter4(float w)
    {
        ycenter4 = w;
    }




}
