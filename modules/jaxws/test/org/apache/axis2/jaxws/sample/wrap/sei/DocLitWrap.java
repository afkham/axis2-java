
package org.apache.axis2.jaxws.sample.wrap.sei;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.test.sample.wrap.FinancialOperation;
import org.test.sample.wrap.Header;
import org.test.sample.wrap.HeaderPart0;
import org.test.sample.wrap.HeaderPart1;
import org.test.sample.wrap.HeaderResponse;

/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_01-b15-fcs
 * Generated source version: 2.0
 * 
 */
@WebService(name = "DocLitWrap", targetNamespace = "http://wrap.sample.test.org")
public interface DocLitWrap {


    /**
     * 
     */
    @WebMethod(action = "http://wrap.sample.test.org/twoWayReturn")
    @Oneway
    @RequestWrapper(localName = "oneWayVoid", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.OneWayVoid")
    public void oneWayVoid();

    /**
     * 
     * @param onewayStr
     */
    @WebMethod(action = "http://wrap.sample.test.org/twoWayReturn")
    @Oneway
    @RequestWrapper(localName = "oneWay", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.OneWay")
    public void oneWay(
        @WebParam(name = "oneway_str", targetNamespace = "")
        String onewayStr);

    /**
     * 
     * @param twoWayHolderInt
     * @param twoWayHolderStr
     */
    @WebMethod(action = "http://wrap.sample.test.org/twoWayReturn")
    @RequestWrapper(localName = "twoWayHolder", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.TwoWayHolder")
    @ResponseWrapper(localName = "twoWayHolder", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.TwoWayHolder")
    public void twoWayHolder(
        @WebParam(name = "twoWayHolder_str", targetNamespace = "", mode = Mode.INOUT)
        Holder<String> twoWayHolderStr,
        @WebParam(name = "twoWayHolder_int", targetNamespace = "", mode = Mode.INOUT)
        Holder<Integer> twoWayHolderInt);

    /**
     * 
     * @param twowayStr
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://wrap.sample.test.org/twoWayReturn")
    @WebResult(name = "return_str", targetNamespace = "")
    @RequestWrapper(localName = "twoWay", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.TwoWay")
    @ResponseWrapper(localName = "ReturnType", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.ReturnType")
    public String twoWay(
        @WebParam(name = "twoway_str", targetNamespace = "")
        String twowayStr);

    /**
     * 
     * @param invokeStr
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "http://wrap.sample.test.org/twoWayReturn")
    @WebResult(name = "return_str", targetNamespace = "")
    @RequestWrapper(localName = "invoke", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.Invoke")
    @ResponseWrapper(localName = "ReturnType", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.ReturnType")
    public String invoke(
        @WebParam(name = "invoke_str", targetNamespace = "")
        String invokeStr);

    /**
     * 
     * @param op
     * @return
     *     returns org.test.sample.wrap.FinancialOperation
     */
    @WebMethod(action = "http://wrap.sample.test.org/finOp")
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "finOp", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.FinOp")
    @ResponseWrapper(localName = "finOpResponse", targetNamespace = "http://wrap.sample.test.org", className = "org.test.sample.wrap.FinOpResponse")
    public FinancialOperation finOp(
        @WebParam(name = "op", targetNamespace = "")
        FinancialOperation op);
    
    /**
     * 
     * @param header1
     * @param header0
     * @param payload
     * @return
     *     returns org.test.sample.wrap.HeaderResponse
     */
    @WebMethod(action = "http://addheaders.sample.test.org/header")
    @WebResult(name = "headerResponse", targetNamespace = "http://wrap.sample.test.org", partName = "payload")
    @SOAPBinding(parameterStyle = ParameterStyle.BARE)
    public HeaderResponse header(
        @WebParam(name = "header", targetNamespace = "http://wrap.sample.test.org", partName = "payload")
        Header payload,
        @WebParam(name = "headerPart0", targetNamespace = "http://wrap.sample.test.org", header = true, mode = Mode.INOUT, partName = "header0")
        Holder<HeaderPart0> header0,
        @WebParam(name = "headerPart1", targetNamespace = "http://wrap.sample.test.org", header = true, partName = "header1")
        HeaderPart1 header1);


}
