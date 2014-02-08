/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.catrobat.musicdroid.instruments.drums;

import android.app.Activity;
import android.view.View;

/**
 * This activity displays an image on the screen.
 * The image has three different regions that can be clicked / touched.
 * When a region is touched, the activity changes the view to show a different
 * image.
 * 
 */

public abstract class DrumPart extends Activity implements View.OnTouchListener {
	//	{
	//
	//	/**
	//	 * Create the view for the activity.
	//	 *
	//	 */
	//
	//	@Override 
	//	public void onCreate(Bundle savedInstanceState) {
	//	    super.onCreate(savedInstanceState);
	//	    setContentView(R.layout.main);
	//
	//	    ImageView iv = (ImageView) findViewById (R.id.image);
	//	    if (iv != null) {
	//	       iv.setOnTouchListener (this);
	//	    }
	//
	//	    toast ("Touch the screen to discover where the regions are.");
	//	}
	//
	//	/**
	//	 * Respond to the user touching the screen.
	//	 * Change images to make things appear and disappear from the screen.
	//	 *
	//	 */    
	//
	//	public boolean onTouch (View v, MotionEvent ev) 
	//	{
	//	    boolean handledHere = false;
	//
	//	    final int action = ev.getAction();
	//
	//	    final int evX = (int) ev.getX();
	//	    final int evY = (int) ev.getY();
	//	    int nextImage = -1;			// resource id of the next image to display
	//
	//	    // If we cannot find the imageView, return.
	//	    ImageView imageView = (ImageView) v.findViewById (R.id.image);
	//	    if (imageView == null) return false;
	//
	//	    // When the action is Down, see if we should show the "pressed" image for the default image.
	//	    // We do this when the default image is showing. That condition is detectable by looking at the
	//	    // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
	//	    Integer tagNum = (Integer) imageView.getTag ();
	//	    int currentResource = (tagNum == null) ? R.drawable.p2_ship_default : tagNum.intValue ();
	//
	//	    // Now that we know the current resource being displayed we can handle the DOWN and UP events.
	//
	//	    switch (action) {
	//	    case MotionEvent.ACTION_DOWN :
	//	       if (currentResource == R.drawable.p2_ship_default) {
	//	          nextImage = R.drawable.p2_ship_pressed;
	//	          handledHere = true;
	//	       /*
	//	       } else if (currentResource != R.drawable.p2_ship_default) {
	//	         nextImage = R.drawable.p2_ship_default;
	//	         handledHere = true;
	//	       */
	//	       } else handledHere = true;
	//	       break;
	//
	//	    case MotionEvent.ACTION_UP :
	//	       // On the UP, we do the click action.
	//	       // The hidden image (image_areas) has three different hotspots on it.
	//	       // The colors are red, blue, and yellow.
	//	       // Use image_areas to determine which region the user touched.
	//	       int touchColor = getHotspotColor (R.id.image_areas, evX, evY);
	//
	//	       // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
	//	       // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
	//	       // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
	//	       // varying pixel density.
	//	       ColorTool ct = new ColorTool ();
	//	       int tolerance = 25;
	//	       nextImage = R.drawable.p2_ship_default;
	//	       if (ct.closeMatch (Color.RED, touchColor, tolerance)) nextImage = R.drawable.p2_ship_alien;
	//	       else if (ct.closeMatch (Color.BLUE, touchColor, tolerance)) nextImage = R.drawable.p2_ship_powered;
	//	       else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance)) nextImage = R.drawable.p2_ship_no_star;
	//	       else if (ct.closeMatch (Color.WHITE, touchColor, tolerance)) nextImage = R.drawable.p2_ship_default;
	//
	//	       // If the next image is the same as the last image, go back to the default.
	//	       // toast ("Current image: " + currentResource + " next: " + nextImage);
	//	       if (currentResource == nextImage) {
	//	          nextImage = R.drawable.p2_ship_default;
	//	       } 
	//	       handledHere = true; 
	//	       break;
	//
	//	    default:
	//	       handledHere = false;
	//	    } // end switch
	//
	//	    if (handledHere) {
	//	 
	//	       if (nextImage > 0) {
	//	          imageView.setImageResource (nextImage);
	//	          imageView.setTag (nextImage);
	//	       }
	//	    }
	//	    return handledHere;
	//	}   
	//
	//	/**
	//	 */
	//	// More methods
	//
	//	/**
	//	 * Get the color from the hotspot image at point x-y.
	//	 * 
	//	 */
	//
	//	public int getHotspotColor (int hotspotId, int x, int y) {
	//	    ImageView img = (ImageView) findViewById (hotspotId);
	//	    if (img == null) {
	//	       Log.d ("ImageAreasActivity", "Hot spot image not found");
	//	       return 0;
	//	    } else {
	//	      img.setDrawingCacheEnabled(true); 
	//	      Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache()); 
	//	      if (hotspots == null) {
	//	         Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
	//	         return 0;
	//	      } else {
	//	        img.setDrawingCacheEnabled(false);
	//	        return hotspots.getPixel(x, y);
	//	      }
	//	    }
	//	}
	//
	//	/**
	//	 * Show a string on the screen via Toast.
	//	 * 
	//	 * @param msg String
	//	 * @return void
	//	 */
	//
	//	public void toast (String msg)
	//	{
	//	    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_LONG).show ();
	//	} // end toast
	//

}
