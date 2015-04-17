package it.geosolutions.imageioimpl.plugins.tiff;

import it.geosolutions.imageio.maskband.DatasetLayout;
import it.geosolutions.imageio.maskband.DatasetLayoutImpl;

import java.io.File;

public class TiffDatasetLayoutImpl extends DatasetLayoutImpl implements DatasetLayout {

    public TiffDatasetLayoutImpl() {
        super();
    }

    private File externalMasks;

    private int numOverviews = -1;

    private int numInternalMasks = -1;

    private int numExternalMasks = -1;

    public int getNumOverviews() {
        return numOverviews;
    }

    public int getNumInternalMasks() {
        return numInternalMasks;
    }

    public int getNumExternalMasks() {
        return numExternalMasks;
    }

    public int getOverviewImageIndex(int overviewIndex) {
        if (numOverviews <= 0 || overviewIndex >= numOverviews) {
            return -1;
        }
        int index = 1;
        if (numInternalMasks > 0) {
            index++;
        }
        return index + overviewIndex;
    }

    public int getInternalMaskImageIndex(int maskIndex) {
        if (numInternalMasks <= 0 || maskIndex >= numInternalMasks) {
            return -1;
        }
        if (maskIndex == 0) {
            return 1;
        }
        return numOverviews + 2 + maskIndex;
    }

    public File getExternalMasks() {
        return externalMasks;
    }

    public boolean hasExternalMasks() {
        return externalMasks != null;
    }

    public void setNumOverviews(int numOverviews) {
        this.numOverviews = numOverviews;
    }

    public void setNumInternalMasks(int numInternalMasks) {
        this.numInternalMasks = numInternalMasks;
    }

    public void setExternalMasks(File externalMasks) {
        this.externalMasks = externalMasks;
    }

    public void setNumExternalMasks(int numExternalMasks) {
        this.numExternalMasks = numExternalMasks;
    }
}
