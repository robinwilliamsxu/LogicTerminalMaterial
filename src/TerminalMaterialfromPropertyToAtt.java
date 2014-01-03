
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
import com.mentor.chs.api.IXDevicePin;
import com.mentor.chs.api.IXWire;
import com.mentor.chs.api.IXWireEnd;
import com.mentor.chs.plugin.IXApplicationContext;
import com.mentor.chs.plugin.IXAttributeSetter;
import com.mentor.chs.plugin.IXOutputWindow;
import com.mentor.chs.plugin.action.IXAction;
import com.mentor.chs.plugin.action.IXHarnessAction;
import com.mentor.chs.plugin.action.IXLogicAction;
import java.util.Set;
import javax.swing.Icon;

/**
 * @author mstamper
 */
public class TerminalMaterialfromPropertyToAtt implements IXHarnessAction {

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
        // Get the IXOutputWindow so you can log messages.
        final IXOutputWindow outputWindow = context.getOutputWindow();
        outputWindow.println("Setting terminal material on wires");

        Set<IXWire> wireList = context.getCurrentDesign().getConnectivity()
                .getWires();
        for (IXWire wire : wireList) {

            // get a writer for the wire
            Set<IXWireEnd> WireEnds = wire.getWireEnds();
            int i = 0;
            for (IXWireEnd WireEnd : WireEnds) 
            {
                final IXAttributeSetter attributeSetter = WireEnd.getAttributeSetter();
                if (attributeSetter == null) {
                    outputWindow
                            .println("ERROR: not in read/write mode. Can't change: "
                            + wire);
                    return false;
                }

                // navigate to the device WireEnd, get its material and put it on
                // the wire
                if (WireEnd instanceof IXWireEnd) 
                {

                    if (WireEnd!= null) 
                    {
                       if (i == 0) {
                                    String EndMaterial = wire.getProperty("StartTerminalSpec");
                                    if (EndMaterial != null)
                                    {
                                        if (EndMaterial != null && EndMaterial != "")
                                        attributeSetter.addAttribute("TerminalMaterialCode", EndMaterial);
                                    }
                                    
                                }
                       else {
                                    String EndMaterial = wire.getProperty("EndTerminalSpec");
                                    if (EndMaterial != null)
                                    {
                                        if (EndMaterial != null && EndMaterial != "")
                                        attributeSetter.addAttribute("TerminalMaterialCode", EndMaterial);
                                    }
                        }
                    }
                 
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
    public IXAction.Trigger[] getTriggers() {
        return new IXAction.Trigger[]{IXAction.Trigger.ContextMenu, IXAction.Trigger.MainMenu};
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
        return "[ Mentor ] Set Terminal Material from Property";
    }

    /**
     * @return The name
     */
    public String getName() {
        return "[ Mentor ] Set Terminal Material from Property";
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
