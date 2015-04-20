package it.geosolutions.imageio.maskband;

import java.io.File;

/**
 * Interface defining methods for accessing Internal Image structure
 * 
 * @author Nicola Lagomarsini GeoSolutions S.A.S.
 */
public interface DatasetLayout {

    /**
     * Returns the number of internal Image overviews
     * 
     * @return an Integer indicating how many overviews are present
     */
    public int getNumInternalOverviews();
    
    /**
     * Returns the number of external Image overviews
     * 
     * @return an Integer indicating how many overviews are present
     */
    public int getNumExternalOverviews();
    
    /**
     * Returns the number of external mask overviews
     * 
     * @return an Integer indicating how many overviews are present
     */
    public int getNumExternalMaskOverviews();

    /**
     * Returns the total number of internal Image masks. Notice that If masks are more than one, the others are considered as overviews of the first mask
     * 
     * @return an Integer indicating how many masks are present
     */
    public int getNumInternalMasks();

    /**
     * Returns the total number of externals Image masks. Notice that If masks are more than one, the others are considered as overviews of the first mask
     * 
     * @return an Integer indicating how many external masks are present
     */
    public int getNumExternalMasks();

    /**
     * Returns the Overview index associated to the input image index defined. This is helpful when we have overviews and masks and we are unable to
     * distinguish them. If no overwiew is present or the overview index is greater than the maximum index, -1 will be returned.
     * 
     * @param overviewIndex Integer defining a general overview index
     * @return The Overview index related to the imageIndex defined
     */
    public int getInternalOverviewImageIndex(int overviewIndex);

    /**
     * Returns the Mask index associated to the input image index defined. This is helpful when we have overviews and masks and we are unable to
     * distinguish them. If no mask is present or the mask index is greater than the maximum index, -1 will be returned.
     * 
     * @param maskIndex Integer defining a general mask index
     * @return The Overview index related to the imageIndex defined
     */
    public int getInternalMaskImageIndex(int maskIndex);

    /**
     * This methods returns a File containing external masks associated to input Image, or <code>null</code> if not present.
     * 
     * @return a {@link File} containing external masks associated to an input {@link File}
     */
    public File getExternalMasks();
    
    /**
     * This methods returns a File containing external overviews associated to input Image, or <code>null</code> if not present.
     * 
     * @return a {@link File} containing external overviews associated to an input {@link File}
     */
    public File getExternalOverviews();

    /**
     * This methods returns a File containing external overviews associated to external Image masks, or <code>null</code> if not present.
     * 
     * @return a {@link File} containing external mask overviews associated to an input {@link File}
     */
    public File getExternalMaskOverviews();
}
