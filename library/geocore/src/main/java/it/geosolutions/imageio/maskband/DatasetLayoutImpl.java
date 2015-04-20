package it.geosolutions.imageio.maskband;

import java.io.File;

/**
 * Abstract implementation for {@link DatasetLayout} interface implementing default values for interface methods.
 * 
 * @author Nicola Lagomarsini GeoSolutions S.A.S.
 */
public abstract class DatasetLayoutImpl implements DatasetLayout {

    public int getNumInternalOverviews() {
        return 0;
    }
    
    public int getNumExternalOverviews(){
        return 0;
    }
    
    public int getNumExternalMaskOverviews(){
        return 0;
    }

    public int getNumInternalMasks() {
        return 0;
    }

    public int getNumExternalMasks() {
        return 0;
    }

    public int getInternalOverviewImageIndex(int overviewIndex) {
        return overviewIndex;
    }

    public int getInternalMaskImageIndex(int maskIndex) {
        return maskIndex;
    }

    public File getExternalMasks() {
        return null;
    }
    
    public File getExternalOverviews(){
        return null;
    }

    public File getExternalMaskOverviews(){
        return null;
    }
}
