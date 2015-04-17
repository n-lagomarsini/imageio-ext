package it.geosolutions.imageio.maskband;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataFormatImpl;

public class MaskBandStreamMetadataFormat extends IIOMetadataFormatImpl implements
        IIOMetadataFormat {

    private static final String DEFAULT_NUM_VALUE = -1 + "";

    /**
     * A single instance of the <code>MaskBandStreamMetadataFormat</code> class.
     */
    protected static MaskBandStreamMetadataFormat maskBandStreamMetadataInstance;

    /**
     * Returns an instance of the <code>MaskBandStreamMetadataFormat</code> class. We build only a single instance and we cache it for future uses.
     * 
     * @return an instance of the <code>MaskBandStreamMetadataFormat</code> class.
     */
    public static synchronized IIOMetadataFormat getInstance() {
        if (maskBandStreamMetadataInstance == null) {
            maskBandStreamMetadataInstance = new MaskBandStreamMetadataFormat();
        }
        return maskBandStreamMetadataInstance;
    }

    /**
     * Constructs a <code>MrSIDIIOImageMetadataFormat</code> instance.
     */
    protected MaskBandStreamMetadataFormat() {
        super(MaskBandStreamMetadata.metadataFormatName, CHILD_POLICY_SOME);

        // //
        //
        // root -> NUM_INTERNAL_MASKS
        //
        // //
        addElement(MaskBandStreamMetadata.NUM_INTERNAL_MASKS,
                MaskBandStreamMetadata.metadataFormatName, CHILD_POLICY_EMPTY);
        addAttribute(MaskBandStreamMetadata.NUM_INTERNAL_MASKS, "value", DATATYPE_INTEGER, true,
                DEFAULT_NUM_VALUE);
        // //
        //
        // root -> NUM_EXTERNAL_MASKS
        //
        // //
        addElement(MaskBandStreamMetadata.NUM_EXTERNAL_MASKS,
                MaskBandStreamMetadata.metadataFormatName, CHILD_POLICY_EMPTY);
        addAttribute(MaskBandStreamMetadata.NUM_EXTERNAL_MASKS, "value", DATATYPE_INTEGER, true,
                DEFAULT_NUM_VALUE);

        // //
        //
        // root -> NUM_OVERVIEWS
        //
        // //
        addElement(MaskBandStreamMetadata.NUM_OVERVIEWS, MaskBandStreamMetadata.metadataFormatName,
                CHILD_POLICY_EMPTY);
        addAttribute(MaskBandStreamMetadata.NUM_OVERVIEWS, "value", DATATYPE_INTEGER, true,
                DEFAULT_NUM_VALUE);
        // //
        //
        // root -> EXTERNAL_MASK_FILE
        //
        // //
        addElement(MaskBandStreamMetadata.EXTERNAL_MASK_FILE,
                MaskBandStreamMetadata.metadataFormatName, CHILD_POLICY_EMPTY);
        addAttribute(MaskBandStreamMetadata.EXTERNAL_MASK_FILE, "value", DATATYPE_STRING, true,
                null);
    }

    /**
     * @see javax.imageio.metadata.IIOMetadataFormatImpl#canNodeAppear(java.lang.String, javax.imageio.ImageTypeSpecifier)
     */
    public boolean canNodeAppear(String elementName, ImageTypeSpecifier imageType) {
        return false;
    }

}
