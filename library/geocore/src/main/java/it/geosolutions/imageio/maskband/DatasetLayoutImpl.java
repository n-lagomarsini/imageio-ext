package it.geosolutions.imageio.maskband;

import java.io.File;

/**
 * Abstract implementation for {@link DatasetLayout} interface implementing default values for interface methods.
 * 
 * @author Nicola Lagomarsini GeoSolutions S.A.S.
 */
public abstract class DatasetLayoutImpl implements DatasetLayout {

    public int getNumOverviews() {
        return 0;
    }

    public int getNumInternalMasks() {
        return 0;
    }

    public int getNumExternalMasks() {
        return 0;
    }

    public int getOverviewImageIndex(int overviewIndex) {
        return overviewIndex;
    }

    public int getInternalMaskImageIndex(int maskIndex) {
        return maskIndex;
    }

    public File getExternalMasks() {
        return null;
    }

    public boolean hasExternalMasks() {
        return false;
    }
}
