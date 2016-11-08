package br.com.maurosantos.android.exemplowebservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by maurosantos on 08/11/2016.
 */

public class CalculatorWS {
    public CalculatorWS() {

    }

    public int add(int i, int j) throws IOException, XmlPullParserException {
        SoapObject soapObject = new SoapObject("http://calculator.me.org/", "add");
        soapObject.addProperty("i", i);
        soapObject.addProperty("j", j);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE("http://10.17.0.112:8080/CalculatorApp/CalculatorWSService?wsdl");
        httpTransportSE.call("add", envelope);

        Object resultado = envelope.getResponse();

        return Integer.parseInt(resultado.toString());
    }
}
