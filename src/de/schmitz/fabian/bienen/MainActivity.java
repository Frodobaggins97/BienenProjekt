package de.schmitz.fabian.bienen;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int SKALIERTE_GROESSE = 200;
	private static final String URI_SCHLUESSEL = "dieUri";
	private Button zaehlButton;
	private Button kameraButton;
	private TextView bienenzahl;
	private ImageView imageView;
	//private boolean isBlackWhite;

	private int grenzwert = 115;
	private int bienenProProzentFlaeche = 10;

	private static final int IMAGE_CAPTURE = 1000;

	private static final String TAG = "BienenZaehlApp";

	private OnClickListener zaehlClickListener = new OnClickListener()
	{

		public void onClick(View v) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof BitmapDrawable) {
				BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
				Bitmap bitmap = bitmapDrawable.getBitmap();
				BienenBitmap bienenBitmap = erzeugeBienenBitmap(bitmap);

				imageView.setImageBitmap(bienenBitmap.getBlackAndWhite());

				bienenzahl.setText("Anzahl Bienen: "
						+ bienenBitmap.getBienenAnzahl());

			}
		}

		public BienenBitmap erzeugeBienenBitmap(Bitmap original) {
			int width, height;
			height = original.getHeight();
			width = original.getWidth();
			Bitmap bwbitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			int helligkeitRot;
			int helligkeitGruen;
			int helligkeitBlau;
			int helligkeitTransparenz = 0xFF000000;
			int helligkeitGesamt;
			int countBlackPixel = 0;
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int colour = original.getPixel(x, y);
					helligkeitRot = colour & 0x00ff0000;
					helligkeitBlau = helligkeitRot >> 16;

					if (helligkeitBlau < grenzwert) {
						helligkeitBlau = 0;
						countBlackPixel++;

					} else {
						helligkeitBlau = 255;
					}

					helligkeitRot = helligkeitBlau << 16;
					helligkeitGruen = helligkeitBlau << 8;
					helligkeitGesamt = helligkeitRot | helligkeitGruen
							| helligkeitBlau | helligkeitTransparenz;
					bwbitmap.setPixel(x, y, helligkeitGesamt);
				}
			}

			return new BienenBitmap(bwbitmap, berechneBienenZahl(
					countBlackPixel, width * height));
		}
	};
	private OnClickListener kameraClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startCamera();
		}
	};
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView);
		zaehlButton = (Button) findViewById(R.id.button1);
		zaehlButton.setOnClickListener(zaehlClickListener);
		bienenzahl = (TextView) findViewById(R.id.Bienenanzahl);
		kameraButton = (Button) findViewById(R.id.kameraButton);
		kameraButton.setOnClickListener(kameraClickListener);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "Sichere URI Wert: " + imageUri);
		outState.putParcelable(URI_SCHLUESSEL, imageUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		imageUri = (Uri) savedInstanceState.get(URI_SCHLUESSEL);
		Log.d(TAG, "Restaurierter URI Wert: " + imageUri);
	}


	public int berechneBienenZahl(int schwarz, int gesamtPixel) {
		float bruchteilSchwarz = (float) schwarz / (float) gesamtPixel;
		float prozentSchwarz = 100 * bruchteilSchwarz;
		return (int) prozentSchwarz * bienenProProzentFlaeche;
	}

	private void startCamera() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, "Bienenknipser");
		values.put(MediaStore.Images.Media.DESCRIPTION, "Descriptionxxxxxx");
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "Keine SD Karte gesteckt!", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		imageUri = getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, IMAGE_CAPTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IMAGE_CAPTURE) {
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmapVonKameraBild = MediaStore.Images.Media
							.getBitmap(getContentResolver(), imageUri);

					// Gr��e des aufgenommenen Bildes
					float w1 = bitmapVonKameraBild.getWidth();
					float h1 = bitmapVonKameraBild.getHeight();
					// auf eine H�he von 300 Pixel skalieren
					int h2 = SKALIERTE_GROESSE;
					int w2 = (int) (w1 / h1 * (float) h2);
					Bitmap skaliert = Bitmap.createScaledBitmap(
							bitmapVonKameraBild, w2, h2, false);

					imageView.setImageBitmap(skaliert);
				} catch (Exception e) {
					Log.e(TAG, "setBitmap()", e);
				}
			} else {
				int rowsDeleted = getContentResolver().delete(imageUri, null,
						null);
				Log.d(TAG, rowsDeleted + " rows deleted");
			}
		}
	}

}
