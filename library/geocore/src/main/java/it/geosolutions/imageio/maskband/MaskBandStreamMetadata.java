package it.geosolutions.imageio.maskband;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MaskBandStreamMetadata extends IIOMetadata {

    static final String EXTERNAL_MASK_FILE = "externalMaskFile";

    static final String NUM_OVERVIEWS = "numOverviews";

    static final String NUM_EXTERNAL_MASKS = "numExternalMasks";

    static final String NUM_INTERNAL_MASKS = "numInternalMasks";

    // package scope
    static final String metadataFormatName = "it_geosolutions_imageioimpl_plugins_tiff_mask_stream";

    static final String metadataFormatClassName = "it.geosolutions.imageioimpl.plugins.tiff.TIFFMaskBandStreamMetadataFormat";

    private static List<String> names = new ArrayList<String>();

    static {
        names.add(NUM_INTERNAL_MASKS);
        names.add(NUM_EXTERNAL_MASKS);
        names.add(NUM_OVERVIEWS);
        names.add(EXTERNAL_MASK_FILE);
    }

    private DatasetLayout dtLayout;

    public boolean isReadOnly() {
        return false;
    }

    public MaskBandStreamMetadata(DatasetLayout dtLayout) {
        super(false, metadataFormatName, metadataFormatClassName, null, null);
        this.setDtLayout(dtLayout);
    }

    // Shorthand for throwing an IIOInvalidTreeException
    private static void fatal(Node node, String reason) throws IIOInvalidTreeException {
        throw new IIOInvalidTreeException(reason, node);
    }

    public Node getAsTree(String formatName) {
        // Getting Root node
        IIOMetadataNode root = new IIOMetadataNode(metadataFormatName);
        // Setting Internal Mask number
        IIOMetadataNode numInternalMasksNode = new IIOMetadataNode(NUM_INTERNAL_MASKS);
        numInternalMasksNode.setAttribute("value", Integer.valueOf(dtLayout.getNumInternalMasks()).toString());
        // Setting External Mask number
        IIOMetadataNode numExternalMasksNode = new IIOMetadataNode(NUM_EXTERNAL_MASKS);
        numExternalMasksNode.setAttribute("value", Integer.valueOf(dtLayout.getNumExternalMasks()).toString());
        // Setting Overview number
        IIOMetadataNode numOverviewsNode = new IIOMetadataNode(NUM_OVERVIEWS);
        numOverviewsNode.setAttribute("value", Integer.valueOf(dtLayout.getNumOverviews()).toString());
        // Setting external file path
        IIOMetadataNode externalMaskFileNode = new IIOMetadataNode(EXTERNAL_MASK_FILE);
        File file = dtLayout.getExternalMasks();
        externalMaskFileNode.setAttribute("value", file != null ? file.getAbsolutePath() : "");

        // Setting Child nodes
        root.appendChild(numInternalMasksNode);
        root.appendChild(numExternalMasksNode);
        root.appendChild(numOverviewsNode);
        root.appendChild(externalMaskFileNode);
        return root;
    }

    private void mergeInputTree(Node root) throws IIOInvalidTreeException {
        // Checking root
        Node node = root;
        if (!node.getNodeName().equals(metadataFormatName)) {
            fatal(node, "Root must be " + nativeMetadataFormatName);
        }
        // Checking Childs
        if (node.hasChildNodes()) {
            // Check node size
            NodeList childNodes = root.getChildNodes();
            int size = names.size();
            if (childNodes.getLength() != size) {
                fatal(node, "Root must have " + size + " childs");
            }
            // Iterate on the nodes
            for (int i = 0; i < size; i++) {
                Node child = childNodes.item(i);
                String message = checkChild(child);
                if (message != null && !message.isEmpty()) {
                    fatal(child, message);
                } else {
                    // Check if it is an Integer
                    String nodeName = child.getNodeName();
                    if (nodeName != EXTERNAL_MASK_FILE) {
                        try {
                            Integer.parseInt(nodeName);
                        } catch (NumberFormatException e) {
                            fatal(child, e.getMessage());
                        }
                    }
                }
            }
        } else {
            fatal(node, "Root must have childs");
        }

        fatal(node, "Incorrect value for ByteOrder \"value\" attribute");
    }

    private String checkChild(Node child) {
        if (child == null) {
            return "Root cannot have null child";
        }
        String nodeName = child.getNodeName();
        if (!names.contains(nodeName)) {
            return "Root cannot have \"" + nodeName + "\" child";
        }

        NamedNodeMap attrs = child.getAttributes();
        String value = (String) attrs.getNamedItem("value").getNodeValue();

        if (value == null) {
            return nodeName + " node must have a \"value\" attribute";
        }

        return null;
    }

    public void mergeTree(String formatName, Node root) throws IIOInvalidTreeException {
        if (formatName.equals(metadataFormatName)) {
            if (root == null) {
                throw new IllegalArgumentException("root == null!");
            }
            mergeInputTree(root);
        } else {
            throw new IllegalArgumentException("Not a recognized format!");
        }
    }

    public void reset() {
        setDtLayout(null);
    }

    public DatasetLayout getDtLayout() {
        return dtLayout;
    }

    public void setDtLayout(DatasetLayout dtLayout) {
        this.dtLayout = dtLayout;
    }
}
