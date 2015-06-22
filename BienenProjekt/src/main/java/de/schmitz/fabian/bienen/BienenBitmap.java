package de.schmitz.fabian.bienen;

import android.graphics.Bitmap;

/**
 * Created by fabian on 22.06.15.
 */
public class BienenBitmap {
    private int BienenAnzahl;
    private Bitmap BlackAndWhite;

    public BienenBitmap(Bitmap BlackAndWhite, int BienenAnzahl)
    {
       this.BienenAnzahl = BienenAnzahl;
        this.BlackAndWhite = BlackAndWhite;
    }

    public int getBienenAnzahl()
    {
        return this.BienenAnzahl;
    }

    public Bitmap getBlackAndWhite()
    {
        return this.BlackAndWhite;
    }
}
