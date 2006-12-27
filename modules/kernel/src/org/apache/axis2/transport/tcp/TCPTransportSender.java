/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package org.apache.axis2.transport.tcp;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.description.TransportOutDescription;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.i18n.Messages;
import org.apache.axis2.transport.AbstractTransportSender;
import org.apache.axis2.transport.TransportSender;
import org.apache.axis2.transport.TransportUtils;
import org.apache.axis2.transport.http.HTTPTransportUtils;
import org.apache.axis2.util.URL;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMOutputFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPTransportSender extends AbstractHandler implements TransportSender {
    protected Writer out;
    private Socket socket;

    public void init(ConfigurationContext confContext, TransportOutDescription transportOut)
            throws AxisFault {
    }

    public void stop() {
    }

    public void cleanup(MessageContext msgContext) throws AxisFault {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
        }
    }

    /**
     * Method invoke
     *
     * @param msgContext
     * @throws AxisFault
     */
    public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {

        // Check for the REST behaviour, if you desire rest beahaviour
        // put a <parameter name="doREST" value="true"/> at the axis2.xml
        msgContext.setDoingMTOM(HTTPTransportUtils.doWriteMTOM(msgContext));
        msgContext.setDoingSwA(HTTPTransportUtils.doWriteSwA(msgContext));

        OutputStream out;
        EndpointReference epr = null;

        if (msgContext.getTo() != null && !msgContext.getTo().hasAnonymousAddress()) {
            epr = msgContext.getTo();
        }

        if (epr != null) {
            if (!epr.hasNoneAddress()) {
                out = openTheConnection(epr, msgContext);
                TransportUtils.writeMessage(msgContext, out);
                try {
                    socket.shutdownOutput();
                    msgContext.setProperty(MessageContext.TRANSPORT_IN, socket.getInputStream());
                } catch (IOException e) {
                    throw new AxisFault(e);
                }
            }
        } else {
            out = (OutputStream) msgContext.getProperty(MessageContext.TRANSPORT_OUT);

            if (out != null) {
                TransportUtils.writeMessage(msgContext, out);
            } else {
                throw new AxisFault(
                        "Both the TO and Property MessageContext.TRANSPORT_OUT is Null, No where to send");
            }
        }

        // TODO fix this, we do not set the value if the operation context is
        // not available
        if (msgContext.getOperationContext() != null) {
            msgContext.getOperationContext().setProperty(Constants.RESPONSE_WRITTEN,
                    Constants.VALUE_TRUE);
        }
        return InvocationResponse.CONTINUE;
    }

    protected OutputStream openTheConnection(EndpointReference toURL, MessageContext msgContext)
            throws AxisFault {
        if (toURL != null) {
            try {
                URL url = new URL(toURL.getAddress());
                SocketAddress add = new InetSocketAddress(url.getHost(), (url.getPort() == -1)
                        ? 80
                        : url.getPort());

                socket = new Socket();
                socket.connect(add);

                return socket.getOutputStream();
            } catch (MalformedURLException e) {
                throw new AxisFault(e.getMessage(), e);
            } catch (IOException e) {
                throw new AxisFault(e.getMessage(), e);
            }
        } else {
            throw new AxisFault(Messages.getMessage("canNotBeNull", "Can not Be Null"));
        }
    }
}
