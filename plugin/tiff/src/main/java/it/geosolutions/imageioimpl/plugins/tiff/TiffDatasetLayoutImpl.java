package it.geosolutions.imageioimpl.plugins.tiff;

import it.geosolutions.imageio.maskband.DatasetLayout;
import it.geosolutions.imageio.maskband.DatasetLayoutImpl;
import it.geosolutions.imageioimpl.plugins.tiff.TIFFStreamMetadata.MetadataNode;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.metadata.IIOMetadata;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TiffDatasetLayoutImpl extends DatasetLayoutImpl implements DatasetLayout {

    public TiffDatasetLayoutImpl() {
        super();
    }

    private File externalMasks;

    private File externalOverviews;

    private File externalMaskOverviews;

    private int numInternalOverviews = -1;

    private int numInternalMasks = -1;

    private int numExternalMasks = -1;

    private int numExternalOverviews = -1;

    private int numExternalMaskOverviews = -1;

    public int getNumInternalOverviews() {
        return numInternalOverviews;
    }

    public int getNumInternalMasks() {
        return numInternalMasks;
    }

    public int getNumExternalMasks() {
        return numExternalMasks;
    }

    public int getInternalOverviewImageIndex(int overviewIndex) {
        if(overviewIndex == 0){
            return overviewIndex;
        }
        if (numInternalOverviews < 0 || overviewIndex > numInternalOverviews) {
            return -1;
        }
        int index = 0;
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
        return numInternalOverviews + 2 + maskIndex;
    }

    public int getNumExternalOverviews() {
        return numExternalOverviews;
    }

    public int getNumExternalMaskOverviews() {
        return numExternalMaskOverviews;
    }

    public File getExternalMaskOverviews() {
        return externalMaskOverviews;
    }

    public File getExternalMasks() {
        return externalMasks;
    }

    public File getExternalOverviews() {
        return externalOverviews;
    }

    public void setNumInternalOverviews(int numOverviews) {
        this.numInternalOverviews = numOverviews;
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

    public void setExternalOverviews(File externalOverviews) {
        this.externalOverviews = externalOverviews;
    }

    public void setExternalMaskOverviews(File externalMaskOverviews) {
        this.externalMaskOverviews = externalMaskOverviews;
    }

    public void setNumExternalOverviews(int numExternalOverviews) {
        this.numExternalOverviews = numExternalOverviews;
    }

    public void setNumExternalMaskOverviews(int numExternalMaskOverviews) {
        this.numExternalMaskOverviews = numExternalMaskOverviews;
    }

    /**
     * Creates a new {@link DatasetLayout} instance created from parsing input reader Stream Metadata.
     * @param metadata 
     * 
     * @return a {@link DatasetLayout} instance parsed from input StreamMetadata
     */
    public static DatasetLayout parseLayout(IIOMetadata metadata) throws IOException {
        // Init the layout to null
        TiffDatasetLayoutImpl layout = null;
        // Getting input parameters
        Node tree = metadata.getAsTree("com_sun_media_imageio_plugins_tiff_stream_1.0");
        // Ensuring not null
        if (tree == null) {
            return layout;
        } else {
            layout = new TiffDatasetLayoutImpl();
        }

        // Checking Childs
        NodeList list = tree.getChildNodes();
        int len = list.getLength();

        for (int i = 0; i < len; i++) {
            Node node = list.item(i);
            // Ensuring not null
            if (node == null) {
                continue;
            }
            // Getting the name
            String nodeName = node.getNodeName();
            // Getting Attribute Value
            String value = node.getAttributes().item(0).getNodeValue();
            // Getting the Enum related to the input Element
            MetadataNode mnode = MetadataNode.getFromName(nodeName);
            // Checking Attribute value
            switch (mnode) {
            case N_INT_MASK:
                layout.setNumInternalMasks(Integer.parseInt(value));
                break;
            case N_EXT_MASK:
                layout.setNumExternalMasks(Integer.parseInt(value));
                break;
            case N_INT_OVR:
                layout.setNumInternalOverviews(Integer.parseInt(value));
                break;
            case N_EXT_OVR:
                layout.setNumExternalOverviews(Integer.parseInt(value));
                break;
            case N_EXT_OVR_MASK:
                layout.setNumExternalMaskOverviews(Integer.parseInt(value));
                break;
            case EXT_MASK_FILE:
                layout.setExternalMasks((value !=null && !value.isEmpty()) ? new File(value) : null);
                break;
            case EXT_OVR_FILE:
                layout.setExternalOverviews((value !=null && !value.isEmpty()) ? new File(value) : null);
                break;
            case EXT_OVR_MASK_FILE:
                layout.setExternalMaskOverviews((value !=null && !value.isEmpty()) ? new File(value) : null);
                break;
            default:
                break;
            }
        }
        return layout;
    }
}
