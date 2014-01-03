
/**
 * Copyright 2009 Mentor Graphics Corporation. All Rights Reserved. <p>
 * Recipients who obtain this code directly from Mentor Graphics use it solely
 * for internal purposes to serve as example Java or Java Script plugins. This
 * code may not be used in a commercial distribution. Recipients may duplicate
 * the code provided that all notices are fully reproduced with and remain in
 * the code. No part of this code may be modified, reproduced, translated, used,
 * distributed, disclosed or provided to third parties without the prior written
 * consent of Mentor Graphics, except as expressly authorized above. <p> THE
 * CODE IS MADE AVAILABLE "AS IS" WITHOUT WARRANTY OR SUPPORT OF ANY KIND.
 * MENTOR GRAPHICS OFFERS NO EXPRESS OR IMPLIED WARRANTIES AND SPECIFICALLY
 * DISCLAIMS ANY WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE,
 * OR WARRANTY OF NON-INFRINGEMENT. IN NO EVENT SHALL MENTOR GRAPHICS OR ITS
 * LICENSORS BE LIABLE FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING LOST PROFITS OR SAVINGS) WHETHER BASED ON
 * CONTRACT, TORT OR ANY OTHER LEGAL THEORY, EVEN IF MENTOR GRAPHICS OR ITS
 * LICENSORS HAVE BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. <p>
 */
//~--- non-JDK imports --------------------------------------------------------
import com.mentor.chs.api.IXAbstractPin;
import com.mentor.chs.api.IXCavity;
import com.mentor.chs.api.IXWire;
import com.mentor.chs.plugin.IXApplicationContext;
import com.mentor.chs.plugin.IXAttributeSetter;
import com.mentor.chs.plugin.action.IXLogicAction;
import java.util.Set;
import javax.swing.Icon;

/**
 * @author mstamper
 */
public class LogicTerminalMaterial implements IXLogicAction {

    IXApplicationContext cntx;

    // this is a nice way to throw any text to the output window
    public void p(String s) {
        cntx.getOutputWindow().println(s);
    }

    /*
     * Code executed by the action.
     */
    public boolean execute(IXApplicationContext context) {
        cntx = context;
        cntx.getOutputWindow().clear();
        // get all of the wires on the design
        Set<IXWire> wireList = cntx.getCurrentDesign().getConnectivity().getWires();
        for (IXWire wire : wireList) {

            // get a writer for the wire
            final IXAttributeSetter attributeSetter = wire.getAttributeSetter();
            if (attributeSetter == null) {
                p("ERROR: not in read/write mode. Can't change: " + wire);
                return false;
            }

            Set<IXAbstractPin> pins = wire.getAbstractPins();
            int i = 0;
            for (IXAbstractPin pin : pins) {

                // navigate to the device pin, get its material and put it on
                // the wire
                // navigate to the connector pin instead of the device pin
                if (pin instanceof IXCavity) {
                    IXCavity cav = (IXCavity) pin;

                    //Use this with the commented out code below to navigate to
                    //The DevicePin instead of the ConnectorPin
                    //IXDevicePin devPin = cav.getMatedDevicePin(); 

                    //Comment out this code if you are going to use the code
                    //provided to use DevicePin instead of ConnectorPin
                    if (cav != null) {
                        String contactMaterial = cav.getProperty("TerminalMaterial");
                        if (contactMaterial != null) {
                            if (!"".equals(contactMaterial)) {
                                if (i == 0) {
                                    attributeSetter.addProperty("StartTerminalSpec", contactMaterial);
                                } else {
                                attributeSetter.addProperty("EndTerminalSpec", contactMaterial);
                                }
                            }

                        }
                    }
                
// use this code to navigate to the DEVICE PIN instead of the CONNECTOR pin
//                if (cav != null && devPin != null) {
//                    String contactMaterial = devPin.getAttribute("ContactMaterial");
//                    if (contactMaterial != null) {
//                        if (contactMaterial != null && !"".equals(contactMaterial)) {
//                            if (i == 0) {
//                                attributeSetter.addProperty(
//                                        "StartTerminalSpec",
//                                        contactMaterial);
//                            } else {
//                                attributeSetter.addProperty(
//                                        "EndTerminalSpec", contactMaterial);
//                            }
//                        }
//                    }
//                }
            }
            i++;
        }
    }

return true;
    }

    /**
     * This action is available in both the context menu and the main menu.
     *
     * @return An array with both Content and Main menu triggers
     */
    public Trigger[] getTriggers() {
        return new Trigger[]{Trigger.ContextMenu, Trigger.MainMenu};
    }

    /**
     * No icon supplied.
     *
     * @return null
     */
    public Icon getSmallIcon() {
        return null;
    }

    /**
     * No mnemonic supplied.
     *
     * @return null
     */
    public Integer getMnemonicKey() {
        return null;
    }

    /**
     * @return The long description
     */
    public String getLongDescription() {
        return getDescription();
    }

    /**
     * This action's purpose is to change data.
     *
     * @return false
     */
    public boolean isReadOnly() {
        return false;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return "[ Mentor ]Terminal Material from Conn cavity";
    }

    /**
     * @return The name
     */
    public String getName() {
        return "[ Mentor ]TerminalMaterialFromConnectorCavity";
    }

    /**
     * @return The version string
     */
    public String getVersion() {
        return "1.0";
    }

    /**
     * This action always says it is available, assuming that any object that
     * does not have a library part will be ignored.
     *
     * @param context The application context
     * @return true
     */
    public boolean isAvailable(IXApplicationContext context) {
        return true;
    }
}
