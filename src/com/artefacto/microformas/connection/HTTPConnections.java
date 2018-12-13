package com.artefacto.microformas.connection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.artefacto.microformas.EntityCatalog;
import com.artefacto.microformas.MainActivity;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.AgregarCoordenadasBean;
import com.artefacto.microformas.beans.AlmacenesBean;
import com.artefacto.microformas.beans.CalidadBilleteBean;
import com.artefacto.microformas.beans.CambiosStatusBean;
import com.artefacto.microformas.beans.CarriersBean;
import com.artefacto.microformas.beans.CausaRetiroBean;
import com.artefacto.microformas.beans.CausasBean;
import com.artefacto.microformas.beans.CausasRechazoBean;
import com.artefacto.microformas.beans.ClientBean;
import com.artefacto.microformas.beans.ClienteCalidadBilleteBean;
import com.artefacto.microformas.beans.ClienteCondicionSiteBean;
import com.artefacto.microformas.beans.ClienteModelosBean;
import com.artefacto.microformas.beans.ClientesCambiosStatusBean;
import com.artefacto.microformas.beans.CodigosIntervencion0Bean;
import com.artefacto.microformas.beans.CodigosIntervencion1Bean;
import com.artefacto.microformas.beans.CodigosIntervencion2Bean;
import com.artefacto.microformas.beans.CondicionSiteBean;
import com.artefacto.microformas.beans.ConnectivityBean;
import com.artefacto.microformas.beans.DescripcionTrabajoBean;
import com.artefacto.microformas.beans.DireccionesBean;
import com.artefacto.microformas.beans.EspecificaCausasRechazoBean;
import com.artefacto.microformas.beans.EspecificacionTipoFallaBean;
import com.artefacto.microformas.beans.EtiquetasBean;
import com.artefacto.microformas.beans.FailsFoundBean;
import com.artefacto.microformas.beans.GenericResultBean;
import com.artefacto.microformas.beans.GruposBean;
import com.artefacto.microformas.beans.GruposClientesBean;
import com.artefacto.microformas.beans.InfoActualizacionBean;
import com.artefacto.microformas.beans.InfoInstalacionBean;
import com.artefacto.microformas.beans.InfoInsumoBean;
import com.artefacto.microformas.beans.InfoMovimientosBean;
import com.artefacto.microformas.beans.InfoRetiroBean;
import com.artefacto.microformas.beans.InfoSustitucionBean;
import com.artefacto.microformas.beans.IngenierosBean;
import com.artefacto.microformas.beans.InsumosBean;
import com.artefacto.microformas.beans.LogComentariosBean;
import com.artefacto.microformas.beans.MSparePartsBean;
import com.artefacto.microformas.beans.MarcasBean;
import com.artefacto.microformas.beans.ModelosBean;
import com.artefacto.microformas.beans.NetworkBean;
import com.artefacto.microformas.beans.NotificationsUpdateBean;
import com.artefacto.microformas.beans.ProductosStatusBean;
import com.artefacto.microformas.beans.PackageShipmentBean;
import com.artefacto.microformas.beans.RefaccionesUnidadBean;
import com.artefacto.microformas.beans.RequestDetailBean;
import com.artefacto.microformas.beans.ResultBean;
import com.artefacto.microformas.beans.SKUBean;
import com.artefacto.microformas.beans.SendExitoResponseBean;
import com.artefacto.microformas.beans.SendRechazoBean;
import com.artefacto.microformas.beans.SendRechazoResponseBean;
import com.artefacto.microformas.beans.ServiciosBean;
import com.artefacto.microformas.beans.ServiciosCausasBean;
import com.artefacto.microformas.beans.ServiciosSolucionesBean;
import com.artefacto.microformas.beans.ShipmentBean;
import com.artefacto.microformas.beans.SoftwareBean;
import com.artefacto.microformas.beans.SolicitudesBean;
import com.artefacto.microformas.beans.SolucionesBean;
import com.artefacto.microformas.beans.SparePartsBean;
import com.artefacto.microformas.beans.StatusBean;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.beans.TipoFallaBean;
import com.artefacto.microformas.beans.UnidadesBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.beans.UploadImageBean;
import com.artefacto.microformas.beans.ValidateClosureBean;
import com.artefacto.microformas.beans.ValidateRejectClosureBean;
import com.artefacto.microformas.beans.ViaticosBean;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.InformacionCierreBean;
import com.artefacto.microformas.tasks.ValidateConnTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HTTPConnections
{
    public static ResultBean loginUser(String username, String password, boolean isIdUser)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/registerUser.asp";
            url += (isIdUser) ? "?ui=" + username : "?u=" + username;
            url += "&p=" + password;
        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node resultNode = doc.getElementsByTagName("result").item(0);
            resultBean.setMessage(resultNode.getFirstChild().getNodeValue());

            if(resultNode.getFirstChild().getNodeValue().equals(REQUEST_ERROR))
            {
                Node messageNode = doc.getElementsByTagName("message").item(0);
                resultBean.setMessage(messageNode.getFirstChild().getNodeValue());
            }
            else
            {
                Node idNode = doc.getElementsByTagName("id").item(0);
                resultBean.setMessage(idNode.getFirstChild().getNodeValue());
            }

            return resultBean;
        }

        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    public static ResultBean verifyUser(String username)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/comprobarUsuario.asp";
            url += "?username=" + username;

        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node resultNode = doc.getElementsByTagName("result").item(0);
            resultBean.setMessage(resultNode.getFirstChild().getNodeValue());
        }
        else
        {
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static ResultBean recoverPassword(String username)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/recuperaPw.asp";
        url += "?username=" + username;

        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node usernameNode = doc.getElementsByTagName("username").item(0);
            resultBean.setMessage(usernameNode.getFirstChild().getNodeValue());
        }
        else
        {
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

	public static Object getClients()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getClients.asp";

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            ArrayList<ClientBean> listClients = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("cliente");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                ClientBean client = new ClientBean();
                client.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                client.setDescription(verifyNode(childNodes.item(1).getFirstChild()));
                client.setActive_mobile_notification(Integer.parseInt(verifyNode(childNodes.item(2).getFirstChild())));
                listClients.add(client);
            }

            return listClients;
        }

        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    public static Object getConnectivities()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getConnectivity.asp";

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            ArrayList<ConnectivityBean> listConnectivity = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("connectivity");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                ConnectivityBean connectivity = new ConnectivityBean();
                connectivity.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                connectivity.setIdClient(Integer.parseInt(childNodes.item(1).getFirstChild().getNodeValue()));
                connectivity.setDescription(verifyNode(childNodes.item(2).getFirstChild()));
                connectivity.setIsGPRS(verifyNode(childNodes.item(3).getFirstChild()).equals("1"));
                listConnectivity.add(connectivity);
            }

            return listConnectivity;
        }

        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    public static Object getSoftwares()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getApplication.asp";

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            ArrayList<SoftwareBean> listSoftware = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("application");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                SoftwareBean software = new SoftwareBean();
                software.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                software.setIdClient(verifyNode(childNodes.item(1).getFirstChild()).equals("") ? Integer.MIN_VALUE :
                        Integer.parseInt(childNodes.item(1).getFirstChild().getNodeValue()));
                software.setDescription(verifyNode(childNodes.item(2).getFirstChild()));
                listSoftware.add(software);
            }

            return listSoftware;
        }

        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    public static Object getFailsFound()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getFailsFound.asp";

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            ArrayList<FailsFoundBean> listFails = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("fail");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                FailsFoundBean failsBean = new FailsFoundBean();
                String strId = verifyNode(childNodes.item(0).getFirstChild());
                failsBean.setId(strId.equals("") ? Integer.MIN_VALUE : Integer.parseInt(strId));

                String strIdFather = verifyNode(childNodes.item(1).getFirstChild());
                failsBean.setIdFather(strIdFather.equals("") ? Integer.MIN_VALUE : Integer.parseInt(strIdFather));

                String strIdClient = verifyNode(childNodes.item(2).getFirstChild());
                failsBean.setIdClient(strIdClient.equals("") ? Integer.MIN_VALUE : Integer.parseInt(strIdClient));
                failsBean.setDesc(verifyNode(childNodes.item(3).getFirstChild()));
                listFails.add(failsBean);
            }

            return listFails;
        }

        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    public static ResultBean insertNewShipment(String idUser, String idTypeDestination, String idDestination,
        String idMessaging, String idPriority, String guideNum, String dataUnits, String dataSupplies)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = "";
        try
        {
            url = URL_SERVER + "/insertNewShipment.asp";
            url += "?id_responsable=" + URLEncoder.encode(idUser, "UTF-8");
            url += "&id_tipo_destino=" + URLEncoder.encode(idTypeDestination, "UTF-8");
            url += "&id_destino=" + URLEncoder.encode(idDestination, "UTF-8");
            url += "&id_mensajeria=" + URLEncoder.encode(idMessaging, "UTF-8");
            url += "&prioridad=" + URLEncoder.encode(idPriority, "UTF-8");
            url += "&no_guia=" + URLEncoder.encode(guideNum, "UTF-8");
            if(!dataUnits.trim().equals(""))
            {
                url += "&d_unidades=" + URLEncoder.encode(dataUnits, "UTF-8");
            }

            if(!dataSupplies.trim().equals(""))
            {
                url += "&d_insumos=" + URLEncoder.encode(dataSupplies, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e)
        {
            Log.d("Microformas", e.getMessage());
            e.printStackTrace();
        }

        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node resultNode = doc.getElementsByTagName("result").item(0);
            resultBean.setMessage(verifyNode(resultNode.getFirstChild()));

            if(resultNode.getFirstChild().getNodeValue().equals(REQUEST_ERROR))
            {
                Node messageNode = doc.getElementsByTagName("message").item(0);
                resultBean.setMessage(verifyNode(messageNode.getFirstChild()));
            }
        }
        else
        {
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

	public static ResultBean validateSupply(String idSupply, String idUser, String count)
	{
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String url = URL_SERVER + "/validarInsumoCantidad.asp";
            url += "?id_insumo=" + idSupply;
            url += "&id_responsable=" + idUser;
            url += "&cantidad=" + count;

        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node resultNode = doc.getElementsByTagName("result").item(0);
            resultBean.setMessage(verifyNode(resultNode.getFirstChild()));

            if(resultNode.getFirstChild().getNodeValue().equals(REQUEST_ERROR))
            {
                Node messageNode = doc.getElementsByTagName("message").item(0);
                resultBean.setMessage(verifyNode(messageNode.getFirstChild()));
            }
        }
        else
        {
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
	}

	public static ResultBean validateUnit(String idSupply, String idUser)
	{
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/validarUnidad.asp";
            url += "?id_unidad=" + idSupply;
            url += "&id_responsable=" + idUser;

        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node resultNode = doc.getElementsByTagName("result").item(0);
            resultBean.setMessage(verifyNode(resultNode.getFirstChild()));

            if(resultNode.getFirstChild().getNodeValue().equals(REQUEST_ERROR))
            {
                Node messageNode = doc.getElementsByTagName("message").item(0);
                resultBean.setMessage(verifyNode(messageNode.getFirstChild()));
            }
        }
        else
        {
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
	}

    public static Object getInvSupplies(String id)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getResponsibleSupplies.asp";
            url += "?id_responsible=" + id;

        ArrayList<SupplyBean> list = new ArrayList<>();
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            NodeList suppliesNode = doc.getElementsByTagName("insumo");
            for(int i = 0; i < suppliesNode.getLength(); i++)
            {
                SupplyBean bean = new SupplyBean();
                bean.setIdSupply(verifyNode(suppliesNode.item(i).getChildNodes().item(0).getFirstChild()));
                bean.setDescClient(verifyNode(suppliesNode.item(i).getChildNodes().item(1).getFirstChild()));
                bean.setDescSupply(verifyNode(suppliesNode.item(i).getChildNodes().item(3).getFirstChild()));
                bean.setCount(verifyNode(suppliesNode.item(i).getChildNodes().item(4).getFirstChild()));

                list.add(bean);
            }

            return list;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getClientSupplies(String id, String idClient)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getClientSupplies.asp";
        url += "?id_responsible=" + id;
        url += "&id_cliente=" + idClient;
        Log.e("URL_SUPPLIES:",url);
        ArrayList<SupplyBean> list = new ArrayList<>();
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            NodeList suppliesNode = doc.getElementsByTagName("insumo");
            for(int i = 0; i < suppliesNode.getLength(); i++)
            {
                SupplyBean bean = new SupplyBean();
                bean.setIdSupply(verifyNode(suppliesNode.item(i).getChildNodes().item(0).getFirstChild()));
               // Log.e("ID:",verifyNode(suppliesNode.item(i).getChildNodes().item(0).getFirstChild()));
                bean.setDescClient(verifyNode(suppliesNode.item(i).getChildNodes().item(1).getFirstChild()));
               // Log.e("ID:",verifyNode(suppliesNode.item(i).getChildNodes().item(1).getFirstChild()));
                bean.setDescSupply(verifyNode(suppliesNode.item(i).getChildNodes().item(3).getFirstChild()));
               // Log.e("ID:",verifyNode(suppliesNode.item(i).getChildNodes().item(3).getFirstChild()));
                bean.setCount(verifyNode(suppliesNode.item(i).getChildNodes().item(4).getFirstChild()));
               // Log.e("ID:",verifyNode(suppliesNode.item(i).getChildNodes().item(4).getFirstChild()));

                list.add(bean);
            }

            return list;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getReceptions(String id)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getReceptions.asp";
            url += "?id_responsable=" + id;

        ArrayList<PackageShipmentBean> list = new ArrayList<>();
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            NodeList unitsNode = doc.getElementsByTagName("unidad");
            for(int i = 0; i < unitsNode.getLength(); i++)
            {
                PackageShipmentBean receptionBean = new PackageShipmentBean();
                receptionBean.setIdShipment(unitsNode.item(i).getChildNodes().item(0).getFirstChild().getNodeValue());

                int nCount = 0;
                String strCount = verifyNode(unitsNode.item(i).getChildNodes().item(1).getFirstChild());
                if(!strCount.equals(""))
                {
                    nCount = Integer.parseInt(strCount);
                }

                receptionBean.setCount(nCount);
                receptionBean.setType(PackageShipmentBean.TYPE_UNIT);
                list.add(receptionBean);
            }

            NodeList suppliesNode = doc.getElementsByTagName("insumo");
            for(int i = 0; i < suppliesNode.getLength(); i++)
            {
                PackageShipmentBean receptionBean = new PackageShipmentBean();
                receptionBean.setIdShipment(suppliesNode.item(i).getChildNodes().item(0).getFirstChild().getNodeValue());

                int nCount = 0;
                String strCount = verifyNode(suppliesNode.item(i).getChildNodes().item(1).getFirstChild());
                if(!strCount.equals(""))
                {
                    nCount = Integer.parseInt(strCount);
                }

                receptionBean.setCount(nCount);
                receptionBean.setType(PackageShipmentBean.TYPE_SUPPLY);
                list.add(receptionBean);
            }

            return list;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getReceptionDetail(String idSent)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getReceptionDetail.asp";
            url += "?id=" + idSent;

        ShipmentBean shipmentBean;
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;

            shipmentBean = new ShipmentBean();
            shipmentBean.setIdShipment(verifyNode(doc.getElementsByTagName("id").item(0).getFirstChild()));
            shipmentBean.setResponsible(verifyNode(doc.getElementsByTagName("responsible").item(0).getFirstChild()));
            shipmentBean.setResponsibleTypeDesc(verifyNode(doc.getElementsByTagName("descResType").item(0).getFirstChild()));
            shipmentBean.setMessagingServiceDesc(verifyNode(doc.getElementsByTagName("descMessaging").item(0).getFirstChild()));
            shipmentBean.setNoGuide(verifyNode(doc.getElementsByTagName("noGuia").item(0).getFirstChild()));
            shipmentBean.setDate(verifyNode(doc.getElementsByTagName("sentDate").item(0).getFirstChild()));

            ArrayList<UnitBean> listUnits = new ArrayList<>();
            NodeList unitsNode = doc.getElementsByTagName("unidad");
            for(int i = 0; i < unitsNode.getLength();  i++)
            {
                NodeList childNodes = unitsNode.item(i).getChildNodes();

                UnitBean unitBean = new UnitBean();
                unitBean.setId(verifyNode(childNodes.item(0).getFirstChild()));
                unitBean.setIdUnitStatus(verifyNode(childNodes.item(1).getFirstChild()));
                unitBean.setIdRequestCollection(verifyNode(childNodes.item(2).getFirstChild()));
                unitBean.setIdProduct(verifyNode(childNodes.item(3).getFirstChild()));
                unitBean.setIsNew(verifyNode(childNodes.item(4).getFirstChild()));
                unitBean.setIsWithdrawal(verifyNode(childNodes.item(5).getFirstChild()));
                unitBean.setDescClient(verifyNode(childNodes.item(6).getFirstChild()));
                unitBean.setDescBrand(verifyNode(childNodes.item(7).getFirstChild()));
                unitBean.setDescModel(verifyNode(childNodes.item(8).getFirstChild()));
                unitBean.setDescKey(verifyNode(childNodes.item(9).getFirstChild()));
                unitBean.setDescSoftware(verifyNode(childNodes.item(10).getFirstChild()));
                unitBean.setDescUnitStatus(verifyNode(childNodes.item(11).getFirstChild()));
                unitBean.setNoSerie(verifyNode(childNodes.item(12).getFirstChild()));
                unitBean.setNoInventory(verifyNode(childNodes.item(13).getFirstChild()));
                unitBean.setNoIMEI(verifyNode(childNodes.item(14).getFirstChild()));
                unitBean.setNoSim(verifyNode(childNodes.item(15).getFirstChild()));
                unitBean.setNoEquipment(verifyNode(childNodes.item(16).getFirstChild()));
                unitBean.setInventoryPos(verifyNode(childNodes.item(17).getFirstChild()));
                unitBean.setStatus(verifyNode(childNodes.item(18).getFirstChild()));
                unitBean.setNewDate(verifyNode(childNodes.item(19).getFirstChild()));

                listUnits.add(unitBean);
            }

            ArrayList<SupplyBean> listSupplies = new ArrayList<>();
            NodeList suppliesNode = doc.getElementsByTagName("insumo");
            for(int i = 0; i < suppliesNode.getLength();  i++)
            {
                NodeList childNodes = suppliesNode.item(i).getChildNodes();

                SupplyBean supplyBean = new SupplyBean();
                supplyBean.setIdSupply(verifyNode(childNodes.item(0).getFirstChild()));
                supplyBean.setIdClient(verifyNode(childNodes.item(1).getFirstChild()));
                supplyBean.setDescSupply(verifyNode(childNodes.item(2).getFirstChild()));
                supplyBean.setDescClient(verifyNode(childNodes.item(3).getFirstChild()));
                supplyBean.setCount(verifyNode(childNodes.item(4).getFirstChild()));

                listSupplies.add(supplyBean);
            }

            shipmentBean.setUnitBeanArray(listUnits);
            shipmentBean.setSupplyBeanArray(listSupplies);

            return shipmentBean;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static ResultBean confirmReception(String idSent, String idResponsible, String units,
          String supplies)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/confirmReceptionUnits.asp";
            url += "?id=" + idSent;
            url += "&idResponsible=" + idResponsible;
            url += "&units=" + units;
            url += "&supplies=" + supplies;

        ResultBean resultBean = new ResultBean();

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            Node resultNode = doc.getElementsByTagName("result").item(0);
            resultBean.setMessage(verifyNode(resultNode.getFirstChild()));

            if(resultNode.getFirstChild().getNodeValue().equals(REQUEST_ERROR))
            {
                Node messageNode = doc.getElementsByTagName("message").item(0);
                resultBean.setMessage(verifyNode(messageNode.getFirstChild()));
            }
        }
        else
        {
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getProcessShipment(String id)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getProcessShipments.asp";
        url += "?idResponsible=" + id;

        ArrayList<PackageShipmentBean> list = new ArrayList<>();
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            NodeList unitsNode = doc.getElementsByTagName("unidad");
            for(int i = 0; i < unitsNode.getLength(); i++)
            {
                PackageShipmentBean receptionBean = new PackageShipmentBean();
                receptionBean.setIdShipment(unitsNode.item(i).getChildNodes().item(0).getFirstChild().getNodeValue());

                int nCount = 0;
                String strCount = verifyNode(unitsNode.item(i).getChildNodes().item(1).getFirstChild());
                if(!strCount.equals(""))
                {
                    nCount = Integer.parseInt(strCount);
                }

                receptionBean.setCount(nCount);
                receptionBean.setType(PackageShipmentBean.TYPE_UNIT);
                list.add(receptionBean);
            }

            NodeList suppliesNode = doc.getElementsByTagName("insumo");
            for(int i = 0; i < suppliesNode.getLength(); i++)
            {
                PackageShipmentBean receptionBean = new PackageShipmentBean();
                receptionBean.setIdShipment(suppliesNode.item(i).getChildNodes().item(0).getFirstChild().getNodeValue());

                int nCount = 0;
                String strCount = suppliesNode.item(i).getChildNodes().item(1).getFirstChild().getNodeValue();
                if(!strCount.equals(""))
                {
                    nCount = Integer.parseInt(strCount);
                }

                receptionBean.setCount(nCount);
                receptionBean.setType(PackageShipmentBean.TYPE_SUPPLY);
                list.add(receptionBean);
            }

            return list;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getProcessShipmentDetail(String idSent)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getProcessShipmentDetail.asp";
        url += "?id=" + idSent;

        ShipmentBean shipmentBean;
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;

            shipmentBean = new ShipmentBean();
            shipmentBean.setIdShipment(verifyNode(doc.getElementsByTagName("id").item(0).getFirstChild()));
            shipmentBean.setResponsible(verifyNode(doc.getElementsByTagName("responsible").item(0).getFirstChild()));
            shipmentBean.setResponsibleTypeDesc(verifyNode(doc.getElementsByTagName("descResType").item(0).getFirstChild()));
            shipmentBean.setMessagingServiceDesc(verifyNode(doc.getElementsByTagName("descMessaging").item(0).getFirstChild()));
            shipmentBean.setNoGuide(verifyNode(doc.getElementsByTagName("noGuia").item(0).getFirstChild()));
            shipmentBean.setDate(verifyNode(doc.getElementsByTagName("sentDate").item(0).getFirstChild()));

            ArrayList<UnitBean> listUnits = new ArrayList<>();
            NodeList unitsNode = doc.getElementsByTagName("unidad");
            for(int i = 0; i < unitsNode.getLength();  i++)
            {
                NodeList childNodes = unitsNode.item(i).getChildNodes();

                UnitBean unitBean = new UnitBean();
                unitBean.setId(verifyNode(childNodes.item(0).getFirstChild()));
                unitBean.setIdUnitStatus(verifyNode(childNodes.item(1).getFirstChild()));
                unitBean.setIdRequestCollection(verifyNode(childNodes.item(2).getFirstChild()));
                unitBean.setIdProduct(verifyNode(childNodes.item(3).getFirstChild()));
                unitBean.setIsNew(verifyNode(childNodes.item(4).getFirstChild()));
                unitBean.setIsWithdrawal(verifyNode(childNodes.item(5).getFirstChild()));
                unitBean.setDescClient(verifyNode(childNodes.item(6).getFirstChild()));
                unitBean.setDescBrand(verifyNode(childNodes.item(7).getFirstChild()));
                unitBean.setDescModel(verifyNode(childNodes.item(8).getFirstChild()));
                unitBean.setDescKey(verifyNode(childNodes.item(9).getFirstChild()));
                unitBean.setDescSoftware(verifyNode(childNodes.item(10).getFirstChild()));
                unitBean.setDescUnitStatus(verifyNode(childNodes.item(11).getFirstChild()));
                unitBean.setNoSerie(verifyNode(childNodes.item(12).getFirstChild()));
                unitBean.setNoInventory(verifyNode(childNodes.item(13).getFirstChild()));
                unitBean.setNoIMEI(verifyNode(childNodes.item(14).getFirstChild()));
                unitBean.setNoSim(verifyNode(childNodes.item(15).getFirstChild()));
                unitBean.setNoEquipment(verifyNode(childNodes.item(16).getFirstChild()));
                unitBean.setInventoryPos(verifyNode(childNodes.item(17).getFirstChild()));
                unitBean.setStatus(verifyNode(childNodes.item(18).getFirstChild()));
                unitBean.setNewDate(verifyNode(childNodes.item(19).getFirstChild()));

                listUnits.add(unitBean);
            }

            ArrayList<SupplyBean> listSupplies = new ArrayList<>();
            NodeList suppliesNode = doc.getElementsByTagName("insumo");
            for(int i = 0; i < suppliesNode.getLength();  i++)
            {
                NodeList childNodes = suppliesNode.item(i).getChildNodes();

                SupplyBean supplyBean = new SupplyBean();
                supplyBean.setIdSupply(verifyNode(childNodes.item(0).getFirstChild()));
                supplyBean.setIdClient(verifyNode(childNodes.item(1).getFirstChild()));
                supplyBean.setDescSupply(verifyNode(childNodes.item(2).getFirstChild()));
                supplyBean.setDescClient(verifyNode(childNodes.item(3).getFirstChild()));
                supplyBean.setCount(verifyNode(childNodes.item(4).getFirstChild()));

                listSupplies.add(supplyBean);
            }

            shipmentBean.setUnitBeanArray(listUnits);
            shipmentBean.setSupplyBeanArray(listSupplies);

            return shipmentBean;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getServices()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getServicios.asp";

        ArrayList<ServiciosBean> serviciosBeanArray = new ArrayList<>();
        ResultBean resultBean;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("ser");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NamedNodeMap nodeAttributes = nodeList.item(i).getAttributes();

                ServiciosBean servicios = new ServiciosBean();
                servicios.setId(verifyNode(nodeAttributes.getNamedItem("A")));
                servicios.setIdCliente(verifyNode(nodeAttributes.getNamedItem("B")));
                servicios.setIdTipoServicio(verifyNode(nodeAttributes.getNamedItem("C")));
                servicios.setIdMoneda(verifyNode(nodeAttributes.getNamedItem("D")));
                servicios.setIdTipoPrecio(verifyNode(nodeAttributes.getNamedItem("E")));
                servicios.setIsInsumosRequired(verifyNode(nodeAttributes.getNamedItem("F")));
                servicios.setIsCausaSolucionRequired(verifyNode(nodeAttributes.getNamedItem("G")));
                servicios.setIsCausaRequired(verifyNode(nodeAttributes.getNamedItem("H")));
                servicios.setIsSolucionRequired(verifyNode(nodeAttributes.getNamedItem("I")));
                servicios.setIsTASRequired(verifyNode(nodeAttributes.getNamedItem("J")));
                servicios.setIsOtorganteTASRequired(verifyNode(nodeAttributes.getNamedItem("K")));
                servicios.setIsNoEquipoRequired(verifyNode(nodeAttributes.getNamedItem("L")));
                servicios.setIsNoSerieRequired(verifyNode(nodeAttributes.getNamedItem("M")));
                servicios.setIsNoInventarioRequired(verifyNode(nodeAttributes.getNamedItem("N")));
                servicios.setIsIDModeloRequired(verifyNode(nodeAttributes.getNamedItem("O")));
                servicios.setIsFecLlegadaRequired(verifyNode(nodeAttributes.getNamedItem("P")));
                servicios.setIsFecLlegadaTercerosRequired(verifyNode(nodeAttributes.getNamedItem("Q")));
                servicios.setIsFolioServicioRequired(verifyNode(nodeAttributes.getNamedItem("R")));
                servicios.setIsFecIniIngenieroRequired(verifyNode(nodeAttributes.getNamedItem("S")));
                servicios.setIsFecFinIngenieroRequired(verifyNode(nodeAttributes.getNamedItem("T")));
                servicios.setIsOtorganteVoBoRequired(verifyNode(nodeAttributes.getNamedItem("U")));
                servicios.setIsOtorganteVoBoTercerosRequired(verifyNode(nodeAttributes.getNamedItem("V")));
                servicios.setIsIntensidadSenialRequired(verifyNode(nodeAttributes.getNamedItem("W")));
                servicios.setIsSimReemplazadaRequired(verifyNode(nodeAttributes.getNamedItem("X")));
                servicios.setIsFolioServicioRechazoRequired(verifyNode(nodeAttributes.getNamedItem("Y")));
                servicios.setIsOtorganteVoBoRechazoRequired(verifyNode(nodeAttributes.getNamedItem("Z")));
                servicios.setIsFallaEncontradaRequired(verifyNode(nodeAttributes.getNamedItem("AA")));
                servicios.setIsOtorganteVoBoClienteRequired(verifyNode(nodeAttributes.getNamedItem("AB")));
                servicios.setIsMotivoCobroRequired(verifyNode(nodeAttributes.getNamedItem("AC")));
                servicios.setIsSoporteClienteRequired(verifyNode(nodeAttributes.getNamedItem("AD")));
                servicios.setIsOtorganteSoporteClienteRequired(verifyNode(nodeAttributes.getNamedItem("AE")));
                servicios.setIsBoletinRequired(verifyNode(nodeAttributes.getNamedItem("AF")));
                servicios.setIsCadenaCierreEscritaRequired(verifyNode(nodeAttributes.getNamedItem("AG")));
                servicios.setIsDowntime(verifyNode(nodeAttributes.getNamedItem("AH")));
                servicios.setIsCierrePDA(verifyNode(nodeAttributes.getNamedItem("AI")));
                servicios.setIsAplicacionRequired(verifyNode(nodeAttributes.getNamedItem("AJ")));
                servicios.setIsVersionRequired(verifyNode(nodeAttributes.getNamedItem("AK")));
                servicios.setIsCajaRequired(verifyNode(nodeAttributes.getNamedItem("AL")));
                servicios.setIdArReopen(verifyNode(nodeAttributes.getNamedItem("AR_REOPEN")));
                servicios.setIdARNeedFile(verifyNode(nodeAttributes.getNamedItem("AR_NEED_F")));
                servicios.setIdARNeedNoCheckUp(verifyNode(nodeAttributes.getNamedItem("AR_NOCHECK")));

                servicios.setIsKitRequired(verifyNode(nodeList.item(i).getChildNodes().item(0).getFirstChild()));
                servicios.setKitSupply(verifyNode(nodeList.item(i).getChildNodes().item(1).getFirstChild()));
                servicios.setDescServicio(verifyNode(nodeList.item(i).getChildNodes().item(2).getFirstChild()));
                servicios.setmNeedSevicesSheet(verifyNode(nodeList.item(i).getChildNodes().item(3).getFirstChild()));
                servicios.setmIsCalidadBillete(verifyNode(nodeList.item(i).getChildNodes().item(4).getFirstChild()));
                servicios.setmIsCondicionSite(verifyNode(nodeList.item(i).getChildNodes().item(5).getFirstChild()));

                serviciosBeanArray.add(servicios);
            }

            return serviciosBeanArray;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getSolutions()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getSoluciones.asp";
        ArrayList<SolucionesBean> solutionsList = new ArrayList<>();

        ResultBean resultBean;
        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;
            NodeList solutionsNode = doc.getElementsByTagName("solution");
            for(int i = 0; i < solutionsNode.getLength(); i++)
            {
                NodeList childNodes = solutionsNode.item(i).getChildNodes();

                SolucionesBean bean = new SolucionesBean();
                bean.setId(verifyNode(childNodes.item(0).getFirstChild()));
                bean.setIdCliente(verifyNode(childNodes.item(1).getFirstChild()));
                bean.setClave(verifyNode(childNodes.item(2).getFirstChild()));
                bean.setIsExito(verifyNode(childNodes.item(3).getFirstChild()));
                bean.setDescSolucion(verifyNode(childNodes.item(4).getFirstChild()));

                solutionsList.add(bean);
            }

            return solutionsList;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getUpdateConnectivity(String idModel, String idClient)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getUpdateConnectivity.asp";
        url += "?id_modelo=" + idModel;
        url += "&id_client=" + idClient;

        ArrayList<ConnectivityBean> connectivityList = new ArrayList<>();

        ResultBean resultBean;
        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;

            NodeList listConnectivity = doc.getElementsByTagName("connectivity");
            for(int i = 0; i < listConnectivity.getLength(); i++)
            {
                NodeList childNodes = listConnectivity.item(i).getChildNodes();

                ConnectivityBean bean = new ConnectivityBean();
                String strId = verifyNode(childNodes.item(0).getFirstChild());
                bean.setId(strId.equals("") ? 0 : Integer.parseInt(strId));

                String strGPRS = verifyNode(childNodes.item(1).getFirstChild());
                bean.setIsGPRS((!(strGPRS.equals("") || strGPRS.equals("0"))));
                bean.setDescription(verifyNode(childNodes.item(2).getFirstChild()));

                connectivityList.add(bean);
            }

            return connectivityList;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getUpdateSoftware(String idConn, String idClient)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getUpdateSoftware.asp";
        url += "?id_conn=" + idConn;
        url += "&id_client=" + idClient;

        ArrayList<SoftwareBean> softwareList = new ArrayList<>();

        ResultBean resultBean;
        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;

            NodeList listSoftware = doc.getElementsByTagName("software");
            for(int i = 0; i < listSoftware.getLength(); i++)
            {
                NodeList childNodes = listSoftware.item(i).getChildNodes();

                SoftwareBean bean = new SoftwareBean();
                String strId = verifyNode(childNodes.item(0).getFirstChild());
                bean.setId(strId.equals("") ? 0 : Integer.parseInt(strId));
                bean.setDescription(verifyNode(childNodes.item(1).getFirstChild()));

                softwareList.add(bean);
            }

            return softwareList;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object getRequestDetails(String id, int type)
    {   // Método para adquirir los detalles de cada solicitud
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getSolicitud.asp";
        url += "?id=" + id;

        RequestDetailBean requestDetailBean = new RequestDetailBean();
        ResultBean resultBean = null;

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            Document doc = (Document) response;

            NodeList nodeE = doc.getElementsByTagName("e");
            NodeList nodeNoAr = doc.getElementsByTagName("NO_AR");
            NodeList nodeDescCliente = doc.getElementsByTagName("DESC_CLIENTE");
            NodeList nodeNoAfiliacion = doc.getElementsByTagName("NO_AFILIACION");
            NodeList nodeDescServicio = doc.getElementsByTagName("DESC_SERVICIO");
            NodeList nodeSintoma = doc.getElementsByTagName("SINTOMA");
            NodeList nodeConcepto = doc.getElementsByTagName("CONCEPTO");
            NodeList nodeDescCorta = doc.getElementsByTagName("DESC_CORTA");
            NodeList nodeBitacora = doc.getElementsByTagName("BITACORA");
            NodeList nodeNotasRemedy = doc.getElementsByTagName("NOTAS_REMEDY");
            NodeList nodeDescEquipo = doc.getElementsByTagName("DESC_EQUIPO");
            NodeList nodeEquipo = doc.getElementsByTagName("EQUIPO");
            NodeList nodeNoSerie = doc.getElementsByTagName("NO_SERIE");
            NodeList nodeDireccion = doc.getElementsByTagName("DIRECCION");
            NodeList nodeColonia = doc.getElementsByTagName("COLONIA");
            NodeList nodePoblacion = doc.getElementsByTagName("POBLACION");
            NodeList nodeEstado = doc.getElementsByTagName("ESTADO");
            NodeList nodeCp = doc.getElementsByTagName("CP");
            NodeList nodeDescNegocio = doc.getElementsByTagName("DESC_NEGOCIO");
            NodeList nodeTelefono = doc.getElementsByTagName("TELEFONO");
            NodeList nodeCaja = doc.getElementsByTagName("CAJA");
            NodeList nodeSoftware = doc.getElementsByTagName("SOFTWARE");
            NodeList nodeConn = doc.getElementsByTagName("CONNECTIVITY");

            NamedNodeMap nodeMap = nodeE.item(0).getAttributes();
            requestDetailBean.setIdAr(verifyNode(nodeMap.getNamedItem("ID_AR")));
            requestDetailBean.setIdCatalogo(String.valueOf(type));
            requestDetailBean.setIsKeyAccount(verifyNode(nodeMap.getNamedItem("IS_KEY_ACCOUNT")));
            requestDetailBean.setHorasGarantia(verifyNode(nodeMap.getNamedItem("HORAS_GARANTIA")));
            requestDetailBean.setHorasAtencion(verifyNode(nodeMap.getNamedItem("HORAS_ATENCION")));
            requestDetailBean.setFecAlta(verifyNode(nodeMap.getNamedItem("FEC_ALTA")));
            requestDetailBean.setFecAtencion(verifyNode(nodeMap.getNamedItem("FEC_ATENCION")));
            requestDetailBean.setFecGarantia(verifyNode(nodeMap.getNamedItem("FEC_GARANTIA")));
            requestDetailBean.setFecCierre(verifyNode(nodeMap.getNamedItem("FEC_CIERRE")));
            requestDetailBean.setIdStatusAr(verifyNode(nodeMap.getNamedItem("ID_STATUS_AR")));
            requestDetailBean.setIdProducto(verifyNode(nodeMap.getNamedItem("ID_PRODUCTO")));
            requestDetailBean.setIdUnidadAtendida(verifyNode(nodeMap.getNamedItem("ID_UNIDAD_ATENDIDA")));
            requestDetailBean.setIdModelo(verifyNode(nodeMap.getNamedItem("ID_MODELO")));
            requestDetailBean.setIdCliente(verifyNode(nodeMap.getNamedItem("ID_CLIENTE")));
            requestDetailBean.setIdServicio(verifyNode(nodeMap.getNamedItem("ID_SERVICIO")));
            requestDetailBean.setIdNegocio(verifyNode(nodeMap.getNamedItem("ID_NEGOCIO")));

            int vPreFacturacion = -1;
            try
            {
                String prefStrNum = verifyNode(nodeMap.getNamedItem("vPREFACTURACION"));
                vPreFacturacion = Integer.parseInt(prefStrNum.equals("") ? "-1" : prefStrNum);
            }
            catch(NumberFormatException ex)
            {
                ex.printStackTrace();
            }

            requestDetailBean.setvPreFacturacion(vPreFacturacion);
            requestDetailBean.setvComentario(verifyNode(nodeMap.getNamedItem("vCOMENTARIO")));
            requestDetailBean.setSim(verifyNode(nodeMap.getNamedItem("NO_SIM")));
            requestDetailBean.setClaveRechazo(verifyNode(nodeMap.getNamedItem("CLAVE_RECHAZO")));
            requestDetailBean.setIdFalla(verifyNode(nodeMap.getNamedItem("ID_FALLA")));

            requestDetailBean.setNoAr(verifyNode(nodeNoAr.item(0).getFirstChild()));
            requestDetailBean.setDescCliente(verifyNode(nodeDescCliente.item(0).getFirstChild()));
            requestDetailBean.setNoAfiliacion(verifyNode(nodeNoAfiliacion.item(0).getFirstChild()));
            requestDetailBean.setDescServicio(verifyNode(nodeDescServicio.item(0).getFirstChild()));
            requestDetailBean.setSintoma(verifyNode(nodeSintoma.item(0).getFirstChild()));
            requestDetailBean.setConcepto(verifyNode(nodeConcepto.item(0).getFirstChild()));
            requestDetailBean.setDescCorta(verifyNode(nodeDescCorta.item(0).getFirstChild()));
            requestDetailBean.setBitacora(verifyNode(nodeBitacora.item(0).getFirstChild()));
            requestDetailBean.setNotasRemedy(verifyNode(nodeNotasRemedy.item(0).getFirstChild()));
            requestDetailBean.setDescEquipo(verifyNode(nodeDescEquipo.item(0).getFirstChild()));
            requestDetailBean.setEquipo(verifyNode(nodeEquipo.item(0).getFirstChild()));
            requestDetailBean.setNoSerie(verifyNode(nodeNoSerie.item(0).getFirstChild()));
            requestDetailBean.setDireccion(verifyNode(nodeDireccion.item(0).getFirstChild()));
            requestDetailBean.setColonia(verifyNode(nodeColonia.item(0).getFirstChild()));
            requestDetailBean.setPoblacion(verifyNode(nodePoblacion.item(0).getFirstChild()));
            requestDetailBean.setEstado(verifyNode(nodeEstado.item(0).getFirstChild()));
            requestDetailBean.setCp(verifyNode(nodeCp.item(0).getFirstChild()));
            requestDetailBean.setDescNegocio(verifyNode(nodeDescNegocio.item(0).getFirstChild()));
            requestDetailBean.setTelefono(verifyNode(nodeTelefono.item(0).getFirstChild()));
            requestDetailBean.setCaja(verifyNode(nodeCaja.item(0).getFirstChild()));
            requestDetailBean.setSoftware(verifyNode(nodeSoftware.item(0).getFirstChild()));
            requestDetailBean.setConnectivity(verifyNode(nodeConn.item(0).getFirstChild()));

            return requestDetailBean;
        }
        else
        {
            resultBean = new ResultBean();
            NetworkBean networkBean = (NetworkBean) response;
            resultBean.setMessage(networkBean.getMessage());
        }

        return resultBean;
    }

    public static Object httpRequest(String url)
    {
        HttpURLConnection urlConnection = null;
        NetworkBean networkBean;

        try
        {
            if(MicroformasApp.isOnline())
            {
                URL requestUrl = new URL(url);
                urlConnection = (HttpURLConnection) requestUrl.openConnection();

                urlConnection.setConnectTimeout(CONN_TIMER);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                InputStream docStream = urlConnection.getInputStream();
                return docBuilder.parse(docStream);
            }
            else
            {
                networkBean = new NetworkBean();
                networkBean.setStatus(NetworkBean.STATUS_OFFLINE);
                networkBean.setMessage("Error de conexión. No tiene conexión a Internet.");
            }
        }
        catch(SocketTimeoutException exTimeout) {
            networkBean = new NetworkBean();
            networkBean.setStatus(NetworkBean.STATUS_TIMEOUT);
            networkBean.setMessage("Error en el servidor. Tiempo de espera agotado.");
        }
        catch (Exception ex)
        {
            networkBean = new NetworkBean();
            networkBean.setStatus(NetworkBean.STATUS_EXCEPTION);
            networkBean.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }

        return networkBean;
    }

    public static String verifyNode(Node currentNode)
    {
        if(currentNode == null)
        {
            return "";
        }

        return currentNode.getNodeValue();
    }

// =================================================================================================
	public static NotificationsUpdateBean getUpdates(String id)
    {   // Módulo de getUpdates
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE = URL_SERVER + "/getUpdates.asp";
		NotificationsUpdateBean notificationsUpdateBean = new NotificationsUpdateBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try
        {
			String fullHTTP	= URL_UPDATE + "?i=" + id;
            Log.d("Microformas", fullHTTP);

            final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			
			//Log.i("com.artefacto.info",fullHTTP);
			
			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			notificationsUpdateBean.setConnStatus(response.getStatusLine().getStatusCode());

			if(notificationsUpdateBean.getConnStatus() == 200)
            {
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				NamedNodeMap	nodeMap		= node.item(0).getAttributes();
				NodeList 		children	= node.item(0).getChildNodes();

				//Agregar fecha de catálogos
				NodeList 		nodeStatusList 			= doc.getElementsByTagName("seq");
				NamedNodeMap	nodeStatusMap			= nodeStatusList.item(0).getAttributes();

				NodeList 		nodeProductosList		= doc.getElementsByTagName("ps");
				NamedNodeMap	nodeProductosMap		= nodeProductosList.item(0).getAttributes();

				NodeList 		nodeCambiosList 		= doc.getElementsByTagName("csar");
				NamedNodeMap	nodeCambiosMap			= nodeCambiosList.item(0).getAttributes();

				NodeList 		nodeViaticosList 		= doc.getElementsByTagName("v");
				NamedNodeMap	nodeViaticosMap			= nodeViaticosList.item(0).getAttributes();

				NodeList 		nodeSparePartList 		= doc.getElementsByTagName("sp");
				NamedNodeMap	nodeSparePartMap		= nodeSparePartList.item(0).getAttributes();

				NodeList 		nodeInsumosList 		= doc.getElementsByTagName("i");
				NamedNodeMap	nodeInsumosMap			= nodeInsumosList.item(0).getAttributes();

				NodeList 		nodeAlmacenesList 		= doc.getElementsByTagName("al");
				NamedNodeMap	nodeAlmacenesMap		= nodeAlmacenesList.item(0).getAttributes();

				NodeList 		nodeMarcasList 			= doc.getElementsByTagName("m");
				NamedNodeMap	nodeMarcasMap			= nodeMarcasList.item(0).getAttributes();

				NodeList 		nodeMSparePartsList 	= doc.getElementsByTagName("ms");
				NamedNodeMap	nodeMSparePartsMap		= nodeMSparePartsList.item(0).getAttributes();

				NodeList 		nodeModelosList 		= doc.getElementsByTagName("mod");
				NamedNodeMap	nodeModelosMap			= nodeModelosList.item(0).getAttributes();

				NodeList 		nodeServiciosList 		= doc.getElementsByTagName("ser");
				NamedNodeMap	nodeServiciosMap		= nodeServiciosList.item(0).getAttributes();

				NodeList 		nodeCausasList 			= doc.getElementsByTagName("ca");
				NamedNodeMap	nodeCausasMap			= nodeCausasList.item(0).getAttributes();

				NodeList 		nodeCausasRechazosList 	= doc.getElementsByTagName("cre");
				NamedNodeMap	nodeCausasRechazoMap	= nodeCausasRechazosList.item(0).getAttributes();

				NodeList 		nodeEspecCauRechList 	= doc.getElementsByTagName("ecr");
				NamedNodeMap	nodeEspecCauRechMap		= nodeEspecCauRechList.item(0).getAttributes();

				NodeList 		nodeSolucionesList 		= doc.getElementsByTagName("sol");
				NamedNodeMap	nodeSolucionesMap		= nodeSolucionesList.item(0).getAttributes();

				NodeList 		nodeGruposList 			= doc.getElementsByTagName("gr");
				NamedNodeMap	nodeGruposMap			= nodeGruposList.item(0).getAttributes();

				NodeList 		nodeGruposCliList 		= doc.getElementsByTagName("gc");
				NamedNodeMap	nodeGruposCliMap		= nodeGruposCliList.item(0).getAttributes();
				
				NodeList 		nodeCodigos0List 		= doc.getElementsByTagName("cin0");
				NamedNodeMap	nodeCodigos0Map			= nodeCodigos0List.item(0).getAttributes();
				
				NodeList 		nodeCodigos1List 		= doc.getElementsByTagName("cin1");
				NamedNodeMap	nodeCodigos1Map			= nodeCodigos1List.item(0).getAttributes();
				
				NodeList 		nodeCodigos2List 		= doc.getElementsByTagName("cin2");
				NamedNodeMap	nodeCodigos2Map			= nodeCodigos2List.item(0).getAttributes();

                NodeList nodeClients = doc.getElementsByTagName("clients");
                String dateClient = nodeClients.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodeConnectivity = doc.getElementsByTagName("connectivity");
                String dateConnectivity = nodeConnectivity.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodeSoftware = doc.getElementsByTagName("software");
                String dateSoftware = nodeSoftware.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodeCausaRetiro = doc.getElementsByTagName("causaretiro");
                String dateCausaRetiro = nodeCausaRetiro.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodeEtiquetas = doc.getElementsByTagName("etiquetas");
                String dateEtiquetas = nodeEtiquetas.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodeFailsFound = doc.getElementsByTagName("fails_found");
                String dateFailsFound = nodeFailsFound.item(0).getChildNodes().item(0).getNodeValue();
                /*JDOS 30/01/2018 Se recuperan fecha de ultimos campos agregados*/
                NodeList nodeCalidadBillete = doc.getElementsByTagName("calidadBillete");
                String dateCalidadBillete = nodeCalidadBillete.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodeCondicionSite = doc.getElementsByTagName("conditionSite");
                String dateCondicionSite = nodeCondicionSite.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodecCalidadBillete = doc.getElementsByTagName("clientqualityTicket");
                String datecCalidadBillete = nodecCalidadBillete.item(0).getChildNodes().item(0).getNodeValue();

                NodeList nodecCondicionSite = doc.getElementsByTagName("clientconditionSite");
                String datecCondicionSite = nodecCondicionSite.item(0).getChildNodes().item(0).getNodeValue();
                /*Fin*/
                NodeList 		nodeClientesModList		= doc.getElementsByTagName("cm");
				NamedNodeMap	nodeClientesModMap		= nodeClientesModList.item(0).getAttributes();
				
				NodeList 		nodeServSolList			= doc.getElementsByTagName("sersol");
				NamedNodeMap	nodeServSolMap			= nodeServSolList.item(0).getAttributes();
				
				NodeList 		nodeTipoFallaList		= doc.getElementsByTagName("tf");
				NamedNodeMap	nodeTipoFallaMap		= nodeTipoFallaList.item(0).getAttributes();
				
				NodeList 		nodeEspTipoFallaList	= doc.getElementsByTagName("etf");
				NamedNodeMap	nodeEspTipoFallaMap		= nodeEspTipoFallaList.item(0).getAttributes();
				
				NodeList 		nodeServCauaList		= doc.getElementsByTagName("serca");
				NamedNodeMap	nodeServCauMap			= nodeServCauaList.item(0).getAttributes();
				
				NodeList 		nodeIngenierosList		= doc.getElementsByTagName("ic");
				NamedNodeMap	nodeIngenierosMap		= nodeIngenierosList.item(0).getAttributes();

				NamedNodeMap nuevasNodeMap 		= children.item(0).getAttributes();
				NamedNodeMap abiertasNodeMap 	= children.item(1).getAttributes();
				NamedNodeMap cerradasNodeMap 	= children.item(2).getAttributes();
				NamedNodeMap pendientesNodeMap 	= children.item(3).getAttributes();
				NamedNodeMap unidadesNodeMap 	= children.item(4).getAttributes();
				NamedNodeMap enviosPendNodeMap = children.item(7).getAttributes();
				NamedNodeMap recepcionesNodeMap = children.item(8).getAttributes();
				NamedNodeMap direccionesNodeMap = children.item(9).getAttributes();

				notificationsUpdateBean.setTime(nodeMap.getNamedItem("t").getNodeValue());
				notificationsUpdateBean.setStatus(nodeMap.getNamedItem("s").getNodeValue());
				notificationsUpdateBean.setVersion(nodeMap.getNamedItem("v").getNodeValue());

				notificationsUpdateBean.setNuevasNumber(nuevasNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setNuevasMD5(nuevasNodeMap.getNamedItem("md5").getNodeValue());

				notificationsUpdateBean.setAbiertasNumber(abiertasNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setAbiertasMD5(abiertasNodeMap.getNamedItem("md5").getNodeValue());

				notificationsUpdateBean.setCerradasNumber(cerradasNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setCerradasMD5(cerradasNodeMap.getNamedItem("md5").getNodeValue());

				notificationsUpdateBean.setPendientesNumber(pendientesNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setPendientesMD5(pendientesNodeMap.getNamedItem("md5").getNodeValue());

				notificationsUpdateBean.setUnidadesNumber(unidadesNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setUnidadesMD5(unidadesNodeMap.getNamedItem("md5").getNodeValue());

                NodeList invSuppliesNode = doc.getElementsByTagName("inv_supplies");
                notificationsUpdateBean.setInvSuppliesNumber(invSuppliesNode.item(0).getChildNodes().item(0).getFirstChild().getNodeValue());
                notificationsUpdateBean.setInvSuppliesMD5(invSuppliesNode.item(0).getChildNodes().item(1).getFirstChild().getNodeValue());

				notificationsUpdateBean.setEnviosPendNumber(enviosPendNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setEnviosPendMD5(enviosPendNodeMap.getNamedItem("md5").getNodeValue());
				
				notificationsUpdateBean.setRecepcionesNumber(recepcionesNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setRecepcionesMD5(recepcionesNodeMap.getNamedItem("md5").getNodeValue());

				notificationsUpdateBean.setDireccionesNumber(direccionesNodeMap.getNamedItem("l").getNodeValue());
				notificationsUpdateBean.setDireccionesMD5(direccionesNodeMap.getNamedItem("md5").getNodeValue());

				//Aquí van todas las fechas recientes de los catálogos
				notificationsUpdateBean.setStatusDate(nodeStatusMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setProductosDate(nodeProductosMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setCambiosDate(nodeCambiosMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setViaticosDate(nodeViaticosMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setSparePartDate(nodeSparePartMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setInsumosDate(nodeInsumosMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setAlmacenesDate(nodeAlmacenesMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setMarcasDate(nodeMarcasMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setMSparePartsDate(nodeMSparePartsMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setModelosDate(nodeModelosMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setServiciosDate(nodeServiciosMap.getNamedItem("d").getNodeValue());

				notificationsUpdateBean.setCausasDate(nodeCausasMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setCausasRechazoDate(nodeCausasRechazoMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setEspecificaCausasRechazoDate(nodeEspecCauRechMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setSolucionesDate(nodeSolucionesMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setGruposDate(nodeGruposMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setGruposClientesDate(nodeGruposCliMap.getNamedItem("d").getNodeValue());
				
				notificationsUpdateBean.setCodigos0Date(nodeCodigos0Map.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setCodigos1Date(nodeCodigos1Map.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setCodigos2Date(nodeCodigos2Map.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setClientesModDate(nodeClientesModMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setServSolDate(nodeServSolMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setTipoFallaDate(nodeTipoFallaMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setEspTipoFallaDate(nodeEspTipoFallaMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setServCauDate(nodeServCauMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setIngenierosDate(nodeIngenierosMap.getNamedItem("d").getNodeValue());
				notificationsUpdateBean.setDateClients(dateClient);
				notificationsUpdateBean.setDateConnectivity(dateConnectivity);
				notificationsUpdateBean.setDateSoftware(dateSoftware);
                notificationsUpdateBean.setDateCausaRetiro(dateCausaRetiro);
                notificationsUpdateBean.setDateEtiquetas(dateEtiquetas);
				notificationsUpdateBean.setFailsFoundDate(dateFailsFound);
				/*JDOS 30/01/2018 SE AGREGA CAMBIO*/
				notificationsUpdateBean.setCondicionSiteDate(dateCondicionSite);
				notificationsUpdateBean.setCalidadBilleteDate(dateCalidadBillete);
				notificationsUpdateBean.setCcalidadBilleteDate(datecCalidadBillete);
				notificationsUpdateBean.setCcondicionSiteDate(datecCondicionSite);

				/*Nota: Para agregar un nuevo catálogo: 
				1. Agregar base de datos en SQLiteHelper
				2. Crear su respectivo bean
				2. Agregar MD5 o Fecha aquí
				3. Agregar el valor de la fecha o MD5 en NotificationsUpdateBean
				4. Agregar su llenado en RegisterUserConnTask y en GetUpdatesService
				5. Agregar al final de estos mismos, una llamada a HTTPConnection solicitando el XML
				6. Crear la clase en HTTPConnections que llene los datos 
				 */
			}
		}
		catch (Exception e)
        {
			notificationsUpdateBean.setConnStatus(100);
			e.printStackTrace();
		}

		return notificationsUpdateBean;
	}

	public static ArrayList<SolicitudesBean> getSolicitudes(String id, int type)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getSolicitudes.asp";
        Log.i("e->","xD");
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		HttpResponse 		response 		= null;
		ArrayList<SolicitudesBean>	solicitudesBean = new ArrayList<SolicitudesBean>();
		try{
			String				fullHTTP	= URL_UPDATE + "?i=" + id;
			String				sendTypeToURL = "n";

			if(type == Constants.DATABASE_NUEVAS)
                sendTypeToURL = "n";
			else if(type == Constants.DATABASE_ABIERTAS)
				sendTypeToURL = "a";
			else if(type == Constants.DATABASE_CERRADAS)
				sendTypeToURL = "c";
			else if(type == Constants.DATABASE_PENDIENTES)
				sendTypeToURL = "p";

            fullHTTP += "&s=" + sendTypeToURL;
			final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient	httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 	pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			response 				= httpClient.execute(pagePost);

			solicitudesBean.add(new SolicitudesBean());
			solicitudesBean.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			//Respuesta del servidor
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("e");
				int				totalNodes	= node.getLength();
				NamedNodeMap	nodeMap;

				//doc.getElementsByTagName("e").item(0).getAttributes().getNamedItem("ID_AR").getNodeValue() != null){
				for(int i = 0; i < totalNodes; i++){
					Node nodeAttributes = node.item(i);
					if(nodeAttributes.getNodeType() == Node.ELEMENT_NODE){
						nodeMap = nodeAttributes.getAttributes();

						if(i > 0){
							solicitudesBean.add(new SolicitudesBean());
						}

						solicitudesBean.get(i).setType(String.valueOf(type));
						solicitudesBean.get(i).setIdAr(nodeMap.getNamedItem("ID_AR").getNodeValue());
						solicitudesBean.get(i).setIdStatusAr(nodeMap.getNamedItem("ID_STATUS_AR").getNodeValue());
					}
				}
			}
		}
		catch (Exception e){
			solicitudesBean.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return solicitudesBean;
	}

	public static boolean setStatus(String connIdRequest, String connIdUser, String connNewStatus, String connComenatario){   // Método para cambiar el status de una solicitud
		boolean isChanged = false;
        String com = null;

        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String URL_SET_STATUS = URL_SERVER + "/setStatus.asp";
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
        try{
            com = URLEncoder.encode(connComenatario.replaceAll("'", "''"), "UTF-8");
        }
        catch (UnsupportedEncodingException e1){
            e1.printStackTrace();
        }

		try{
			String				fullHTTP	= URL_SET_STATUS + "?i=" + connIdRequest + "&s=" + connNewStatus + "&u=" + connIdUser + "&c=" + com;
            Log.e("ComentarioStatus",fullHTTP);
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				
				NodeList 	 n		= doc.getElementsByTagName("d");
				NamedNodeMap r		= n.item(0).getAttributes();

				//Revisa si el usuario es correcto
				if(r.getNamedItem("r").getNodeValue().equals("OK")){
					//Se realizó el procedure, regresando true
					isChanged = true;
				}
				
			}
		}
		catch(SocketTimeoutException ex){
			isChanged = false;
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return isChanged;
	}

	public static ArrayList<StatusBean> getStatusCatalog()
    {   // Actualiza el Catálogo de Status
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getStatusAR.asp";
		ArrayList<StatusBean> statusBeanArray = new ArrayList<StatusBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

            //Respuesta del servidor
			statusBeanArray.add(new StatusBean());
			statusBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(statusBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								statusBeanArray.add(new StatusBean()); 

							nodeMap = child.getAttributes();
							statusBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							statusBeanArray.get(i).setIsNuevas(nodeMap.getNamedItem("B").getNodeValue());
							statusBeanArray.get(i).setIsCerradas(nodeMap.getNamedItem("C").getNodeValue());
							statusBeanArray.get(i).setIsPendientes(nodeMap.getNamedItem("D").getNodeValue());
							statusBeanArray.get(i).setIsAbiertas(nodeMap.getNamedItem("E").getNodeValue());
                            statusBeanArray.get(i).setIsSolicitudAlmacen(nodeMap.getNamedItem("F").getNodeValue());
							statusBeanArray.get(i).setIsSolicitudViaticos(nodeMap.getNamedItem("G").getNodeValue());

							statusBeanArray.get(i).setActivo(nodeMap.getNamedItem("H").getNodeValue());
							statusBeanArray.get(i).setOrden(nodeMap.getNamedItem("I").getNodeValue());
                            statusBeanArray.get(i).setActive_mobile_notification(nodeMap.getNamedItem("J").getNodeValue());
							statusBeanArray.get(i).setDescStatus(child.getTextContent());

							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			statusBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return statusBeanArray;
	}

	public static ArrayList<ClientesCambiosStatusBean> getClientesCambiosStatus()
    {   // Adquiere una lista de los clientes que existen para cambio de status
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getClientesCambiosStatusAR.asp";
		ArrayList<ClientesCambiosStatusBean> clientesCambiosStatusBeanArray = new ArrayList<ClientesCambiosStatusBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			clientesCambiosStatusBeanArray.add(new ClientesCambiosStatusBean());
			clientesCambiosStatusBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(clientesCambiosStatusBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								clientesCambiosStatusBeanArray.add(new ClientesCambiosStatusBean());
							clientesCambiosStatusBeanArray.get(i).setIdCliente(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			clientesCambiosStatusBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return clientesCambiosStatusBeanArray;
	}

	public static ArrayList<ProductosStatusBean> getProductosCatalog()
    {   // Adquiere una relación productos-status
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getProductosStatusAR.asp";
		ArrayList<ProductosStatusBean> productosStatusBeanArray = new ArrayList<ProductosStatusBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			productosStatusBeanArray.add(new ProductosStatusBean());
			productosStatusBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(productosStatusBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
                Node child;
                NamedNodeMap nodeMap		= null;
				int				i			= 0;

				if( elem != null) {
                    if (elem.hasChildNodes()) {
                        for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								productosStatusBeanArray.add(new ProductosStatusBean());

							nodeMap = child.getAttributes();
							productosStatusBeanArray.get(i).setIdProducto(nodeMap.getNamedItem("A").getNodeValue());
							productosStatusBeanArray.get(i).setIdStatus(nodeMap.getNamedItem("B").getNodeValue());

							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			productosStatusBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return productosStatusBeanArray;
	}

	public static ArrayList<CambiosStatusBean> getCambiosCatalog(String idCliente)
    {   // Adquiere una lista de los status iniciales y finales acorde al cliente
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCambiosStatusAR.asp";
		ArrayList<CambiosStatusBean> cambiosStatusBeanArray = new ArrayList<CambiosStatusBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?i=" + idCliente;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			cambiosStatusBeanArray.add(new CambiosStatusBean());
			cambiosStatusBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(cambiosStatusBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
                Node child;
                NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null) {
                    if (elem.hasChildNodes()) {
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								cambiosStatusBeanArray.add(new CambiosStatusBean());

							nodeMap = child.getAttributes();
							cambiosStatusBeanArray.get(i).setIdStatusIni(nodeMap.getNamedItem("A").getNodeValue());
							cambiosStatusBeanArray.get(i).setIdStatusFin(nodeMap.getNamedItem("B").getNodeValue());

							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			cambiosStatusBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return cambiosStatusBeanArray;
	}

	public static ArrayList<ViaticosBean> getViaticos()
    {   // Adquiere una lista de los viáticos posibles
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getViaticos.asp";
		ArrayList<ViaticosBean> viaticosBeanArray = new ArrayList<ViaticosBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			viaticosBeanArray.add(new ViaticosBean());
			viaticosBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(viaticosBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node elem = node.item(0);
                Node child;
				NamedNodeMap	nodeMap		= null;
				int i = 0;

                if (elem != null) {
                    if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								viaticosBeanArray.add(new ViaticosBean());

							nodeMap = child.getAttributes();
							viaticosBeanArray.get(i).setIdViatico(nodeMap.getNamedItem("A").getNodeValue());
							viaticosBeanArray.get(i).setDescViatico(nodeMap.getNamedItem("B").getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			viaticosBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return viaticosBeanArray;
	}

	public static ArrayList<SparePartsBean> getSpareParts()
    {   // Adquiere una lista de los Spare Parts
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getSpareParts.asp";

		ArrayList<SparePartsBean> sparePartsBeanArray = new ArrayList<SparePartsBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			sparePartsBeanArray.add(new SparePartsBean());
			sparePartsBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(sparePartsBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node elem = node.item(0);
                Node child;
				NamedNodeMap	nodeMap		= null;
				int				i = 0;

                if (elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								sparePartsBeanArray.add(new SparePartsBean());

							nodeMap = child.getAttributes();
							sparePartsBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							sparePartsBeanArray.get(i).setDescSparePart(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			sparePartsBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return sparePartsBeanArray;
	}

	public static ArrayList<InsumosBean> getInsumos()
    {   // Adquiere la lista de Insumos
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getInsumos.asp";
		ArrayList<InsumosBean> insumosBeanArray = new ArrayList<InsumosBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			insumosBeanArray.add(new InsumosBean());
			insumosBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(insumosBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
                                insumosBeanArray.add(new InsumosBean());
							nodeMap = child.getAttributes();
							insumosBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							insumosBeanArray.get(i).setIdCliente(nodeMap.getNamedItem("B").getNodeValue());
							insumosBeanArray.get(i).setIdTipoInsumo(nodeMap.getNamedItem("C").getNodeValue());
							insumosBeanArray.get(i).setDescInsumo(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			insumosBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return insumosBeanArray;
	}

	public static ArrayList<AlmacenesBean> getAlmacenes()
    {   // Adquiere la lista de Almacenes
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getAlmacenes.asp";
		ArrayList<AlmacenesBean> almacenesBeanArray = new ArrayList<AlmacenesBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			almacenesBeanArray.add(new AlmacenesBean());
			almacenesBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(almacenesBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int i = 0;

                if (elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								almacenesBeanArray.add(new AlmacenesBean());	
							nodeMap = child.getAttributes();
							almacenesBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							almacenesBeanArray.get(i).setDescAlmacen(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			almacenesBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return almacenesBeanArray;
	}

	public static ArrayList<CarriersBean> getCarriers()
    {   // Consigue los carriers
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCarriers.asp";
		ArrayList<CarriersBean> carriersBeanArray = new ArrayList<CarriersBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			carriersBeanArray.add(new CarriersBean());
			carriersBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(carriersBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
                Node elem = node.item(0);
                Node child;
				NamedNodeMap	nodeMap		= null;
                int i = 0;

                if ( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								carriersBeanArray.add(new CarriersBean());	
							nodeMap = child.getAttributes();
							carriersBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							carriersBeanArray.get(i).setDescCarrier(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			carriersBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return carriersBeanArray;
	}

	public static ArrayList<MarcasBean> getMarcas()
    {   // Adquiere la lista de Marcas
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getMarcas.asp";
		ArrayList<MarcasBean> marcasBeanArray = new ArrayList<MarcasBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			marcasBeanArray.add(new MarcasBean());
			marcasBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(marcasBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
                int i = 0;

                if (elem != null){
					if (elem.hasChildNodes()){
                        for (child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								marcasBeanArray.add(new MarcasBean());	
							nodeMap = child.getAttributes();
							marcasBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							marcasBeanArray.get(i).setIdProducto(nodeMap.getNamedItem("B").getNodeValue());
							marcasBeanArray.get(i).setDescMarca(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			marcasBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return marcasBeanArray;
	}

	public static ArrayList<MSparePartsBean> getMSpareParts()
    {   // Adquiere la lista de Modelo Spare Parts
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getModeloSpareParts.asp";
		ArrayList<MSparePartsBean> mSparePartsBeanArray = new ArrayList<MSparePartsBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response = httpClient.execute(pagePost);

			//Respuesta del servidor
			mSparePartsBeanArray.add(new MSparePartsBean());
			mSparePartsBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(mSparePartsBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node elem = node.item(0);
                Node child;
				NamedNodeMap	nodeMap		= null;
				int				i = 0;

                if (elem != null) {
                    if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								mSparePartsBeanArray.add(new MSparePartsBean());	
							nodeMap = child.getAttributes();
							mSparePartsBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							mSparePartsBeanArray.get(i).setIdModelo(nodeMap.getNamedItem("B").getNodeValue());
							mSparePartsBeanArray.get(i).setIdSparePart(nodeMap.getNamedItem("C").getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			mSparePartsBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return mSparePartsBeanArray;
	}

    /*
    * 22/03/2017 JDOS
    * Conexion para obtener lista de causas de retiro
    * */

    public static Object getCausaRetiro(){
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getCausaRetiro.asp";

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            ArrayList<CausaRetiroBean> listCausaRetiro = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("causaRetiro");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                CausaRetiroBean causaRetiro = new CausaRetiroBean();
                causaRetiro.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                causaRetiro.setIdcliente(Integer.parseInt(childNodes.item(1).getFirstChild().getNodeValue()));
                causaRetiro.setDescCausaRetiro(verifyNode(childNodes.item(2).getFirstChild()));
                causaRetiro.setStatus(verifyNode(childNodes.item(3).getFirstChild()));
                listCausaRetiro.add(causaRetiro);
            }

            return listCausaRetiro;
        }

        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    /*
    * 16/08/2017 JDOS
    * Conexion para obtener lista de Etiquetas
    * */

    public static Object getEtiquetas(){

        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getEtiquetas.asp";

        Object response = httpRequest(url);
        if(response instanceof Document)
        {
            ArrayList<EtiquetasBean> listEtiqueta = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("etiquetas");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                NodeList childNodes = nodeList.item(i).getChildNodes();
                EtiquetasBean etiquetasBean = new EtiquetasBean();
                etiquetasBean.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                etiquetasBean.setIdcliente(Integer.parseInt(childNodes.item(1).getFirstChild().getNodeValue()));
                etiquetasBean.setEtiqueta(verifyNode(childNodes.item(2).getFirstChild()));
                listEtiqueta.add(etiquetasBean);
            }

            return listEtiqueta;
        }

        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());

        return resultBean;
    }

    /*
    * JDOS 30/01/2018
    * Conexion para obtener Lista de Catalogo de calidad de billete*/

    public static Object getCalidadBillete(){
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getCalidadBillete.asp";

        Object response = httpRequest(url);

        if(response instanceof Document){
            ArrayList<CalidadBilleteBean> listCalidadBillete = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("qualityTicket");

            for (int i = 0; i < nodeList.getLength(); i++){
                NodeList childNodes = nodeList.item(i).getChildNodes();
                CalidadBilleteBean calidadBilleteBean = new CalidadBilleteBean();

                calidadBilleteBean.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                calidadBilleteBean.setDescCalidadBillete(verifyNode(childNodes.item(1).getFirstChild()));
                listCalidadBillete.add(calidadBilleteBean);
            }
            return listCalidadBillete;
        }
        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());
        return resultBean;
    }

    public static Object getCondicionSite(){
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getCondicionSite.asp";

        Object response = httpRequest(url);

        if(response instanceof Document){
            ArrayList<CondicionSiteBean> condicionSiteBeanArrayList = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("conditionSite");

            for (int i = 0; i < nodeList.getLength(); i++){
                NodeList childNodes = nodeList.item(i).getChildNodes();
                CondicionSiteBean condicionSiteBean = new CondicionSiteBean();

                condicionSiteBean.setId(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                condicionSiteBean.setDescCondicionSite(verifyNode(childNodes.item(1).getFirstChild()));
                condicionSiteBeanArrayList.add(condicionSiteBean);
            }
            return condicionSiteBeanArrayList;
        }
        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());
        return resultBean;
    }

    public static Object getClienteCalidadBillete(){
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getClienteCalidadBillete.asp";

        Object response = httpRequest(url);
        if(response instanceof Document){
            ArrayList<ClienteCalidadBilleteBean> listCalidadBillete = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("clientqualityTicket");

            for (int i = 0; i < nodeList.getLength(); i++){
                NodeList childNodes = nodeList.item(i).getChildNodes();
                ClienteCalidadBilleteBean clienteCalidadBilleteBean = new ClienteCalidadBilleteBean();

                clienteCalidadBilleteBean.setIdClienteCalidadBillete(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                clienteCalidadBilleteBean.setIdCalidadBillete(Integer.parseInt(childNodes.item(1).getFirstChild().getNodeValue()));
                clienteCalidadBilleteBean.setIdCliente(Integer.parseInt(childNodes.item(2).getFirstChild().getNodeValue()));
                listCalidadBillete.add(clienteCalidadBilleteBean);
            }
            return listCalidadBillete;
        }
        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());
        return resultBean;
    }

    public static Object getClienteCondicionSite(){
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String url = URL_SERVER + "/getClienteCondicionSite.asp";

        Object response = httpRequest(url);

        if(response instanceof Document){
            ArrayList<ClienteCondicionSiteBean> listCondicionesSite = new ArrayList<>();

            Document doc = (Document) response;
            NodeList nodeList = doc.getElementsByTagName("clientconditionSite");

            for (int i = 0; i < nodeList.getLength(); i++){
                NodeList childNodes = nodeList.item(i).getChildNodes();
                ClienteCondicionSiteBean clienteCondicionSiteBean = new ClienteCondicionSiteBean();

                clienteCondicionSiteBean.setIdClienteCondicionSite(Integer.parseInt(childNodes.item(0).getFirstChild().getNodeValue()));
                clienteCondicionSiteBean.setIdCondicionSite(Integer.parseInt(childNodes.item(1).getFirstChild().getNodeValue()));
                clienteCondicionSiteBean.setIdCliente(Integer.parseInt(childNodes.item(2).getFirstChild().getNodeValue()));

                listCondicionesSite.add(clienteCondicionSiteBean);
            }
            return listCondicionesSite;
        }
        ResultBean resultBean = new ResultBean();
        NetworkBean networkBean = (NetworkBean) response;
        resultBean.setMessage(networkBean.getMessage());
        return resultBean;
    }

    /*Fin*/

	public static ArrayList<ModelosBean> getModelos() {   // Adquiere la lista de Modelos
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getModelos.asp";
		ArrayList<ModelosBean> modelosBeanArray = new ArrayList<ModelosBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient httpClient = new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			modelosBeanArray.add(new ModelosBean());
			modelosBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(modelosBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			modeloNode;
				Node        	skuNode; 
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								modelosBeanArray.add(new ModelosBean());	
							nodeMap = child.getAttributes();

							modeloNode = child.getFirstChild();
							skuNode    = child.getLastChild();
							
							modelosBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							modelosBeanArray.get(i).setIdMarca(nodeMap.getNamedItem("B").getNodeValue());
							modelosBeanArray.get(i).setIdGPRS(nodeMap.getNamedItem("C").getNodeValue());
							//modelosBeanArray.get(i).setDescModelo(child.getTextContent());
							modelosBeanArray.get(i).setDescModelo(modeloNode.getTextContent());
							modelosBeanArray.get(i).setSku(skuNode.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			modelosBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return modelosBeanArray;
	}

	public static String sendComment(String idAr,String idTecnico, String comentario)
    {   // Envía el comentario
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String ret = "Sin Red. se intentara mas tarde";
		String URL_SEND_COMMENT	= URL_SERVER + "/envioComentario.asp";

		String ia="";
		String i="";
		String n="";

		
		try{
			ia = URLEncoder.encode(idAr, "UTF-8");
			i = URLEncoder.encode(idTecnico, "UTF-8");
			n = URLEncoder.encode(comentario.replaceAll("'", "''"), "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String				fullHTTP	= URL_SEND_COMMENT + "?ia=" + ia + "&i=" + i + "&n=" + n;
			
			//Log.i("durl", fullHTTP);
			
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);
			

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200) {
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					//Revisa si el usuario es correcto
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Se realizó el procedure, regresando true
						ret = "OK";
					}
					else{
						ret = "Error en la BD :" + r.getNamedItem("desc").getNodeValue();
					}
				}
				else{
					ret = "id de solicitud y tecnico no concuerdan.";
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return ret;
	}

	public static GenericResultBean sendSpareRequest(String idAr, String idUsuario, String idAlmacen,
        String refacciones, String idUrgencia, String notas, String tipoServicio, String idDireccion,
        String fecha)
	{   // Envía las refacciones
		GenericResultBean genericResultBean = new GenericResultBean();
		
		String ret = "Sin Red. se intentara mas tarde";
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SEND_COMMENT	= URL_SERVER + "/peticionRefaccion.asp";
		try
		{
			String fullHTTP	= URL_SEND_COMMENT + "?";
			
			if(idAr.equals("")) {
				fullHTTP += "ia=" + URLEncoder.encode("0", "UTF-8");
			}
			else {
				fullHTTP += "ia=" + URLEncoder.encode(idAr, "UTF-8");
			}
			
			if(idUsuario.equals("")) {
				fullHTTP += "&iTec=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&iTec=" + URLEncoder.encode(idUsuario, "UTF-8");
			}
			
			if(idUsuario.equals("")) {
				fullHTTP += "&iAlm=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&iAlm=" + URLEncoder.encode(idAlmacen, "UTF-8");
			}
			
			fullHTTP += "&refaccionInfo=" + URLEncoder.encode(refacciones, "UTF-8");
	
			if(idUrgencia.equals("")) {
				fullHTTP += "&idU=" + URLEncoder.encode("0", "UTF-8");
			}
			else {
				fullHTTP += "&idU=" + idUrgencia;
			}
			
			if(notas.equals("")) {
				fullHTTP += "&notes=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&notes=" + URLEncoder.encode(notas, "UTF-8");
			}
			
			if(tipoServicio.equals("")) {
				fullHTTP += "&typeSer=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&typeSer=" + tipoServicio;
			}
			
			if(idDireccion.equals("")){
				fullHTTP += "&idDir=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&idDir=" + URLEncoder.encode(idDireccion, "UTF-8");
			}
			
			if(fecha.equals("")) {
				fullHTTP += "&fecha=" + URLEncoder.encode("", "UTF-8");
			} else {
                fullHTTP += "&fecha=" + URLEncoder.encode(fecha, "UTF-8");
			}

			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200)
			{
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("pend");

				if (list != null)
				{ 
					if(list.getLength() > 0)
					{
						String posError = "";
						for(int i = 0; i < list.getLength(); i++)
						{
							NamedNodeMap nPend = list.item(i).getAttributes();
							
							try
							{
								String result = nPend.getNamedItem("res").getNodeValue();
								if(result.equals("ERROR"))
								{
									posError = "Error in item " + String.valueOf("i");
								}
							}
							catch(Exception e) {}
						}
						
						if(posError.equals(""))
						{
							genericResultBean.setRes("OK");
							genericResultBean.setDesc("Se ha realizado el cambio de status exitosamente!");
						}
						else
						{
							genericResultBean.setRes("ERROR");
							genericResultBean.setDesc(posError);
						}
					}				
				}
				else
				{
					genericResultBean.setRes("ERROR");
					genericResultBean.setDesc("Los datos son incorrectos");
				}
			}
		}
		catch(SocketTimeoutException ex)
		{
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return genericResultBean;
	}

	public static String sendViaticos(String idRequest, String idUsuario, String lugar,
        String observaciones, String idPrioridad, String viaticos, String costos)
    {
        ValidateConnTask validateConnTask = new ValidateConnTask();
        String ret = "Sin Red. se intentara mas tarde";
        //if(validateConnTask.Conexion(MicroformasApp.getAppContext())){

            HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
            String URL_SEND_COMMENT	= URL_SERVER + "/peticionViatico.asp";

            String ia 	= "";
            String i 	= "";
            String l 	= "";
            String o	= "";
            String u	= "";
            String v	= "";
            String c	= "";

            try{
                ia 	= URLEncoder.encode(idRequest, "UTF-8");
                i 	= URLEncoder.encode(idUsuario, "UTF-8");
                l 	= URLEncoder.encode(lugar, "UTF-8");
                o	= URLEncoder.encode(observaciones, "UTF-8");
                u	= URLEncoder.encode(idPrioridad, "UTF-8");
                v	= URLEncoder.encode(viaticos, "UTF-8");
                c	= URLEncoder.encode(costos, "UTF-8");
            }
            catch (UnsupportedEncodingException e1){
                e1.printStackTrace();
            }

            try{
                String	fullHTTP = URL_SEND_COMMENT + "?ia=" + ia + "&i=" + i + "&l=" + l
                        + "&o=" + o  + "&u=" + u
                        + "&v=" + v + "&c=" + c;

                final HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
                HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

                HttpClient 			httpClient 	= new DefaultHttpClient();
                HttpPost 			pagePost 	= new HttpPost(fullHTTP);
                pagePost.setParams(httpParams);
                pagePost.setHeader("Accept-Charset", "utf-8");
                HttpResponse 		response 	= httpClient.execute(pagePost);

                if(response.getStatusLine().getStatusCode() == 200){
                    DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
                    DocumentBuilder			builder 	= factory.newDocumentBuilder();
                    Document 				doc 		= builder.parse(response.getEntity().getContent());

                    NodeList list = doc.getElementsByTagName("r");

                    if (list!=null && list.getLength()>0){
                        NamedNodeMap r	= list.item(0).getAttributes();

                        //Revisa si el usuario es correcto
                        if(r.getNamedItem("res").getNodeValue().equals("OK")){
                            //Se realizó el procedure, regresando true
                            ret = "OK";
                        }
                        else{
                            ret = "Error en la BD :" + r.getNamedItem("desc").getNodeValue();
                        }
                    }
                    else{
                        ret = "Error de parametros";
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        //}
		return ret;
	}

	public static ArrayList<DireccionesBean> getDirecciones(String id)
    {   // Adquiere la lista de Direcciones
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getDirecciones.asp";
		ArrayList<DireccionesBean> direccionesBeanArray = new ArrayList<DireccionesBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?i=" + id;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			direccionesBeanArray.add(new DireccionesBean());
			direccionesBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(direccionesBeanArray.get(0).getConnStatus() == 200){
	
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0){
								direccionesBeanArray.add(new DireccionesBean());	
							}

							NodeList 	nodeDireccionsList 	= doc.getElementsByTagName("B");
							NodeList 	nodeColoniaList 	= doc.getElementsByTagName("C");
							NodeList 	nodePoblacionList 	= doc.getElementsByTagName("D");
							NodeList 	nodeEstadoList 		= doc.getElementsByTagName("E");
                            NodeList    nodeDefaultList     = doc.getElementsByTagName("F");

							nodeMap = child.getAttributes();
							direccionesBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());


                            direccionesBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
                            direccionesBeanArray.get(i).setDescDireccion(nodeDireccionsList.item(i).getFirstChild().getNodeValue());
							direccionesBeanArray.get(i).setColonia(nodeColoniaList.item(i).getFirstChild().getNodeValue());
							direccionesBeanArray.get(i).setPoblacion(nodePoblacionList.item(i).getFirstChild().getNodeValue());
							direccionesBeanArray.get(i).setEstado(nodeEstadoList.item(i).getFirstChild().getNodeValue());
                            direccionesBeanArray.get(i).setIsDefault(nodeDefaultList.item(i).getFirstChild().getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			direccionesBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return direccionesBeanArray;
	}

	public static ArrayList<UnidadesBean> getUnidades(String id)
    {   // Adquiere la lista de unidades
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getUnidades.asp";
		ArrayList<UnidadesBean> unidadesBeanArray = new ArrayList<UnidadesBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?i=" + id;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);
		

			//Respuesta del servidor
			unidadesBeanArray.add(new UnidadesBean());
			unidadesBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(unidadesBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling()){
							if(i > 0)
								unidadesBeanArray.add(new UnidadesBean());	
							nodeMap = child.getAttributes();
							unidadesBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							unidadesBeanArray.get(i).setIdStatusUnidad(nodeMap.getNamedItem("B").getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			unidadesBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return unidadesBeanArray;
	}

	public static UnitBean getUnidad(String id)
    {   // Adquiere la unidad
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getUnidad.asp";
		UnitBean unitBean = new UnitBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?i=" + id;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			unitBean.setConnStatus(response.getStatusLine().getStatusCode());

			if(unitBean.getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 				= doc.getElementsByTagName("e");
				NamedNodeMap	nodeMap				= node.item(0).getAttributes();

				NodeList 		nodeDescCliente 	= doc.getElementsByTagName("K");
				NodeList 		nodeDescMarca 		= doc.getElementsByTagName("L");
				NodeList		nodeDescModelo 		= doc.getElementsByTagName("M");
				NodeList 		nodeNoSerie 		= doc.getElementsByTagName("N");
				NodeList 		nodeNoInventario	= doc.getElementsByTagName("O");
				NodeList 		nodeNoImei 			= doc.getElementsByTagName("P");
				NodeList 		nodeNoSim 			= doc.getElementsByTagName("Q");
				NodeList 		nodeNoEquipo 		= doc.getElementsByTagName("R");
				NodeList 		nodeDescLlave 		= doc.getElementsByTagName("S");
//				NodeList 		nodeDescSoftware 	= doc.getElementsByTagName("T");
				NodeList 		nodePosInventario 	= doc.getElementsByTagName("U");
				NodeList 		nodeDescStatUnidad	= doc.getElementsByTagName("V");
				NodeList 		nodeStatus 			= doc.getElementsByTagName("W");
				NodeList 		nodeFecAlta 		= doc.getElementsByTagName("X");

				NodeList 		nodeDescAccesorio 	= doc.getElementsByTagName("Y");
				NodeList 		nodeDesccripcionAcc = doc.getElementsByTagName("Z");
				NodeList 		nodeStatusAccesorio = doc.getElementsByTagName("AA");
                NodeList        nodeDescSoftware = doc.getElementsByTagName("DESC_APLICATIVO");
                NodeList        nodeConnectivity = doc.getElementsByTagName("DESC_CONECTIVIDAD");

				unitBean.setId(nodeMap.getNamedItem("A").getNodeValue());
				unitBean.setIdRequestCollection(nodeMap.getNamedItem("B").getNodeValue());
				unitBean.setIdProduct(nodeMap.getNamedItem("C").getNodeValue());
				unitBean.setIsNew(nodeMap.getNamedItem("D").getNodeValue());
				unitBean.setIsBroken(nodeMap.getNamedItem("E").getNodeValue());
				unitBean.setIsWithdrawal(nodeMap.getNamedItem("F").getNodeValue());
				unitBean.setIdClient(nodeMap.getNamedItem("G").getNodeValue());
				unitBean.setIdProductType(nodeMap.getNamedItem("H").getNodeValue());
				unitBean.setIdUnitStatus(nodeMap.getNamedItem("I").getNodeValue());
				unitBean.setIdNewUser(nodeMap.getNamedItem("J").getNodeValue());

				unitBean.setDescClient(nodeDescCliente.item(0).getFirstChild().getNodeValue());
				unitBean.setDescBrand(nodeDescMarca.item(0).getFirstChild().getNodeValue());
				unitBean.setDescModel(nodeDescModelo.item(0).getFirstChild().getNodeValue());
				unitBean.setNoSerie(nodeNoSerie.item(0).getFirstChild().getNodeValue());
				unitBean.setNoInventory(nodeNoInventario.item(0).getFirstChild().getNodeValue());
				unitBean.setNoIMEI(nodeNoImei.item(0).getFirstChild().getNodeValue());
				unitBean.setNoSim(nodeNoSim.item(0).getFirstChild().getNodeValue());
				unitBean.setNoEquipment(nodeNoEquipo.item(0).getFirstChild().getNodeValue());
				unitBean.setDescKey(nodeDescLlave.item(0).getFirstChild().getNodeValue());
				unitBean.setDescSoftware(nodeDescSoftware.item(0).getFirstChild().getNodeValue());
				unitBean.setInventoryPos(nodePosInventario.item(0).getFirstChild().getNodeValue());
				unitBean.setDescUnitStatus(nodeDescStatUnidad.item(0).getFirstChild().getNodeValue());
				unitBean.setStatus(nodeStatus.item(0).getFirstChild().getNodeValue());
				unitBean.setNewDate(nodeFecAlta.item(0).getFirstChild().getNodeValue());
                unitBean.setDescConnectivity(nodeConnectivity.item(0).getFirstChild().getNodeValue());

                try{
					unitBean.setDescAccesory(nodeDescAccesorio.item(0).getFirstChild().getNodeValue());
					unitBean.setDescriptionAccesory(nodeDesccripcionAcc.item(0).getFirstChild().getNodeValue());
					unitBean.setStatusAccesory(nodeStatusAccesorio.item(0).getFirstChild().getNodeValue());
				}
				catch(Exception e){
				}
			}
		}
		catch (Exception e){
			unitBean.setConnStatus(100);
			e.printStackTrace();
		}

		return unitBean;
	}

	public static ArrayList<CausasBean> getCausas()
    {   // Adquiere las Causas
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCausas.asp";
		ArrayList<CausasBean> causasBeanArray = new ArrayList<CausasBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			causasBeanArray.add(new CausasBean());
			causasBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(causasBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
                int i = 0;

                if (elem != null){
					if (elem.hasChildNodes()){
                        for (child = elem.getFirstChild(); child != null; child = child.getNextSibling() ) {
                            if (i > 0)
                                causasBeanArray.add(new CausasBean());

							nodeMap = child.getAttributes();

							causasBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							causasBeanArray.get(i).setIdCliente(nodeMap.getNamedItem("B").getNodeValue());
							causasBeanArray.get(i).setClave(nodeMap.getNamedItem("C").getNodeValue());
							causasBeanArray.get(i).setDescCausa(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			causasBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return causasBeanArray;
	}

	public static ArrayList<CausasRechazoBean> getCausasRechazo()
    {   // Adquiere las causas de rechazo
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCausasRechazo.asp";
		ArrayList<CausasRechazoBean> causasRechazoBeanArray = new ArrayList<CausasRechazoBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			causasRechazoBeanArray.add(new CausasRechazoBean());
			causasRechazoBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(causasRechazoBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

                if (elem != null) {
                    if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								causasRechazoBeanArray.add(new CausasRechazoBean());	

							nodeMap = child.getAttributes();

							causasRechazoBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							causasRechazoBeanArray.get(i).setIdCliente(nodeMap.getNamedItem("B").getNodeValue());
							causasRechazoBeanArray.get(i).setDescCausaRechazo(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			causasRechazoBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return causasRechazoBeanArray;
	}

	public static ArrayList<EspecificaCausasRechazoBean> getEspecificacionCausasRechazo()
    {   // Adquiere las especificaciones de Causa del Rechazo
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getEspecificacionCausaRechazo.asp";
		ArrayList<EspecificaCausasRechazoBean> especificacionCausasRechazoBeanArray = new ArrayList<EspecificaCausasRechazoBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			especificacionCausasRechazoBeanArray.add(new EspecificaCausasRechazoBean());
			especificacionCausasRechazoBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(especificacionCausasRechazoBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for(child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								especificacionCausasRechazoBeanArray.add(new EspecificaCausasRechazoBean());	

							nodeMap = child.getAttributes();

							especificacionCausasRechazoBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							especificacionCausasRechazoBeanArray.get(i).setIdCausaRechazoParent(nodeMap.getNamedItem("B").getNodeValue());
							especificacionCausasRechazoBeanArray.get(i).setDescEspeficicacionCausaRechazo(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			especificacionCausasRechazoBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return especificacionCausasRechazoBeanArray;
	}

	public static ArrayList<GruposBean> getGrupos()
    {   // Adquiere los Grupos
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getGrupos.asp";
		ArrayList<GruposBean> gruposBeanArray = new ArrayList<GruposBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			gruposBeanArray.add(new GruposBean());
			gruposBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(gruposBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
                Node elem = node.item(0);
                Node 			child;
				NamedNodeMap	nodeMap		= null;
                int i = 0;

                if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								gruposBeanArray.add(new GruposBean());	

							nodeMap = child.getAttributes();

							gruposBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							gruposBeanArray.get(i).setDescGrupo(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			gruposBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return gruposBeanArray;
	}

	public static ArrayList<GruposClientesBean> getGruposClientes()
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getGruposClientes.asp";
		ArrayList<GruposClientesBean> gruposClientesBeanArray = new ArrayList<GruposClientesBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			gruposClientesBeanArray.add(new GruposClientesBean());
			gruposClientesBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(gruposClientesBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
                Node child;
                NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if (elem != null) {
                    if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								gruposClientesBeanArray.add(new GruposClientesBean());	

							nodeMap = child.getAttributes();

							gruposClientesBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							gruposClientesBeanArray.get(i).setIdGrupo(nodeMap.getNamedItem("B").getNodeValue());
							gruposClientesBeanArray.get(i).setIdCliente(nodeMap.getNamedItem("C").getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			gruposClientesBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return gruposClientesBeanArray;
	}

	public static ArrayList<CodigosIntervencion0Bean> getCodigosIntervencion0()
    {   // Adquiere los Códigos de Intervención Nivel 0
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCodigosIntervencionNivel0.asp";
		ArrayList<CodigosIntervencion0Bean> codigosIntervencion0BeanArray = new ArrayList<CodigosIntervencion0Bean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			codigosIntervencion0BeanArray.add(new CodigosIntervencion0Bean());
			codigosIntervencion0BeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			
			if(codigosIntervencion0BeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								codigosIntervencion0BeanArray.add(new CodigosIntervencion0Bean());

                            nodeMap = child.getAttributes();

                            codigosIntervencion0BeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							codigosIntervencion0BeanArray.get(i).setClaveCodigo(nodeMap.getNamedItem("B").getNodeValue());
							codigosIntervencion0BeanArray.get(i).setIdCliente(nodeMap.getNamedItem("C").getNodeValue());
							codigosIntervencion0BeanArray.get(i).setReportaInstalacion(nodeMap.getNamedItem("D").getNodeValue());
							codigosIntervencion0BeanArray.get(i).setDescCodigo(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			codigosIntervencion0BeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return codigosIntervencion0BeanArray;
	}

	public static ArrayList<CodigosIntervencion1Bean> getCodigosIntervencion1()
    {   // Adquiere los Códigos de Intervención Nivel 1
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCodigosIntervencionNivel1.asp";
		ArrayList<CodigosIntervencion1Bean> codigosIntervencion1BeanArray = new ArrayList<CodigosIntervencion1Bean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			codigosIntervencion1BeanArray.add(new CodigosIntervencion1Bean());
			codigosIntervencion1BeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			
			if(codigosIntervencion1BeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                            if (i > 0)
                                codigosIntervencion1BeanArray.add(new CodigosIntervencion1Bean());

                            nodeMap = child.getAttributes();

							codigosIntervencion1BeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							codigosIntervencion1BeanArray.get(i).setClaveCodigo(nodeMap.getNamedItem("B").getNodeValue());
							codigosIntervencion1BeanArray.get(i).setIdCliente(nodeMap.getNamedItem("C").getNodeValue());
							codigosIntervencion1BeanArray.get(i).setIdParent0(nodeMap.getNamedItem("D").getNodeValue());
							codigosIntervencion1BeanArray.get(i).setDescCodigo(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			codigosIntervencion1BeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return codigosIntervencion1BeanArray;
	}

    public static ArrayList<ClienteModelosBean> getClientesModelos()
    {   // Adquiere la lista de Clientes Modelos
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String URL_UPDATE	= URL_SERVER + "/getClienteModelos.asp";
        ArrayList<ClienteModelosBean> clienteModelosBeanArray = new ArrayList<ClienteModelosBean>();

        //Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
        try{
            String				fullHTTP	= URL_UPDATE;
            final HttpParams httpParams 	= new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

            HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
            HttpPost 			pagePost 	= new HttpPost(fullHTTP);
            pagePost.setHeader("Accept-Charset","utf-8");
            HttpResponse 		response 	= httpClient.execute(pagePost);

            //Respuesta del servidor
            clienteModelosBeanArray.add(new ClienteModelosBean());
            clienteModelosBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());


            if(clienteModelosBeanArray.get(0).getConnStatus() == 200){
                DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
                DocumentBuilder			builder 	= factory.newDocumentBuilder();
                Document 				doc 		= builder.parse(response.getEntity().getContent());

                NodeList 		node 		= doc.getElementsByTagName("d");
                Node			elem		= node.item(0);
                Node 			child;
                NamedNodeMap	nodeMap		= null;
                int				i			= 0;

                if( elem != null){
                    if (elem.hasChildNodes()) {
                        for( child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                            if(i > 0)
                                clienteModelosBeanArray.add(new ClienteModelosBean());

                            nodeMap = child.getAttributes();

                            clienteModelosBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
                            clienteModelosBeanArray.get(i).setIdCliente(nodeMap.getNamedItem("B").getNodeValue());
                            clienteModelosBeanArray.get(i).setIdModelo(nodeMap.getNamedItem("C").getNodeValue());
                            i++;
                        }
                    }
                }
            }
        }
        catch (Exception e){
            clienteModelosBeanArray.get(0).setConnStatus(100);
            e.printStackTrace();
        }

        return clienteModelosBeanArray;
    }

	public static ArrayList<CodigosIntervencion2Bean> getCodigosIntervencion2()
    {   // Adquiere los Códigos de Intervención Nivel 2
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCodigosIntervencionNivel2.asp";
		ArrayList<CodigosIntervencion2Bean> codigosIntervencion2BeanArray = new ArrayList<CodigosIntervencion2Bean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			codigosIntervencion2BeanArray.add(new CodigosIntervencion2Bean());
			codigosIntervencion2BeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());


			if(codigosIntervencion2BeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                            if (i > 0)
                                codigosIntervencion2BeanArray.add(new CodigosIntervencion2Bean());

                            nodeMap = child.getAttributes();

							codigosIntervencion2BeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							codigosIntervencion2BeanArray.get(i).setClaveCodigo(nodeMap.getNamedItem("B").getNodeValue());
							codigosIntervencion2BeanArray.get(i).setIdCliente(nodeMap.getNamedItem("C").getNodeValue());
							codigosIntervencion2BeanArray.get(i).setIdParent1(nodeMap.getNamedItem("D").getNodeValue());
							codigosIntervencion2BeanArray.get(i).setDescCodigo(child.getTextContent());
							i++;
						}
					}
				}
			}
		}
		catch (Exception e){
			codigosIntervencion2BeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return codigosIntervencion2BeanArray;
	}

	public static ArrayList<ServiciosSolucionesBean> getServiciosSoluciones()
    {   // Adquiere la lista de Servicios Soluciones
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getServiciosSoluciones.asp";
		ArrayList<ServiciosSolucionesBean> serviciosSolucionesBeanArray = new ArrayList<ServiciosSolucionesBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			serviciosSolucionesBeanArray.add(new ServiciosSolucionesBean());
			serviciosSolucionesBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(serviciosSolucionesBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
                Node child;
                NamedNodeMap nodeMap = null;
				int				i			= 0;

				if( elem != null){
                    if (elem.hasChildNodes()) {
                        for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                            if (i > 0)
                                serviciosSolucionesBeanArray.add(new ServiciosSolucionesBean());

							nodeMap = child.getAttributes();

							serviciosSolucionesBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							serviciosSolucionesBeanArray.get(i).setIdServicio(nodeMap.getNamedItem("B").getNodeValue());
							serviciosSolucionesBeanArray.get(i).setIdSolucion(nodeMap.getNamedItem("C").getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			serviciosSolucionesBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return serviciosSolucionesBeanArray;
	}

	public static ArrayList<TipoFallaBean> getTipoFalla()
    {   // Adquiere la lista de Tipo Fallas
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getTipoFalla.asp";
		ArrayList<TipoFallaBean> tipoFallaBeanArray = new ArrayList<TipoFallaBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			tipoFallaBeanArray.add(new TipoFallaBean());
			tipoFallaBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			
			if(tipoFallaBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int i = 0;

                if (elem != null) {
                    if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
                                tipoFallaBeanArray.add(new TipoFallaBean());

							nodeMap = child.getAttributes();

							tipoFallaBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							tipoFallaBeanArray.get(i).setIdServicio(nodeMap.getNamedItem("B").getNodeValue());
							tipoFallaBeanArray.get(i).setIdCliente(nodeMap.getNamedItem("C").getNodeValue());
							tipoFallaBeanArray.get(i).setDescTipoFalla(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			tipoFallaBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return tipoFallaBeanArray;
	}

	public static ArrayList<EspecificacionTipoFallaBean> getEspecificaTipoFalla()
    {   // Adquiere la lista de Especificacion Tipo Fallas
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getEspecificacionTipoFalla.asp";
		ArrayList<EspecificacionTipoFallaBean> espTipoFallaBeanArray = new ArrayList<EspecificacionTipoFallaBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			espTipoFallaBeanArray.add(new EspecificacionTipoFallaBean());
			espTipoFallaBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			
			if(espTipoFallaBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null) {
                    if (elem.hasChildNodes()) {
                        for( child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
							if(i > 0)
								espTipoFallaBeanArray.add(new EspecificacionTipoFallaBean());	

							nodeMap = child.getAttributes();

							espTipoFallaBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							espTipoFallaBeanArray.get(i).setIdFallaParent(nodeMap.getNamedItem("B").getNodeValue());
							espTipoFallaBeanArray.get(i).setDescEspecificacionFalla(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			espTipoFallaBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return espTipoFallaBeanArray;
	}

	public static ArrayList<ServiciosCausasBean> getServiciosCausas()
    {   // Adquiere la lista de Servicios Causas
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getServiciosCausas.asp";
		ArrayList<ServiciosCausasBean> serviciosCausasBeanArray = new ArrayList<ServiciosCausasBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			serviciosCausasBeanArray.add(new ServiciosCausasBean());
			serviciosCausasBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(serviciosCausasBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				if( elem != null) {
                    if (elem.hasChildNodes()) {
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                            if(i > 0)
								serviciosCausasBeanArray.add(new ServiciosCausasBean());	

							nodeMap = child.getAttributes();

							serviciosCausasBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							serviciosCausasBeanArray.get(i).setIdServicio(nodeMap.getNamedItem("B").getNodeValue());
							serviciosCausasBeanArray.get(i).setIdCausa(nodeMap.getNamedItem("C").getNodeValue());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			serviciosCausasBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return serviciosCausasBeanArray;
	}

	public static ArrayList<IngenierosBean> getIngenieros()
    {   // Adquiere los Ingenieros
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getIngenierosCampo.asp";
		ArrayList<IngenierosBean> ingenierosBeanArray = new ArrayList<IngenierosBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			ingenierosBeanArray.add(new IngenierosBean());
			ingenierosBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(ingenierosBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node elem = node.item(0);
                Node child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

                if (elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								ingenierosBeanArray.add(new IngenierosBean());	

							nodeMap = child.getAttributes();

							ingenierosBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
							ingenierosBeanArray.get(i).setNombreCompleto(child.getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			ingenierosBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return ingenierosBeanArray;
	}

	public static ArrayList<SKUBean> getSKU(String idAR)
    {   // Adquiere los SKU
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/completeSku.asp";
		ArrayList<SKUBean> skuBeanArray = new ArrayList<SKUBean>();
		skuBeanArray.add(new SKUBean());	
		
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + idAR;
			final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			skuBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(skuBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				NamedNodeMap	nodeMap		= node.item(0).getAttributes();
				
				Node			elem		= node.item(0);
				Node 			child;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								skuBeanArray.add(new SKUBean());	
							
							nodeMap = child.getAttributes();
							
							skuBeanArray.get(i).setIdModelo(nodeMap.getNamedItem("A").getNodeValue());
							skuBeanArray.get(i).setIdMarca(nodeMap.getNamedItem("B").getNodeValue());
							
							NodeList childList = child.getChildNodes();
							
							skuBeanArray.get(i).setSku(childList.item(0).getTextContent());
							skuBeanArray.get(i).setDescMarca(childList.item(1).getTextContent());
							skuBeanArray.get(i).setDescripcion(childList.item(2).getTextContent());
							skuBeanArray.get(i).setDescModelo(childList.item(3).getTextContent());
							
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			skuBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return skuBeanArray;
	}
	
	//TODO valida el cierre 
	public static ValidateClosureBean validateClosure(String id, int type)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/validarCierre.asp";
		ValidateClosureBean vcb = new ValidateClosureBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + id +"&t=" + type;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			vcb.setConnStatus(response.getStatusLine().getStatusCode());

			if(vcb.getConnStatus()  == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				
//				writeResults("validateClosure", fullHTTP + " ", doc);
				
				NodeList 	 nodeD	= doc.getElementsByTagName("r");
				Node node = null;
				NamedNodeMap rr = null;
				
				if( nodeD != null ){
					node = nodeD.item(0);
					if(node != null){
						rr = node.getAttributes();
					}
				} else {
				}

				NodeList 	 nodeW	= doc.getElementsByTagName("datae");
				Node 		 nodew 	= null;
				NamedNodeMap r		= null;
				
				if( nodeW != null ){
					if(nodeW.getLength() != 0){
						nodew = nodeW.item(0);
						if(nodew != null){
							r = nodew.getAttributes();
						}
					}
				}
				vcb.setRes(rr.getNamedItem("res").getNodeValue());
				vcb.setVal(rr.getNamedItem("val").getNodeValue());
				vcb.setDesc(rr.getNamedItem("desc").getNodeValue());

				//checamos los casos patol—gicos:
				if(!vcb.getVal().equals("SI")){
					//?
					
				} else if(vcb.getVal().equals("SI")) {
					vcb.setIdAR(r.getNamedItem("A").getNodeValue());
					vcb.setIdServicio(r.getNamedItem("B").getNodeValue());
					vcb.setIdCliente(r.getNamedItem("C").getNodeValue());
					vcb.setIsWincor(r.getNamedItem("D").getNodeValue());
					vcb.setIdCausa(r.getNamedItem("E").getNodeValue());
					vcb.setIdSolucion(r.getNamedItem("F").getNodeValue());
					vcb.setNoEquipo(r.getNamedItem("G").getNodeValue());
					vcb.setDescripcionTrabajo(r.getNamedItem("H").getNodeValue());
					vcb.setAtiende(r.getNamedItem("I").getNodeValue());
					vcb.setFolioTas(r.getNamedItem("J").getNodeValue());
					vcb.setCodigoIntervencion(r.getNamedItem("K").getNodeValue());
					vcb.setNoSerieFalla(r.getNamedItem("L").getNodeValue());
					vcb.setNoInventarioFalla(r.getNamedItem("M").getNodeValue());
					vcb.setIdModeloFalla(r.getNamedItem("N").getNodeValue());
					vcb.setIsActualizacion(r.getNamedItem("O").getNodeValue());
					vcb.setIsInstalacion(r.getNamedItem("P").getNodeValue());
					vcb.setIsSustitucion(r.getNamedItem("Q").getNodeValue());
					vcb.setIsRetiro(r.getNamedItem("R").getNodeValue());
					vcb.setFecInicio(setDateFormatExito(r.getNamedItem("S").getNodeValue()));
					vcb.setFecActual(setDateFormatExito(r.getNamedItem("T").getNodeValue()));
					vcb.setIdProyecto(r.getNamedItem("U").getNodeValue());
					vcb.setIdModeloReq(r.getNamedItem("V").getNodeValue());
					vcb.setIdProducto(r.getNamedItem("W").getNodeValue());
					vcb.setIdTipoServicio(r.getNamedItem("X").getNodeValue());
					vcb.setIdTipoPrecio(r.getNamedItem("Y").getNodeValue());
					vcb.setIdMoneda(r.getNamedItem("Z").getNodeValue());
					
					vcb.setIdTipoCobro(r.getNamedItem("AA").getNodeValue());
					vcb.setIsCobrable(r.getNamedItem("AB").getNodeValue());
					vcb.setDescMoneda(r.getNamedItem("AC").getNodeValue());
					vcb.setHorasAtencion(r.getNamedItem("AD").getNodeValue());
					vcb.setPrecioExito(r.getNamedItem("AE").getNodeValue());
					vcb.setPrecioRechazo(r.getNamedItem("AF").getNodeValue());
					vcb.setDescTipoCobro(r.getNamedItem("AG").getNodeValue());
					vcb.setDescTipoPrecio(r.getNamedItem("AH").getNodeValue());
					vcb.setFecLlegada(setDateFormatExito(r.getNamedItem("AI").getNodeValue()));
					vcb.setFecLlegadaTerceros(setDateFormatExito(r.getNamedItem("AJ").getNodeValue()));
					vcb.setFolioServicio(r.getNamedItem("AK").getNodeValue());
					vcb.setFecIniIngeniero(setDateFormatExito(r.getNamedItem("AL").getNodeValue()));
					vcb.setFecFinIngeniero(setDateFormatExito(r.getNamedItem("AM").getNodeValue()));
					vcb.setOtorganteVoBo(r.getNamedItem("AN").getNodeValue());
					vcb.setOtorganteVoBoTerceros(r.getNamedItem("AO").getNodeValue());
					vcb.setIntensidadSenial(r.getNamedItem("AP").getNodeValue());
					vcb.setIdSIMRemplazada(r.getNamedItem("AQ").getNodeValue());
					vcb.setDigitoVerificador(r.getNamedItem("AR").getNodeValue());
					vcb.setIdTipoFallaEncontrada(r.getNamedItem("AS").getNodeValue());
					vcb.setFallaEncontrada(r.getNamedItem("AT").getNodeValue());
					vcb.setOtorganteVoBoCliente(r.getNamedItem("AU").getNodeValue());
					vcb.setMotivoCobro(r.getNamedItem("AV").getNodeValue());
					vcb.setIsSoporteCliente(r.getNamedItem("AW").getNodeValue());
					vcb.setIsboletin(r.getNamedItem("AX").getNodeValue());
					vcb.setOtorganteSoporteCliente(r.getNamedItem("AY").getNodeValue());
					vcb.setCadenaCierreEscrita(r.getNamedItem("AZ").getNodeValue());
					vcb.setIsUptime(r.getNamedItem("BA").getNodeValue());
					vcb.setMinsDowntime(r.getNamedItem("BB").getNodeValue());
					vcb.setDescHorarioUptime(r.getNamedItem("BC").getNodeValue());
					vcb.setDescHorarioAcceso(r.getNamedItem("BD").getNodeValue());
					vcb.setIsContract(r.getNamedItem("BE").getNodeValue());
					vcb.setDocTIR(r.getNamedItem("BF").getNodeValue());
					vcb.setInstalacionLlaves(r.getNamedItem("BG").getNodeValue());
					vcb.setTipoLlaves(r.getNamedItem("BH").getNodeValue());
					vcb.setStatusInstalacionLlaves(r.getNamedItem("BI").getNodeValue());
					vcb.setNombrePersonalLlavesA(r.getNamedItem("BJ").getNodeValue());
					vcb.setNombrePersonalLlavesB(r.getNamedItem("BK").getNodeValue());
					vcb.setTipoPw(r.getNamedItem("BL").getNodeValue());
					vcb.setTisTipoTeclado(r.getNamedItem("BM").getNodeValue());
					vcb.setVersionTecladoEPP(r.getNamedItem("BN").getNodeValue());
					vcb.setProcesador(r.getNamedItem("BO").getNodeValue());
					vcb.setVelocidadProcesador(r.getNamedItem("BP").getNodeValue());
					vcb.setMemoria(r.getNamedItem("BQ").getNodeValue());
					vcb.setCapacidadDiscoDuro(r.getNamedItem("BR").getNodeValue());
					vcb.setMonitor(r.getNamedItem("BS").getNodeValue());
					vcb.setLectorTarjeta(r.getNamedItem("BT").getNodeValue());
					vcb.setAplicacion_santander(r.getNamedItem("BU").getNodeValue());
					vcb.setVersion_santander(r.getNamedItem("BV").getNodeValue());
					vcb.setCaja(r.getNamedItem("BW").getNodeValue());
					vcb.setVoltajeNeutro(r.getNamedItem("BX").getNodeValue());
					vcb.setVoltajeTierra(r.getNamedItem("BY").getNodeValue());
					vcb.setVoltajeTierraNeutro(r.getNamedItem("BZ").getNodeValue());
					vcb.setIdEspecifiqueTipoFallo(r.getNamedItem("CA").getNodeValue());
					vcb.setFolioValidacion(r.getNamedItem("CB").getNodeValue());
					vcb.setFolioTIR(r.getNamedItem("CC").getNodeValue());

					vcb.setIsCausaSolucionRequired(r.getNamedItem("CD").getNodeValue());
					vcb.setIsTASRequired(r.getNamedItem("CE").getNodeValue());
					vcb.setIsOtorganteTASRequired(r.getNamedItem("CF").getNodeValue());
					vcb.setIsNoEquipoREquired(r.getNamedItem("CG").getNodeValue());
					vcb.setIsNoSerieRequired(r.getNamedItem("CH").getNodeValue());
					vcb.setIsNoInventarioRequired(r.getNamedItem("CI").getNodeValue());
					vcb.setIsIdModeloRequired(r.getNamedItem("CJ").getNodeValue());
					vcb.setIsFecLlegadaRequired(r.getNamedItem("CK").getNodeValue());
					vcb.setIsFecLlegadaTercerosRequired(r.getNamedItem("CL").getNodeValue());
					vcb.setIsFolioServicioRequired(r.getNamedItem("CM").getNodeValue());
					vcb.setIsFecIniIngenieroRequired(r.getNamedItem("CN").getNodeValue());
					vcb.setIsFecFinIngenieroRequired(r.getNamedItem("CO").getNodeValue());
					vcb.setIsOtorganteVoBoRequired(r.getNamedItem("CP").getNodeValue());
					vcb.setIsOtorganteVoBoTercerosRequired(r.getNamedItem("CQ").getNodeValue());
					vcb.setIsIntensidadSenialRequired(r.getNamedItem("CR").getNodeValue());
					vcb.setIsIsSIMRemplazadaRequired(r.getNamedItem("CS").getNodeValue());
					vcb.setIsFallaEncontradaRequired(r.getNamedItem("CT").getNodeValue());

                    vcb.setIsFallaEncontradaRequiredText(r.getNamedItem("CTxt").getNodeValue());

					vcb.setIsOtorganteVoBoClienteRequired(r.getNamedItem("CU").getNodeValue());
					vcb.setIsMotivoCobroRequired(r.getNamedItem("CV").getNodeValue());
					vcb.setIsIsSoporteClienteRequired(r.getNamedItem("CW").getNodeValue());
					vcb.setIsIsBoletinRequired(r.getNamedItem("CX").getNodeValue());
					vcb.setIsOtorganteSoporteClienteRequired(r.getNamedItem("CY").getNodeValue());
					vcb.setIsCadenaCierreEscritaRequired(r.getNamedItem("CZ").getNodeValue());
					vcb.setIsCodigoIntervencionRequired(r.getNamedItem("DA").getNodeValue());
					vcb.setLengthCodigoIntervencion(r.getNamedItem("DB").getNodeValue());
					vcb.setIsDigitoVerificadorRequired(r.getNamedItem("DC").getNodeValue());
					vcb.setIsIdTipoFallaEncontradaRequired(r.getNamedItem("DD").getNodeValue());
					vcb.setIsInstalacionLlavesRequired(r.getNamedItem("DE").getNodeValue());
					vcb.setIsTipoLlaveRequired(r.getNamedItem("DF").getNodeValue());
					vcb.setIsStatusInstalacionLlavesRequired(r.getNamedItem("DG").getNodeValue());
					vcb.setIsNombrePersonaLlavesARequired(r.getNamedItem("DH").getNodeValue());
					vcb.setIsNombrePersonaLlavesBRequired(r.getNamedItem("DI").getNodeValue());
					vcb.setIsTipoPwRequired(r.getNamedItem("DJ").getNodeValue());
					vcb.setIsTipoTecladoRequired(r.getNamedItem("DK").getNodeValue());
					vcb.setIsVersionTecladoEPPRequired(r.getNamedItem("DL").getNodeValue());
					vcb.setIsProcesadorRequired(r.getNamedItem("DM").getNodeValue());
					vcb.setIsVelocidadProcesadorRequired(r.getNamedItem("DN").getNodeValue());
					vcb.setIsMemoriaRequired(r.getNamedItem("DO").getNodeValue());
					vcb.setIsCapacidadDiscoDuroRequired(r.getNamedItem("DP").getNodeValue());
					vcb.setIsMonitorRequired(r.getNamedItem("DQ").getNodeValue());
					vcb.setIsLectorTarjetaRequired(r.getNamedItem("DR").getNodeValue());
					vcb.setIsAplicacionRequired(r.getNamedItem("DS").getNodeValue());
					vcb.setIsVersionRequired(r.getNamedItem("DT").getNodeValue());
					vcb.setIsCajaRequired(r.getNamedItem("DU").getNodeValue());
					
					vcb.setIsMultipleTask(r.getNamedItem("DV").getNodeValue());
					vcb.setIsLecturaVoltajeRequired(r.getNamedItem("DW").getNodeValue());
					vcb.setIsEspecificaTipoFalla(r.getNamedItem("DX").getNodeValue());
					vcb.setIsFolioValidacionRequired(r.getNamedItem("DY").getNodeValue());
					vcb.setIsFolioTIRRequired(r.getNamedItem("DZ").getNodeValue());
					vcb.setIsDescTrabajoRequired(r.getNamedItem("EA").getNodeValue());
					vcb.setIsDescTrabajoCatalogRequired(r.getNamedItem("EB").getNodeValue());
					// ADD SIM AND SIM REQUIRED FIELDS
					vcb.setSIM(r.getNamedItem("EC").getNodeValue());
					vcb.setIsSIMRequired(r.getNamedItem("ED").getNodeValue());
					vcb.setIdFail(r.getNamedItem("EE").getNodeValue());

					//SE AGREGAN CAMPOS NUEVOS 31/01/2018
                    vcb.setIsCalidadBilleteRequired(r.getNamedItem("CBR").getNodeValue());
                    vcb.setIsCondicionSiteRequired(r.getNamedItem("CSR").getNodeValue());
				}
			}
		}
		catch(SocketTimeoutException ex){
			vcb.setRes("ERROR");
			vcb.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			vcb.setConnStatus(100);
			e.printStackTrace();
		}

		return vcb;
	}

	public static ValidateRejectClosureBean validateRejectClosure(String id/*, int type*/)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/validarCierreRechazo.asp";

		//RequestDetailBean requestDetailBean = new RequestDetailBean();
		ValidateRejectClosureBean vcb = new ValidateRejectClosureBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + id;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			vcb.setConnStatus(response.getStatusLine().getStatusCode());

			if(vcb.getConnStatus() == 200){
				//aqu’ terminan las coincidencias

				//checamos el status de la validaci—n?

				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList nodeR				= doc.getElementsByTagName("r");
				Node node_r 				= null;
				NamedNodeMap r				= null;

				if (nodeR != null){
					node_r = nodeR.item(0);
					if (node_r != null){
						r = node_r.getAttributes();
					}
				}

				NodeList 	 nodeData	    = doc.getElementsByTagName("datar");
				Node 		 node_data	 	= null;
				NamedNodeMap data			= null;

				if (nodeData != null){
					node_data = nodeData.item(0);
					if (node_data != null){
						data = node_data.getAttributes();
					}
				}

				vcb.setRes(r.getNamedItem("res").getNodeValue());
				vcb.setVal(r.getNamedItem("val").getNodeValue());
				vcb.setDesc(r.getNamedItem("desc").getNodeValue());

				//checamos los casos patol—gicos:
				if (vcb.getVal().trim().equals("SI")){
					vcb.setIdAR(data.getNamedItem("A").getNodeValue());
					vcb.setIdServicio(data.getNamedItem("B").getNodeValue());
					vcb.setIdCliente(data.getNamedItem("C").getNodeValue());
					vcb.setDescMoneda(data.getNamedItem("D").getNodeValue());
					vcb.setDescTipoCobro(data.getNamedItem("E").getNodeValue());
					vcb.setDescTipoPrecio(data.getNamedItem("F").getNodeValue());
					vcb.setDescripcionTrabajo(data.getNamedItem("G").getNodeValue());					
					vcb.setHorasAtencion(data.getNamedItem("H").getNodeValue());
					vcb.setIdTipoPrecio(data.getNamedItem("I").getNodeValue());
					vcb.setIdTipoCobro(data.getNamedItem("J").getNodeValue());
					vcb.setIsCobrable(data.getNamedItem("K").getNodeValue());
					vcb.setPrecioRechazo(data.getNamedItem("L").getNodeValue());
					vcb.setIsCausaSolucionRequired(data.getNamedItem("M").getNodeValue());
					vcb.setIsCausaRequired(data.getNamedItem("N").getNodeValue());
					vcb.setIsSolucionRequired(data.getNamedItem("O").getNodeValue());
					vcb.setIsFolioServicioRechazoRequired(data.getNamedItem("P").getNodeValue());
					vcb.setIsOtorganteVoBoRechazoRequired(data.getNamedItem("Q").getNodeValue());
					vcb.setIsDescripcionTrabajoRechazoRequired(data.getNamedItem("R").getNodeValue());
					vcb.setIsIdTipoFallaEncontradaRequired(data.getNamedItem("S").getNodeValue());
					vcb.setIsEspecificaTipoFalla(data.getNamedItem("T").getNodeValue());
					vcb.setIsIdCausaRechazoRequired(data.getNamedItem("U").getNodeValue());
					vcb.setOtorganteVoBo(data.getNamedItem("V").getNodeValue());
					vcb.setFecInicio(data.getNamedItem("W").getNodeValue());
					vcb.setClaveRechazo(data.getNamedItem("X").getNodeValue());
					vcb.setIsClaveRechazoRequired(data.getNamedItem("Y").getNodeValue());
				}
			}
		}
		catch(SocketTimeoutException ex){
			vcb.setRes("ERROR");
			vcb.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			vcb.setConnStatus(100);
			e.printStackTrace();
		}

		return vcb;
	}

	public static UploadImageBean uploadImage(String path, String newimageName, String numAR,
        String idTecnico)
    {
        // Checar si la imagen es válida
		//Objects for infoFoto
		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);
		
		HttpClient 			httpClient;
		HttpPost 			pagePost;
		HttpResponse 		response;

		//Objects for uploadFile
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		DataInputStream inStream = null;
		String existingFileName = path;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		String reponse_data = "";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;

		String encodedImageName = newimageName;

		try{
			encodedImageName = URLEncoder.encode(encodedImageName, "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

        //(THIS)
        HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
		String URL_UPLOAD;
		if(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE)){
			URL_UPLOAD = "http://smc.microformas.com.mx/BBNET/upload.aspx";
        } else {
            URL_UPLOAD = "http://smc.microformas.com.mx/BBNET/uploadTest.aspx";
        }

        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_INFO	= URL_SERVER + "/infoFoto.asp?ia=" + numAR + "&it=" + idTecnico
                + "&sfn=" + encodedImageName;

		//Obtenemos la Imagen
		//TODO NO ESTÁ EN USO ESTE CODIGO
		
		//File imageFile = new File(path);
		/* Bitmap bm;
		if (imageFile.exists()) {   
			bm = BitmapFactory.decodeFile(path);
		}*/



		UploadImageBean uploadImageBean = new UploadImageBean();
		uploadImageBean.setSuccesfulSend(false);


		try{
			//------------------ CLIENT REQUEST
			FileInputStream fileInputStream = new FileInputStream(new File(existingFileName) );
			// open a URL connection to the Servlet
			URL url = new URL(URL_UPLOAD);
			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();
			// Allow Inputs
			conn.setDoInput(true);
			// Allow Outputs
			conn.setDoOutput(true);
			// Don't use a cached copy.
			conn.setUseCaches(false);
			// Use a post method.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			//conn.setRequestProperty("Connection", "close");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			conn.setConnectTimeout(CONN_TIMER);
			conn.setReadTimeout(CONN_TIMER);
			if(true){
				dos = new DataOutputStream( conn.getOutputStream() );
				dos.writeBytes(twoHyphens + boundary + lineEnd);
	
				dos.writeBytes("Content-Disposition: form-data; name=\"File1\";filename=\"" + newimageName + "\"" + lineEnd); // newimageName is the Name of the File to be uploaded
				dos.writeBytes(lineEnd);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				while (bytesRead > 0){
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				fileInputStream.close();
				dos.flush();
				dos.close();
		}
		}
		catch (Exception ex){
			ex.printStackTrace();
		
		}
		//------------------ read the SERVER RESPONSE
		try {
			inStream = new DataInputStream ( conn.getInputStream() );
			String str;
			while (( str = inStream.readLine()) != null){
				reponse_data=str;
			}
			inStream.close();
		}
		catch (IOException ioex){
			ioex.printStackTrace();
		}

		if(reponse_data.indexOf("Foto enviada exitosamente") != -1){
			uploadImageBean.setSuccesfulSend(true);
		}

		//To update database
		if(uploadImageBean.isSuccesfulSend()){
			try {

				//Make http request
				httpClient 	= new DefaultHttpClient(httpParams);
				pagePost 	= new HttpPost(URL_INFO);
				pagePost.setHeader("Accept-Charset","utf-8");
				response 	= httpClient.execute(pagePost);


				//Chek for server response
				//Respuesta del servidor
				uploadImageBean.setConnStatus(response.getStatusLine().getStatusCode());

				if(uploadImageBean.getConnStatus() == 200){
					DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
					DocumentBuilder			builder 	= factory.newDocumentBuilder();
					Document 				doc 		= builder.parse(response.getEntity().getContent());

					NodeList 	 n		= doc.getElementsByTagName("d");
					Node 		 node 	= n.item(0).getFirstChild();
					NamedNodeMap r		= node.getAttributes();

					//Revisa si la info es correcta
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Los datos son correctos, regresando id y estatus de activo.
						uploadImageBean.setSuccelfulInfo(true);

					}
					else if(r.getNamedItem("res").getNodeValue().equals("ERROR")){
						//Los datos son incorrectos, regresando mensaje de error
						uploadImageBean.setDesc(r.getNamedItem("desc").getNodeValue());
						uploadImageBean.setSuccelfulInfo(false);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return uploadImageBean;
	}

	public static String InsertarInstalacion(String idAR, String idTecnico, String idUnidad, String idNegocio, String edit,
        String accion, String noEquipo)
    {
		String ret = "No hay conexion a Internet, intenta mas tarde";
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SEND_COMMENT	= URL_SERVER + "/insertarInstalacion.asp";

		String ia 	= "";
		String e 	= "";
		String in 	= "";
		String it	= "";
		String iu	= "";
		String a	= "";
		String noe	= "";


		try{
			ia 	= URLEncoder.encode(idAR, "UTF-8");
			e 	= URLEncoder.encode(edit, "UTF-8");
			in 	= URLEncoder.encode(idNegocio, "UTF-8");
			it	= URLEncoder.encode(idTecnico, "UTF-8");
			iu	= URLEncoder.encode(idUnidad, "UTF-8");
			a	= URLEncoder.encode(accion, "UTF-8");
			noe	= URLEncoder.encode(noEquipo, "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String				fullHTTP	= URL_SEND_COMMENT + "?ia=" + ia + "&e=" + e + "&in=" + in
					+"&it=" + it + "&iu=" + iu
					+"&a=" + a + "&noe=" + noe;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					//Revisa si el usuario es correcto
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Se realizó el procedure, regresando true
						ret = "OK";
					}
					else{
						ret = "Error de parametros";
					}
				}
				else{
					ret = "Error de parametros";
				}
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return ret;
	}

	public static String InsertarInsumo(String idAR, String idTecnico,String idInsumo, String cantidad,
        String accion, String idARInsumo)
    {
		String ret = "No hay conexion a Internet, intenta mas tarde";
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SEND_COMMENT	= URL_SERVER + "/insertarInsumo.asp";

		String ia 	= "";
		String it 	= "";
		String in 	= "";
		String c	= "";
		String iai	= "";
		String a	= "";


		try{
			ia 	= URLEncoder.encode(idAR, "UTF-8");
			it 	= URLEncoder.encode(idTecnico, "UTF-8");
			in 	= URLEncoder.encode(idInsumo, "UTF-8");
			c	= URLEncoder.encode(cantidad, "UTF-8");
			iai	= URLEncoder.encode(idARInsumo, "UTF-8");
			a	= URLEncoder.encode(accion, "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String				fullHTTP	= URL_SEND_COMMENT + "?ia=" + ia + "&it=" + it + "&in=" + in
					+"&c=" + c + "&iai=" + iai
                    +"&a=" + a ;
            Log.e("URL",fullHTTP);
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					//Revisa si el usuario es correcto
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Se realizó el procedure, regresando true
						ret = "OK";
					}
					else{
					    if(r.getNamedItem("res").getNodeValue().equals("ERROR1")){
                            ret = "Error:" + r.getNamedItem("desc").getNodeValue();
                        }else{
                            ret = "Error en la BD :" + r.getNamedItem("desc").getNodeValue();
                        }
					}
				}
				else{
					ret = "Error de parametros";
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return ret;
	}

	public static InfoSustitucionBean obtainInfoSustitucion(String ia, int id)
    {   // Parser de sustitucion
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/infoSustitucion.asp";

		//RequestDetailBean requestDetailBean = new RequestDetailBean();
		InfoSustitucionBean isb = new InfoSustitucionBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + ia + "&it=" + id;
			
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
            HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			isb.setConnStatus(response.getStatusLine().getStatusCode());

			if(isb.getConnStatus() == 200){
				//aqu’ terminan las coincidencias

				//checamos el status de la validaci—n?

				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 	 nodeR	   		= doc.getElementsByTagName("r");
				Node 		 node_r 		= null;
				NamedNodeMap r 				= null;
				if (nodeR != null){
					node_r 		= nodeR.item(0);
					if (node_r !=null){
						r				= node_r.getAttributes();
					}
				}

				NodeList 	 nodeData	    = doc.getElementsByTagName("data");
				Node 		 node_data	 	= null;
				NamedNodeMap data			= null;

				if (nodeData != null){
					node_data = nodeData.item(0);
					if (node_data !=null){
						data = node_data.getAttributes();
					}
				}

				NodeList 	 nodeOTROR	    = doc.getElementsByTagName("otror");
				Node 		 node_otror 	= null;
				NamedNodeMap otror			= null;

				if (nodeOTROR != null){
					node_otror = nodeOTROR.item(0);
					if (node_otror != null){
						otror = node_otror.getAttributes();
					}
				}

				NodeList 	 nodeSUS	    = doc.getElementsByTagName("sus");
				Node 		 node_sus 		= null;
				NamedNodeMap sus			= null;


				//requestDetailBean.setIdNegocio(nodeMap.getNamedItem("ID_NEGOCIO").getNodeValue());

				isb.setRes(r.getNamedItem("res").getNodeValue());
				isb.setVal(r.getNamedItem("val").getNodeValue());
				isb.setDesc(r.getNamedItem("desc").getNodeValue());

				//asignamos el valor primario del status
				statusSust = Integer.parseInt(isb.getVal());

				//asignamos el status en funcion de otror
				if (otror != null){
					if(otror.getNamedItem("val").getNodeValue().equals("3")){
						if (statusSust == 1){
							statusSust = 3;
						} else if (statusSust == 2){
							statusSust = 4;
						}
					}
				}
				isb.setStatusSust(statusSust);

				if (data != null){
					//rellenamos el campo de datos.
					isb.setData_idNegocio(data.getNamedItem("A").getNodeValue());
					isb.setData_idCliente(data.getNamedItem("B").getNodeValue());
					isb.setData_isIdRequired(data.getNamedItem("C").getNodeValue());
					isb.setData_isBom(data.getNamedItem("D").getNodeValue());
					isb.setData_buttonDisplay(data.getNamedItem("E").getNodeValue());
					isb.setData_cancelButtonDisplay(data.getNamedItem("F").getNodeValue());
					isb.setData_edit(data.getNamedItem("G").getNodeValue());
					isb.setData_idTipoProducto(data.getNamedItem("H").getNodeValue());
				}
				ArrayList<InfoSustitucionBean.Unidad> unidadesNegocio = new ArrayList<InfoSustitucionBean.Unidad>();

				NodeList 	 nodeUN	 	    = doc.getElementsByTagName("un");
				NamedNodeMap un				= null;

				if (nodeUN != null){
					for (int i=0;i<nodeUN.getLength();i++){
						Node node_un = nodeUN.item(i);
						un = node_un.getAttributes();
						//rellenamos uNegocio
						InfoSustitucionBean.Unidad unidad = new InfoSustitucionBean.Unidad();
						unidad.setIdUnidad(un.getNamedItem("I").getNodeValue());
						unidad.setIdModelo(un.getNamedItem("J").getNodeValue());
						unidad.setIdProducto(un.getNamedItem("K").getNodeValue());
						unidad.setNoUnidades(un.getNamedItem("L").getNodeValue());
						unidad.setDescCliente(un.getNamedItem("M").getNodeValue());
						unidad.setIsDisabled(un.getNamedItem("N").getNodeValue());
						unidad.setDescModelo(un.getNamedItem("O").getNodeValue());
						unidad.setDescMarca(un.getNamedItem("P").getNodeValue());
						unidad.setNoSerie(un.getNamedItem("Q").getNodeValue());
						unidad.setNoInventario(un.getNamedItem("R").getNodeValue());
						unidad.setNoIMEI(un.getNamedItem("S").getNodeValue());
						unidad.setNoEquipo(un.getNamedItem("T").getNodeValue());
						unidad.setPosicionInventario(un.getNamedItem("U").getNodeValue());
						unidad.setDescStatusUnidad(un.getNamedItem("V").getNodeValue());
						unidad.setIdStatusUnidad(un.getNamedItem("W").getNodeValue());
						unidad.setDescNegocio(un.getNamedItem("X").getNodeValue());
						unidadesNegocio.add(unidad);
					}
				}
				isb.setUnidadesNegocio(unidadesNegocio);

				ArrayList<InfoSustitucionBean.Unidad> unidadesTecnico = new ArrayList<InfoSustitucionBean.Unidad>();

				NodeList 	 nodeUT	  	    = doc.getElementsByTagName("ut");
				Node 		 node_UT 		= null;
				NamedNodeMap ut				= null;

				if (nodeUT != null){
					//uTecnico
					for (int i=0;i<nodeUT.getLength();i++){
						node_UT = nodeUT.item(i);
						ut = node_UT.getAttributes();
						InfoSustitucionBean.Unidad unidad = new InfoSustitucionBean.Unidad();
						unidad.setIdUnidad(ut.getNamedItem("I").getNodeValue());
						unidad.setIdModelo(ut.getNamedItem("J").getNodeValue());
						unidad.setIdProducto(ut.getNamedItem("K").getNodeValue());
						unidad.setNoUnidades(ut.getNamedItem("L").getNodeValue());
						unidad.setDescCliente(ut.getNamedItem("M").getNodeValue());
						unidad.setIsDisabled(ut.getNamedItem("N").getNodeValue());
						unidad.setDescModelo(ut.getNamedItem("O").getNodeValue());
						unidad.setDescMarca(ut.getNamedItem("P").getNodeValue());
						unidad.setNoSerie(ut.getNamedItem("Q").getNodeValue());
						unidad.setNoInventario(ut.getNamedItem("R").getNodeValue());
						unidad.setNoIMEI(ut.getNamedItem("S").getNodeValue());
						unidad.setIdStatusUnidad(ut.getNamedItem("W").getNodeValue());
						unidad.setPosicionInventario(ut.getNamedItem("U").getNodeValue());
						unidad.setDescStatusUnidad(ut.getNamedItem("V").getNodeValue());
						unidadesTecnico.add(unidad);
					}
				}
				isb.setUnidadesTecnico(unidadesTecnico);


				//sustitucion
				
				ArrayList<InfoSustitucionBean.Sustitucion> sustituciones = new ArrayList<InfoSustitucionBean.Sustitucion>();
				
				if (nodeSUS != null){
					for (int i=0;i<nodeSUS.getLength();i++){
						node_sus = nodeSUS.item(i);
						sus = node_sus.getAttributes();
						InfoSustitucionBean.Sustitucion sustitucion = new InfoSustitucionBean.Sustitucion();
						sustitucion.setS_idAR(sus.getNamedItem("AU").getNodeValue());
						sustitucion.setS_idNegocio(sus.getNamedItem("AV").getNodeValue());
						sustitucion.setS_idTecnico(sus.getNamedItem("AW").getNodeValue());
						sustitucion.setS_idUnidadEntrada(sus.getNamedItem("AX").getNodeValue());
						sustitucion.setS_idUnidadSalida(sus.getNamedItem("AY").getNodeValue());
						sustitucion.setS_descTecnico(sus.getNamedItem("AZ").getNodeValue());
						sustitucion.setS_descNegocio(sus.getNamedItem("BA").getNodeValue());
						sustitucion.setS_noSerieEntrada(sus.getNamedItem("BB").getNodeValue());
						sustitucion.setS_noSerieSalida(sus.getNamedItem("BC").getNodeValue());
						sustitucion.setS_descCliente(sus.getNamedItem("BD").getNodeValue());
						sustituciones.add(sustitucion);
					}
				}
				
				isb.setSustituciones(sustituciones);
				
			}
		}
		catch(SocketTimeoutException ex){
			isb.setRes("ERROR");
			isb.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			isb.setConnStatus(100);
			e.printStackTrace();
		}

		return isb;
	}

	public static String InsertarSustitucion(String idAR, String idTecnico, String idUnidadEntrada, String idUnidadSalida,
			String idNegocio, String edit, String accion, String noEquipo, String isDaniada)
    {
		String ret = "No hay conexion a Internet, intenta mas tarde";
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String URL_SEND_COMMENT	= URL_SERVER + "/insertarSustitucion.asp";

		String ia 	= "";
		String e 	= "";
		String in 	= "";
		String it	= "";
		String iue	= "";
		String ius	= "";
		String a	= "";
		String isd	= "";
		String noe	= "";

		try{
			ia 	= URLEncoder.encode(idAR, "UTF-8");
			e 	= URLEncoder.encode(edit, "UTF-8");
			in 	= URLEncoder.encode(idNegocio, "UTF-8");
			it	= URLEncoder.encode(idTecnico, "UTF-8");
			iue	= URLEncoder.encode(idUnidadEntrada, "UTF-8");
			ius = URLEncoder.encode(idUnidadSalida, "UTF-8");
			a	= URLEncoder.encode(accion, "UTF-8");
			isd = URLEncoder.encode(isDaniada, "UTF-8");
			noe = URLEncoder.encode(noEquipo, "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String	fullHTTP	= URL_SEND_COMMENT 	+ "?ia=" + ia + "&e=" + e + "&in=" + in
													+"&it=" + it + "&iue=" + iue + "&ius=" + ius 
													+ "&a=" + a +"&isd=" + isd + "&noe=" + noe;
			
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				//QUITAR WRITE
//				writeResults("insertarSustitucion", fullHTTP, doc);
				
				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					//Revisa si el usuario es correcto
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Se realizó el procedure, regresando true
						ret = "OK";
					}
					else{
						ret = "Error en la BD :" + r.getNamedItem("desc").getNodeValue();
					}
				}
				else{
					ret = "Error de parametros";
				}
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return ret;
	}

	public static String InsertarRetiro(String idAR,String idTecnico, String idUnidad, String idNegocio, 
        String edit, String accion, String noEquipo, String isDaniada)
    {
		String ret = "No hay conexion a Internet, intenta mas tarde";
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SEND_COMMENT	= URL_SERVER + "/insertarRetiro.asp";

		String ia 	= "";
		String e 	= "";
		String in 	= "";
		String it	= "";
		String iu	= "";
		String a	= "";
		String isd	= "";
        String cr   = "";


		try{
			ia 	= URLEncoder.encode(idAR, "UTF-8");
			e 	= URLEncoder.encode(edit, "UTF-8");
			in 	= URLEncoder.encode(idNegocio, "UTF-8");
			it	= URLEncoder.encode(idTecnico, "UTF-8");
			iu	= URLEncoder.encode(idUnidad, "UTF-8");
			a	= URLEncoder.encode(accion, "UTF-8");
			isd = URLEncoder.encode(isDaniada, "UTF-8");
            //cr  = URLEncoder.encode(CausaRetiro,"UTF-8");

        } catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String				fullHTTP	= URL_SEND_COMMENT + "?ia=" + ia + "&e=" + e + "&in=" + in
					+"&it=" + it + "&iu=" + iu
                    + "&a=" + a +"&isd=" + isd;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					//Revisa si el usuario es correcto
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Se realizó el procedure, regresando true
						ret = "OK";
					}
					else{
						ret = "Error en la BD :" + r.getNamedItem("desc").getNodeValue();
					}
				}
				else{
					ret = "Error de parametros";
				}
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return ret;
	}

	public static InfoInstalacionBean obtainInfoInstalacion(String ia) {   // Parser de InfoInstalacion.asp
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String URL_UPDATE	= URL_SERVER + "/infoInstalacion.asp";
		InfoInstalacionBean iib = new InfoInstalacionBean();

        //Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + ia;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			iib.setConnStatus(response.getStatusLine().getStatusCode());

			if(iib.getConnStatus() == 200){
				//aqu’ terminan las coincidencias

				//checamos el status de la validaci—n?

				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
                // end
                NodeList nodeR	   		= doc.getElementsByTagName("r");
                Node node_r = nodeR.item(0);
                NamedNodeMap r = node_r.getAttributes();

				NodeList nodeDATA = doc.getElementsByTagName("data");
				Node 		 node_data 		= nodeDATA.item(0);
				NamedNodeMap data			= node_data.getAttributes();

				iib.setRes(r.getNamedItem("res").getNodeValue());
				iib.setDesc(r.getNamedItem("desc").getNodeValue());

				iib.setIdNegocio(data.getNamedItem("A").getNodeValue());
                iib.setIdCliente(data.getNamedItem("B").getNodeValue());
				iib.setIdIsReq(data.getNamedItem("C").getNodeValue());
				iib.setIdIsBom(data.getNamedItem("D").getNodeValue());
				iib.setButtonDisplay(data.getNamedItem("E").getNodeValue());
				iib.setCancelButtonDisplay(data.getNamedItem("F").getNodeValue());
				iib.setEdit(data.getNamedItem("G").getNodeValue());
				iib.setIdTipoProducto(data.getNamedItem("H").getNodeValue());
				
				ArrayList<InfoInstalacionBean.Unidad> unidadesNegocio = new ArrayList<InfoInstalacionBean.Unidad>();

				NodeList 	 nodeUN	 	    = doc.getElementsByTagName("u");
                NamedNodeMap un				= null;

				if (nodeUN != null) {
                    for (int i = 0;i<nodeUN.getLength();i++){
                        Node node_un = nodeUN.item(i);
                        un = node_un.getAttributes();
                        //rellenamos uNegocio
						InfoInstalacionBean.Unidad unidad = new InfoInstalacionBean.Unidad();
						unidad.setIdUnidad(un.getNamedItem("A").getNodeValue());
						unidad.setIdModelo(un.getNamedItem("B").getNodeValue());					
						unidad.setDescModelo(un.getNamedItem("C").getNodeValue());
						unidad.setDescMarca(un.getNamedItem("D").getNodeValue());
						unidad.setNoSerie(un.getNamedItem("E").getNodeValue());
                        unidad.setNoInventario(un.getNamedItem("F").getNodeValue());
                        unidad.setNoIMEI(un.getNamedItem("G").getNodeValue());
						unidad.setNoEquipo(un.getNamedItem("H").getNodeValue());
						unidad.setPosicionInventario(un.getNamedItem("I").getNodeValue());
						unidad.setDescStatusUnidad(un.getNamedItem("J").getNodeValue());
						unidad.setIdStatusUnidad(un.getNamedItem("K").getNodeValue());
						unidad.setDescNegocio(un.getNamedItem("L").getNodeValue());
						unidadesNegocio.add(unidad);
					}
				}
				iib.setUnidadesNegocio(unidadesNegocio);

				
			}
		}
		catch(SocketTimeoutException ex){
			iib.setRes("ERROR");
			iib.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			iib.setConnStatus(100);
			e.printStackTrace();
		}

		return iib;
	}

	public static InfoRetiroBean obtainInfoRetiro(String ia, int idT)
    {   // Parser de retiro.
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String URL_UPDATE	= URL_SERVER + "/infoRetiro.asp";
		//RequestDetailBean requestDetailBean = new RequestDetailBean();
		InfoRetiroBean irb = new InfoRetiroBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + ia + "&it=" + idT;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			irb.setConnStatus(response.getStatusLine().getStatusCode());

			if(irb.getConnStatus() == 200){
				//aqu’ terminan las coincidencias

				//checamos el status de la validaci—n?

				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 	 nodeR	   		= doc.getElementsByTagName("r");
				Node 		 node_r 		= null;
				NamedNodeMap r 				= null;
				if (nodeR != null){
					node_r 		= nodeR.item(0);
					if (node_r !=null){
						r				= node_r.getAttributes();
					}
				}

				NodeList 	 nodeData	    = doc.getElementsByTagName("data");
				Node 		 node_data	 	= null;
				NamedNodeMap data			= null;

				if (nodeData != null){
					node_data = nodeData.item(0);
					if (node_data !=null){
						data = node_data.getAttributes();
					}
				}

				//requestDetailBean.setIdNegocio(nodeMap.getNamedItem("ID_NEGOCIO").getNodeValue());

				irb.setRes(r.getNamedItem("res").getNodeValue());
				irb.setVal(r.getNamedItem("val").getNodeValue());
				irb.setDesc(r.getNamedItem("desc").getNodeValue());
				
				irb.setStatusRet(Integer.parseInt(r.getNamedItem("val").getNodeValue()));

				if (data != null){
					//rellenamos el campo de datos.
					irb.setIdNegocio(data.getNamedItem("A").getNodeValue());
					irb.setIdCliente(data.getNamedItem("B").getNodeValue());
					irb.setIsBom(data.getNamedItem("C").getNodeValue());
					irb.setButtonDisplay(data.getNamedItem("D").getNodeValue());
					irb.setCancelButtonDisplay(data.getNamedItem("E").getNodeValue());
					irb.setEdit(data.getNamedItem("F").getNodeValue());
					irb.setIdTipoProducto(data.getNamedItem("G").getNodeValue());
				}
				ArrayList<InfoRetiroBean.Unidad> unidadesNegocio = new ArrayList<InfoRetiroBean.Unidad>();

				NodeList 	 nodeUN	 	    = doc.getElementsByTagName("un");
				NamedNodeMap un				= null;

				if (nodeUN != null){
					for (int i=0;i<nodeUN.getLength();i++){
						Node node_un = nodeUN.item(i);
						un = node_un.getAttributes();
						//rellenamos uNegocio
						InfoRetiroBean.Unidad unidad = new InfoRetiroBean.Unidad();
						unidad.setIdUnidad(un.getNamedItem("A").getNodeValue());
						unidad.setIdModelo(un.getNamedItem("B").getNodeValue());					
						unidad.setDescModelo(un.getNamedItem("C").getNodeValue());
						unidad.setDescMarca(un.getNamedItem("D").getNodeValue());
						unidad.setNoSerie(un.getNamedItem("E").getNodeValue());
						unidad.setNoInventario(un.getNamedItem("F").getNodeValue());
						unidad.setNoIMEI(un.getNamedItem("G").getNodeValue());
						unidad.setNoEquipo(un.getNamedItem("H").getNodeValue());
						unidad.setPosicionInventario(un.getNamedItem("I").getNodeValue());
						unidad.setDescStatusUnidad(un.getNamedItem("J").getNodeValue());
						unidad.setIdStatusUnidad(un.getNamedItem("K").getNodeValue());
						unidad.setDescNegocio(un.getNamedItem("L").getNodeValue());
						unidadesNegocio.add(unidad);
					}
				}
				irb.setUnidadesNegocio(unidadesNegocio);
				
				//Unidades de Retiro
				ArrayList<InfoRetiroBean.UnidadRetiro> unidadesRetiro = new ArrayList<InfoRetiroBean.UnidadRetiro>();

				NodeList 	 nodeUR	 	    = doc.getElementsByTagName("ur");
				NamedNodeMap ur				= null;

				if (nodeUR != null){
					for (int i=0;i<nodeUR.getLength();i++){
						Node node_ur = nodeUR.item(i);
						ur = node_ur.getAttributes();
						//rellenamos uRetiro
						InfoRetiroBean.UnidadRetiro unidad = new InfoRetiroBean.UnidadRetiro();
						unidad.setIdUnidad(ur.getNamedItem("A").getNodeValue());
						unidad.setIdModelo(ur.getNamedItem("B").getNodeValue());					
						unidad.setDescModelo(ur.getNamedItem("C").getNodeValue());
						unidad.setDescMarca(ur.getNamedItem("D").getNodeValue());
						unidad.setNoSerie(ur.getNamedItem("E").getNodeValue());
						unidad.setNoInventario(ur.getNamedItem("F").getNodeValue());
						unidad.setNoIMEI(ur.getNamedItem("G").getNodeValue());
						unidad.setNoEquipo(ur.getNamedItem("H").getNodeValue());
						unidad.setPosicionInventario(ur.getNamedItem("I").getNodeValue());
						unidad.setDescStatusUnidad(ur.getNamedItem("J").getNodeValue());
						unidad.setIdStatusUnidad(ur.getNamedItem("K").getNodeValue());
						unidadesRetiro.add(unidad);
					}
				}
				irb.setUnidadesRetiro(unidadesRetiro);
			}
		}
		catch(SocketTimeoutException ex){
			irb.setRes("ERROR");
			irb.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			irb.setConnStatus(100);
			e.printStackTrace();
		}

		return irb;
	}

	public static SendRechazoResponseBean sendCierreRechazo(SendRechazoBean sendRechazoBean)
    {   // Envía cierre por rechazo
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SET_STATUS	= URL_SERVER + "/confirmarRechazo.asp";
		SendRechazoResponseBean sendRechazoResponseBean = new SendRechazoResponseBean();
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_SET_STATUS + "?ia=" + sendRechazoBean.getIdAR();
			
			if(sendRechazoBean.getIsIdCausaRechazoRequired() == 1)
				fullHTTP += "&icr=" + URLEncoder.encode(sendRechazoBean.getIdCausaRechazo());
			if(sendRechazoBean.getEspecificaCausaRechazoRequired() == 1)
				fullHTTP += "&ecr=" + URLEncoder.encode(sendRechazoBean.getIdEspecificaCausaRechazo());
			if(sendRechazoBean.getIsCausaSolucionRequired() == 1){
				fullHTTP += "&ic=" + URLEncoder.encode(sendRechazoBean.getIdCausa());
				fullHTTP += "&is=" + URLEncoder.encode(sendRechazoBean.getIdSolucion());
			}
			if(sendRechazoBean.getIsDescripcionTrabajoRechazoRequired() == 1)
				fullHTTP += "&dt=" + URLEncoder.encode(sendRechazoBean.getDescriptionTrabajo());
			if(sendRechazoBean.getIsFolioServicioRechazoRequired() == 1)
				fullHTTP += "&fs=" + URLEncoder.encode(sendRechazoBean.getFolioServicioRechazo());
			if(sendRechazoBean.getIsOtorganteVoBoRechazoRequired() == 1)
				fullHTTP += "&ovr=" + URLEncoder.encode(sendRechazoBean.getOtorganteVoBoRechazo());
			
			//VERIFIED IF NO SIM IS REQUIRED AND ADD FIELD TO STRING fullHTTP \( '.'   )
			if(sendRechazoBean.getIsClaveRechazoRequired() == 1)
				fullHTTP += "&crz=" + URLEncoder.encode(sendRechazoBean.getClaveRechazo());
			
			fullHTTP += "&au=" + URLEncoder.encode(sendRechazoBean.getAutorizador());
			fullHTTP += "&fc=" + URLEncoder.encode(sendRechazoBean.getFecCierre());
			fullHTTP += "&it=" + URLEncoder.encode(sendRechazoBean.getIdTecnico());
			fullHTTP += "&pr=" + URLEncoder.encode(sendRechazoBean.getPrecioRechazo());

			System.out.println("FullHTTP = " + fullHTTP);
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset","utf-8");
						
			HttpResponse 		response 	= httpClient.execute(pagePost);
			
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 	 n		= doc.getElementsByTagName("r");
				NamedNodeMap r		= n.item(0).getAttributes();

				sendRechazoResponseBean.setRes(r.getNamedItem("res").getNodeValue());
				sendRechazoResponseBean.setDesc(r.getNamedItem("desc").getNodeValue());
				
				System.out.println("res = " + sendRechazoResponseBean.getRes());
				System.out.println("desc = " + sendRechazoResponseBean.getDesc());
				if(r.getNamedItem("res").getNodeValue().trim().equals("OK"))
                    sendRechazoResponseBean.setVal(r.getNamedItem("val").getNodeValue());
			}
		}
		catch(SocketTimeoutException ex){
			sendRechazoResponseBean.setRes("ERROR");
			sendRechazoResponseBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
			sendRechazoResponseBean.setRes("ERROR");
			sendRechazoResponseBean.setDesc("Error al procesar la solicitud, intente más tarde");
		}

		return sendRechazoResponseBean;
	}

	public static SendExitoResponseBean sendCierreExito(ValidateClosureBean seb)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SET_STATUS	= URL_SERVER + "/confirmarExito.asp";
		SendExitoResponseBean serb = new SendExitoResponseBean();
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_SET_STATUS + "?id=" + seb.getIdAR();
			//Revisar
			fullHTTP += "&s=" + URLEncoder.encode(seb.getIdServicio());
			fullHTTP += "&vn=" + URLEncoder.encode(seb.getVoltajeNeutro());
			fullHTTP += "&fv=" + URLEncoder.encode(seb.getFolioValidacion());
			fullHTTP += "&ft=" + URLEncoder.encode(seb.getFolioTIR());
			fullHTTP += "&vt=" + URLEncoder.encode(seb.getVoltajeTierra());
			fullHTTP += "&vtn=" + URLEncoder.encode(seb.getVoltajeTierraNeutro());
			fullHTTP += "&etf=" + URLEncoder.encode(seb.getIdEspecifiqueTipoFallo());
			
			if(seb.getIsCausaSolucionRequired().equals("1")){
				fullHTTP += "&ca=" + URLEncoder.encode(seb.getIdCausa());
				fullHTTP += "&so=" + URLEncoder.encode(seb.getIdSolucion());
			}
			
			if(seb.getIsTASRequired().equals("1"))
				fullHTTP += "&ftas=" + URLEncoder.encode(seb.getFolioTas());
			
			if(seb.getIsOtorganteTASRequired().equals("1"))
				fullHTTP += "&ot=" + URLEncoder.encode(seb.getOtorganteTAS()); 
			
			if(seb.getIsNoEquipoREquired().equals("1"))
				fullHTTP += "&ne=" + URLEncoder.encode(seb.getNoEquipo());
			
			if(seb.getIsNoSerieRequired().equals("1") && seb.getIsSustitucion().equals("0"))
				fullHTTP += "&ns=" + URLEncoder.encode(seb.getNoSerieFalla());
				
			if(seb.getIsNoInventarioRequired().equals("1") && seb.getIsSustitucion().equals("0"))
				fullHTTP += "&ni=" + URLEncoder.encode(seb.getNoInventarioFalla());
			
			if(seb.getIsIdModeloRequired().equals("1") && seb.getIsSustitucion().equals("0"))
				fullHTTP += "&im=" + URLEncoder.encode(seb.getIdModeloFalla());
			
			if(seb.getIsFecLlegadaRequired().equals("1"))
				fullHTTP += "&fll=" + URLEncoder.encode(seb.getFecLlegada());
			
			if(seb.getIsFecLlegadaTercerosRequired().equals("1"))
				fullHTTP += "&fllt=" + URLEncoder.encode(seb.getFecLlegadaTerceros());
			
			if(seb.getIsFolioServicioRequired().equals("1"))
				fullHTTP += "&fs=" + URLEncoder.encode(seb.getFolioServicio());
			
			if(seb.getIsFecIniIngenieroRequired().equals("1"))
				fullHTTP += "&fiin=" + URLEncoder.encode(seb.getFecIniIngeniero());
			
			if(seb.getIsFecFinIngenieroRequired().equals("1"))
				fullHTTP += "&ffin=" + URLEncoder.encode(seb.getFecFinIngeniero());
			
			if(seb.getIsOtorganteVoBoRequired().equals("1"))
				fullHTTP += "&ov=" + URLEncoder.encode(seb.getOtorganteVoBo());
			
			if(seb.getIsOtorganteVoBoTercerosRequired().equals("1"))
				fullHTTP += "&ovt=" + URLEncoder.encode(seb.getOtorganteVoBoTerceros());
			
			if(seb.getIsIntensidadSenialRequired().equals("1"))
				fullHTTP += "&in=" + URLEncoder.encode(seb.getIntensidadSenial());
			
			if(seb.getIsIsSIMRemplazadaRequired().equals("1"))
				fullHTTP += "&sim=" + URLEncoder.encode(seb.getIdSIMRemplazada());
			
			if(seb.getIsFallaEncontradaRequired().equals("1") || seb.getIsFallaEncontradaRequiredText().equals("1"))
				fullHTTP += "&fe=" + URLEncoder.encode(seb.getFallaEncontrada());
			
			if(seb.getIsOtorganteVoBoClienteRequired().equals("1"))
				fullHTTP += "&ovc=" + URLEncoder.encode(seb.getOtorganteVoBoCliente());
			
			if(seb.getIsMotivoCobroRequired().equals("1"))
				fullHTTP += "&mc=" + URLEncoder.encode(seb.getMotivoCobro());
			
			if(seb.getIsIsSoporteClienteRequired().equals("1"))
				fullHTTP += "&sc=" + URLEncoder.encode(seb.getIsSoporteCliente());
			
			if(seb.getIsIsBoletinRequired().equals("1"))
				fullHTTP += "&b=" + URLEncoder.encode(seb.getIsboletin());
			
			if(seb.getIsOtorganteSoporteClienteRequired().equals("1"))
				fullHTTP += "&osc=" + URLEncoder.encode(seb.getOtorganteSoporteCliente());
			
			if(seb.getIsCadenaCierreEscritaRequired().equals("1"))
				fullHTTP += "&cc=" + URLEncoder.encode(seb.getCadenaCierreEscrita());
			
			if(seb.getIsDigitoVerificadorRequired().equals("1"))
				fullHTTP += "&dv=" + URLEncoder.encode(seb.getDigitoVerificador());
			
			if(seb.getIsIdTipoFallaEncontradaRequired().equals("1"))
				fullHTTP += "&itfe=" + URLEncoder.encode(seb.getIdTipoFallaEncontrada());
			
			if(seb.getIsCodigoIntervencionRequired().equals("1"))
				fullHTTP += "&ci=" + URLEncoder.encode(seb.getCodigoIntervencion());
			
			if(seb.getIsInstalacionLlavesRequired().equals("1"))
				fullHTTP += "&tll=" + URLEncoder.encode(seb.getTipoLlaves());
			
			if(seb.getIsStatusInstalacionLlavesRequired().equals("1"))
				fullHTTP += "&stll=" + URLEncoder.encode(seb.getStatusInstalacionLlaves());
			
			if(seb.getIsNombrePersonaLlavesARequired().equals("1"))
				fullHTTP += "&nplla=" + URLEncoder.encode(seb.getNombrePersonalLlavesA());
			
			if(seb.getIsNombrePersonaLlavesBRequired().equals("1"))
				fullHTTP += "&npllb=" + URLEncoder.encode(seb.getNombrePersonalLlavesA());
			
			if(seb.getIsTipoPwRequired().equals("1"))
				fullHTTP += "&tpw=" + URLEncoder.encode(seb.getTipoPw());
			
			if(seb.getIsTipoTecladoRequired().equals("1"))
				fullHTTP += "&tt=" + URLEncoder.encode(seb.getTisTipoTeclado()); 
			
			if(seb.getIsVersionTecladoEPPRequired().equals("1"))
				fullHTTP += "&vtepp=" + URLEncoder.encode(seb.getVersionTecladoEPP());
			
			if(seb.getIsProcesadorRequired().equals("1"))
				fullHTTP += "&pr=" + URLEncoder.encode(seb.getProcesador());
			
			if(seb.getIsVelocidadProcesadorRequired().equals("1"))
				fullHTTP += "&vp=" + URLEncoder.encode(seb.getVelocidadProcesador());
			
			if(seb.getIsMemoriaRequired().equals("1"))
				fullHTTP += "&me=" + URLEncoder.encode(seb.getMemoria());
			
			if(seb.getIsCapacidadDiscoDuroRequired().equals("1"))
				fullHTTP += "&cdd=" + URLEncoder.encode(seb.getCapacidadDiscoDuro());
			
			if(seb.getIsMonitorRequired().equals("1"))
				fullHTTP += "&mon=" + URLEncoder.encode(seb.getMonitor());
			
			if(seb.getIsLectorTarjetaRequired().equals("1"))
				fullHTTP += "&lt=" + URLEncoder.encode(seb.getLectorTarjeta());
			
			if(seb.getIsAplicacionRequired().equals("1"))
				fullHTTP += "&app=" + URLEncoder.encode(seb.getAplicacion_santander()); 
			
			if(seb.getIsVersionRequired().equals("1"))
				fullHTTP += "&ver=" + URLEncoder.encode(seb.getVersion_santander());
			
			if(seb.getIsCajaRequired().equals("1"))
				fullHTTP += "&caj=" + URLEncoder.encode(seb.getCaja());
			
			if(seb.getIsDescTrabajoCatalogRequired().equals("1"))
				fullHTTP += "&desct=" + URLEncoder.encode(seb.getIdDescripcionTrabajo());
			else
				fullHTTP += "&desc=" + URLEncoder.encode(seb.getDescripcionTrabajo());
			
			//VERIFIED IF CLAVE RECHAZO IS REQUIRED AND ADD FIELD TO STRING fullHTTP \( '.'   )
			if(seb.getIsSIMRequired().equals("1"))
				fullHTTP += "&simm=" + URLEncoder.encode(seb.getSIM());

			if(seb.getIsCalidadBilleteRequired().equals("1")){
			    fullHTTP += "&cbillete=" + URLEncoder.encode(seb.getIdCalidadBillete());
            }

            if(seb.getIsCondicionSiteRequired().equals("1")){
                fullHTTP += "&cSite=" + URLEncoder.encode(seb.getIdCondicionSite());
            }

			fullHTTP += "&at=" + URLEncoder.encode(seb.getAtiende());
			
			fullHTTP += "&fecc=" + URLEncoder.encode(seb.getFecCierre());
			fullHTTP += "&pre=" + URLEncoder.encode(seb.getPrecioExito());
			fullHTTP += "&it=" + URLEncoder.encode(seb.getIdTecnico()); //IdUsuario?
            Log.e("Ruta",fullHTTP);
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);
			
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				
				//QUITAR WRITE
//				writeResults("confirmarExito", fullHTTP, doc);

				NodeList 	 n		= doc.getElementsByTagName("r");
				NamedNodeMap r		= n.item(0).getAttributes();
				
				serb.setRes(r.getNamedItem("res").getNodeValue());
				serb.setDesc(r.getNamedItem("desc").getNodeValue());
				
				if(r.getNamedItem("res").getNodeValue().trim().equals("OK"));
				serb.setVal(r.getNamedItem("val").getNodeValue());
			}
		}
		catch(SocketTimeoutException ex){
			serb.setRes("ERROR");
			serb.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return serb;
	}
	
	public static InfoInsumoBean obtainInfoInsumo(String ia)
    {
        ia = ia.replace("'","");

        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
        String URL_UPDATE	= URL_SERVER + "/infoInsumo.asp";
        InfoInsumoBean iib = new InfoInsumoBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP = URL_UPDATE + "?ia=" + ia;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
            HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
            HttpResponse 		response 	= httpClient.execute(pagePost);

            //Respuesta del servidor
			iib.setConnStatus(response.getStatusLine().getStatusCode());

			if(iib.getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 	 nodeR	   		= doc.getElementsByTagName("r");
				Node 		 node_r 		= null;
				NamedNodeMap r 				= null;
				if (nodeR != null){
					node_r 		= nodeR.item(0);
					if (node_r !=null){
						r				= node_r.getAttributes();
					}
				}
				
				iib.setRes(r.getNamedItem("res").getNodeValue());
				
				iib.setDesc(r.getNamedItem("desc").getNodeValue());
				
				Node NoInsumosRollo = r.getNamedItem("NO_INSUMOS_ROLLO");
				if (NoInsumosRollo!=null){
					iib.setNoInsumosRollo(NoInsumosRollo.getNodeValue());
				}
				
				Node idCliente = r.getNamedItem("ID_CLIENTE");
				if(idCliente != null){
					iib.setIdCliente(idCliente.getNodeValue());
				}
				
				if (iib.getRes().equals("OK"))
					iib.setStatusRet(1);
				else
					iib.setStatusRet(2);

				ArrayList<InfoInsumoBean.Insumo> insumos = new ArrayList<InfoInsumoBean.Insumo>();

				NodeList 	 nodeIN	 	    = doc.getElementsByTagName("ins");
				NamedNodeMap in				= null;

				if (nodeIN != null){
					for (int i=0;i<nodeIN.getLength();i++){
						Node node_in = nodeIN.item(i);
						in = node_in.getAttributes();
						//rellenamos Insumo
						InfoInsumoBean.Insumo insumo = new InfoInsumoBean.Insumo();
						insumo.setIdArInsumo(in.getNamedItem("ID_AR_INSUMO").getNodeValue());
						insumo.setDescInsumo(in.getNamedItem("DESC_INSUMO").getNodeValue());
						insumo.setDescUsuario(in.getNamedItem("DESC_USUARIO").getNodeValue());
						insumo.setFecAlta(in.getNamedItem("FEC_ALTA").getNodeValue());
						insumo.setCantidad(in.getNamedItem("CANTIDAD").getNodeValue());
						insumo.setCostoUnitario(in.getNamedItem("COSTO_UNITARIO").getNodeValue());
						insumo.setCostoTotal(in.getNamedItem("COSTO_TOTAL").getNodeValue());
						insumos.add(insumo);
						
					}
				}
				iib.setInsumos(insumos);
			}
		}
		catch(SocketTimeoutException ex){
			iib.setRes("ERROR");
			iib.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			iib.setConnStatus(100);
			e.printStackTrace();
		}

		return iib; 
	}	

	public static InfoMovimientosBean obtainInfoMovimientos(String ia)
    {   // Parser de validarMovimientos.asp
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/validarMovimientos.asp";
        InfoMovimientosBean imb = new InfoMovimientosBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + ia;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			imb.setConnStatus(response.getStatusLine().getStatusCode());

			if(imb.getConnStatus() == 200){
				//aqu’ terminan las coincidencias

				//checamos el status de la validación?

				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				// end
				NodeList 	 nodeR	   		= doc.getElementsByTagName("r");
				Node 		 node_r 		= nodeR.item(0);
				NamedNodeMap r				= node_r.getAttributes();

				NodeList 	 nodeT  		= doc.getElementsByTagName("T");
				Node 		 node_t 		= nodeT.item(0);
				NamedNodeMap t			= node_t.getAttributes();
				String tValue;
					

				imb.setRes(r.getNamedItem("V").getNodeValue());
				imb.setDesc(r.getNamedItem("D").getNodeValue());

				imb.setActualizacion(false);
				imb.setInstalacion(false);
				imb.setSustitucion(false);
				imb.setRetiro(false);
				imb.setInsumo(false);
				//Revisamos todos los tags T, que muestran los permisos
				for(int i = nodeT.getLength() - 1; i >= 0; i--){
					node_t = nodeT.item(i);
					t = node_t.getAttributes();
						
					tValue = t.getNamedItem("V").getNodeValue();
						
					if(tValue.equalsIgnoreCase("actualizacion.asp")){
						imb.setActualizacion(true);
					}
					else if(tValue.equalsIgnoreCase("instalacion.asp")){
						imb.setInstalacion(true);
					}
					else if(tValue.equalsIgnoreCase("sustitucion.asp")){
						imb.setSustitucion(true);
					}
					else if(tValue.equalsIgnoreCase("retiro.asp")){
						imb.setRetiro(true);
					}
					else if(tValue.equalsIgnoreCase("insumos.asp")){
						imb.setInsumo(true);
					}
					
				}
				
			}
		}
		catch(SocketTimeoutException ex){
			imb.setRes("ERROR");
			imb.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			imb.setConnStatus(100);
			e.printStackTrace();
		}

		return imb; 
	}

	public static InformacionCierreBean getInformacionCierre(String id)
    {   // Adquiere la cadena de cierre
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getCadenaCierre.asp";

		InformacionCierreBean informacionCierreBean = new InformacionCierreBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + id;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			informacionCierreBean.setConnStatus(response.getStatusLine().getStatusCode());

			if(informacionCierreBean.getConnStatus() == 200) {
                DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
                DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("r");
				NamedNodeMap	nodeMap		= node.item(0).getAttributes();
			
				informacionCierreBean.setRes(nodeMap.getNamedItem("res").getNodeValue());
				informacionCierreBean.setDesc(nodeMap.getNamedItem("desc").getNodeValue());
				
				try{
					informacionCierreBean.setVal(nodeMap.getNamedItem("val").getNodeValue());
				}catch(Exception e){
					
				}
			}
		}
		catch(SocketTimeoutException ex){
			informacionCierreBean.setRes("ERROR");
			informacionCierreBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			informacionCierreBean.setConnStatus(100);
			e.printStackTrace();
		}

		return informacionCierreBean;
	}
	

	public static InfoActualizacionBean getInfoActualizacion(String id)
    {   // Adquiere los valores de Info Actualizacion
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/infoActualizacion.asp";
		InfoActualizacionBean infoActualizacionBean = new InfoActualizacionBean();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + id;
			final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			infoActualizacionBean.setConnStatus(response.getStatusLine().getStatusCode());

			if(infoActualizacionBean.getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 			= doc.getElementsByTagName("r");
				NamedNodeMap	nodeMap			= node.item(0).getAttributes();

				NodeList 		nodeDataList 	= doc.getElementsByTagName("data");
				NamedNodeMap	nodeDatasMap	= nodeDataList.item(0).getAttributes();
				
				infoActualizacionBean.setRes(nodeMap.getNamedItem("res").getNodeValue());
				infoActualizacionBean.setDesc(nodeMap.getNamedItem("desc").getNodeValue());
				
				infoActualizacionBean.setIdNegocio(nodeDatasMap.getNamedItem("A").getNodeValue());
				infoActualizacionBean.setIdCliente(nodeDatasMap.getNamedItem("B").getNodeValue());
				infoActualizacionBean.setIdStatus(nodeDatasMap.getNamedItem("C").getNodeValue());
				infoActualizacionBean.setIsBom(nodeDatasMap.getNamedItem("D").getNodeValue());
				infoActualizacionBean.setIsUnidadAtendida(nodeDatasMap.getNamedItem("E").getNodeValue());
				infoActualizacionBean.setIsUnidadAtendidaRequired(nodeDatasMap.getNamedItem("F").getNodeValue());
				infoActualizacionBean.setDescTecnico(nodeDatasMap.getNamedItem("G").getNodeValue());
				infoActualizacionBean.setDescNegocio(nodeDatasMap.getNamedItem("H").getNodeValue());
			}
		}
		catch(SocketTimeoutException ex){
			infoActualizacionBean.setRes("ERROR");
			infoActualizacionBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			infoActualizacionBean.setConnStatus(100);
			e.printStackTrace();
		}

		return infoActualizacionBean;
	}

	public static GenericResultBean borrarUnidad(String idUnidad,String idNegocio)
    {   // Elimina un registro de Unidad
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SEND_COMMENT	= URL_SERVER + "/borrarUnidadNegocio.asp";

		String iu="";
		String in="";

		try{
			iu = URLEncoder.encode(idUnidad, "UTF-8");
			in = URLEncoder.encode(idNegocio, "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String				fullHTTP	= URL_SEND_COMMENT + "?iu=" + iu + "&in=" + in;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					try{
						genericResultBean.setRes(r.getNamedItem("res").getNodeValue());
					}catch(Exception e){	
					}
					try{
						genericResultBean.setDesc(r.getNamedItem("desc").getNodeValue());
					}catch(Exception e){	
					}
					try{
						genericResultBean.setVal(r.getNamedItem("val").getNodeValue());
					}catch(Exception e){	
					}
					
					//Revisa si el usuario es correcto
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						//Se realizó el procedure, regresando true
					}
					else{
					}
				}
				else{
				}
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return genericResultBean;
	}

	public static GenericResultBean agregarUnidad(String idCliente, String noSerie, String noInventario,
        String idModelo, String noSIM, String idSolicitudRec, String isNueva, String idMarca,
        String noIMEI, String idTipoResponsable, String idResponsable, String idLlave,
        String idSoftware, String isRetiro, String posicionInventario, String idTecnico,
        String idStatusUnidad, String noEquipo, String idNegocio, String isDaniada, String idCarrier,
        String idConnectivity)
    {   // Agrega una Unidad de Negocio
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/insertarUnidadNegocio.asp";

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_ADD_UNIDAD + "?";

			if(idCliente.equals("")){
				fullHTTP += "ic=" + URLEncoder.encode("-1", "UTF-8");
			}
			else{
				fullHTTP += "ic=" + URLEncoder.encode(idCliente, "UTF-8");
			}
			
			/*if(idAR.equals("")) {
				fullHTTP += "&iAr=" + URLEncoder.encode("-1", "UTF-8");
			} else {
				fullHTTP += "&iAr=" + URLEncoder.encode(idAR, "UTF-8");
			}*/

			if(noSerie == null || noSerie.equals("")){
			}
			else{
				fullHTTP += "&nose=" + URLEncoder.encode(noSerie, "UTF-8");
			}

			if(noInventario == null || noInventario.equals("")){
			}
			else{
				fullHTTP += "&noi=" + URLEncoder.encode(noInventario, "UTF-8");
			}

			if(idModelo == null || idModelo.equals("")){
				fullHTTP += "&imo=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&imo=" + URLEncoder.encode(idModelo, "UTF-8");
			}

			if(noSIM == null || noSIM.equals("")){
			}
			else{
				fullHTTP += "&nosim=" + URLEncoder.encode(noSIM, "UTF-8");
			}

			if(idSolicitudRec == null || idSolicitudRec.equals("")){
				fullHTTP += "&idsr=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&idsr=" + URLEncoder.encode(idSolicitudRec, "UTF-8");
			}

			if(isNueva == null || isNueva.equals("")){
				fullHTTP += "&in=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&in=" + URLEncoder.encode(isNueva, "UTF-8");
			}

			if(idMarca == null || idMarca.equals("")){
				fullHTTP += "&ima=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&ima=" + URLEncoder.encode(idMarca, "UTF-8");
			}

			if(noIMEI == null || noIMEI.equals("")){
			}
			else{
				fullHTTP += "&noimei=" + URLEncoder.encode(noIMEI, "UTF-8");
			}

			if(idTipoResponsable == null || idTipoResponsable.equals("")){
				fullHTTP += "&idtr=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&idtr=" + URLEncoder.encode(idTipoResponsable, "UTF-8");
			}

			if(idResponsable == null || idResponsable.equals("")){
				fullHTTP += "&ir=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&ir=" + URLEncoder.encode(idResponsable, "UTF-8");
			}

			if(idLlave == null || idLlave.equals("")){
			}
			else{
				fullHTTP += "&il=" + URLEncoder.encode(idLlave, "UTF-8");
			}

			if(idSoftware == null || idSoftware.equals("")){
			}
			else{
				fullHTTP += "&is=" + URLEncoder.encode(idSoftware, "UTF-8");
			}

            if(idConnectivity != null)
            {
                if(!idConnectivity.equals(""))
                {
                    fullHTTP += "&iconn=" + URLEncoder.encode(idConnectivity, "UTF-8");
                }
            }

			if(isRetiro == null || isRetiro.equals("")){
				fullHTTP += "&isr=" + URLEncoder.encode("-1", "UTF-8");
			}
			else{
				fullHTTP += "&isr=" + URLEncoder.encode(isRetiro, "UTF-8");
			}

			if(posicionInventario == null || posicionInventario.equals("")){
			}
			else{
				fullHTTP += "&pi=" + URLEncoder.encode(posicionInventario, "UTF-8");
			}

			if(idTecnico == null || idTecnico.equals("")){
				fullHTTP += "&it=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&it=" + URLEncoder.encode(idTecnico, "UTF-8");
			}

			if(idStatusUnidad == null || idStatusUnidad.equals("")){
				fullHTTP += "&isu=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&isu=" + URLEncoder.encode(idStatusUnidad, "UTF-8");
			}

			if(noEquipo == null || noEquipo.equals("")){
			}
			else{
				fullHTTP += "&noe=" + URLEncoder.encode(noEquipo, "UTF-8");
			}

			if(idNegocio == null || idNegocio.equals("")){
				fullHTTP += "&ineg=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&ineg=" + URLEncoder.encode(idNegocio, "UTF-8");
			}
			
			if(idCarrier == null || idCarrier.equals("")){
				fullHTTP += "&icar=" + URLEncoder.encode("-1");
			}
			else{
				fullHTTP += "&icar=" + URLEncoder.encode(idCarrier, "UTF-8");
			}
            Log.e("FULL:",fullHTTP);
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("r");

				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();

					try{
						genericResultBean.setRes(r.getNamedItem("res").getNodeValue());
					}catch(Exception e){	
					}
					try{
						genericResultBean.setDesc(r.getNamedItem("desc").getNodeValue());
					}catch(Exception e){	
					}
					try{
						genericResultBean.setVal(r.getNamedItem("val").getNodeValue());
					}catch(Exception e){	
					}
				}
				else{
					genericResultBean.setDesc("Los datos son incorrectos");
				}
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return genericResultBean;
	}

	public static GenericResultBean sendPendienteTerminal(String idAR, String idTecnico,
        String idAlmacen, ArrayList<EntityCatalog> terminals, ArrayList<EntityCatalog> insumos,
        String idUrgencia, String notas, String tipoServicio, String idDireccion, String fechaCompromiso,
        String isKitInsumo, String idKitInsumo, String isConnectivity)
	{   // Pendiente (Terminal)
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/peticionTerminal.asp";

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_ADD_UNIDAD + "?";
			
			if(idAR.equals("")){
				fullHTTP += "ia=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "ia=" + URLEncoder.encode(idAR, "UTF-8");
			}
			
			if(idTecnico.equals("")){
				fullHTTP += "&iTec=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&iTec=" + URLEncoder.encode(idTecnico, "UTF-8");
			}
			
			if(idAlmacen.equals("")) {
				fullHTTP += "&iAlm=" + URLEncoder.encode("0", "UTF-8");
			}
			else {
				fullHTTP += "&iAlm=" + URLEncoder.encode(idAlmacen, "UTF-8");
			}
			
			if(terminals == null)
			{
				fullHTTP += "&terminalInfo=" + URLEncoder.encode("null", "UTF-8");
			}
			else
			{
				String terInfo = "";
				for(int i = 0; i < terminals.size(); i++)
				{
					terInfo += terminals.get(i).toString() + ((i == terminals.size() - 1) ? "" : "#");
				}
				
				fullHTTP += "&terminalInfo=" + URLEncoder.encode(terInfo, "UTF-8");
			}
			
			if(insumos == null)
			{
				fullHTTP += "&insumosInfo=" + URLEncoder.encode("null", "UTF-8");
			}
			else
			{
				String terInfo = "";
				for(int i = 0; i < insumos.size(); i++)
				{
					terInfo += insumos.get(i).toString() + ((i == insumos.size() - 1) ? "" : "#");
				}
				
				fullHTTP += "&insumosInfo=" + URLEncoder.encode(terInfo, "UTF-8");
			}
			
			if(idUrgencia.equals("")){
				fullHTTP += "&idU=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&idU=" + idUrgencia;
			}
			
			if(notas.equals("")){
				fullHTTP += "&notes=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&notes=" + URLEncoder.encode(notas, "UTF-8");
			}
			
			if(tipoServicio.equals("")){
				fullHTTP += "&typeSer=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&typeSer=" + tipoServicio;
			}
			
			if(idDireccion.equals("")){
				fullHTTP += "&idDir=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&idDir=" + URLEncoder.encode(idDireccion, "UTF-8");
			}
			
			if(fechaCompromiso.equals("")){
				fullHTTP += "&fecha=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&fecha=" + URLEncoder.encode(fechaCompromiso, "UTF-8");
			}

            fullHTTP += "&isKit=" + URLEncoder.encode(isKitInsumo.trim(), "UTF-8");
            fullHTTP += "&idKit=" + URLEncoder.encode(idKitInsumo.trim(), "UTF-8");
            fullHTTP += "&isConn=" + URLEncoder.encode(isConnectivity.trim(), "UTF-8");

			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);
			
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				
				NodeList list = doc.getElementsByTagName("pend");
				
				if (list != null)
				{
					if(list.getLength() > 0)
					{
						String posError = "";
						for(int i = 0; i < list.getLength(); i++)
						{
							NamedNodeMap nPend = list.item(i).getAttributes();
							
							try
							{
								String result = nPend.getNamedItem("res").getNodeValue();
								if(result.equals("ERROR"))
								{
									posError = "Error in item " + String.valueOf("i");
								}
							}
							catch(Exception e) {}
						}
						
						if(posError.equals(""))
						{
							genericResultBean.setRes("OK");
							genericResultBean.setDesc("Se ha realizado el cambio de status exitosamente!");
						}
						else
						{
							genericResultBean.setRes("ERROR");
							genericResultBean.setDesc(posError);
						}
					}
				}
				else
				{
					genericResultBean.setRes("ERROR");
					genericResultBean.setDesc("Los datos son incorrectos");
				}
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return genericResultBean;
	}

	public static GenericResultBean sendPendienteSparePart(String idAR, String idTecnico,
    String idSparePart, String idAlmacen, String idUrgencia, String notas, String tipoServicio,
    String idDireccion, String fechaCompromiso, String cantidad)
    {   // Pendiente Spare Part
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/peticionSparePart.asp";
		
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_ADD_UNIDAD + "?";
			
			if(idAR.equals("")){
				fullHTTP += "ia=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "ia=" + URLEncoder.encode(idAR, "UTF-8");
			}
			
			if(idTecnico.equals("")){
				fullHTTP += "&i=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&i=" + URLEncoder.encode(idTecnico, "UTF-8");
			}
			
			if(idSparePart.equals("")){
				fullHTTP += "&is=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&is=" + URLEncoder.encode(idSparePart, "UTF-8");
			}
			
			if(idAlmacen.equals("")){
				fullHTTP += "&a=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&a=" + URLEncoder.encode(idAlmacen, "UTF-8");
			}
			
			if(idUrgencia.equals("")){
				fullHTTP += "&u=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&u=" + idUrgencia;
			}
			
			if(notas.equals("")){
				fullHTTP += "&n=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&n=" + URLEncoder.encode(notas, "UTF-8");
			}
			
			if(tipoServicio.equals("")){
				fullHTTP += "&ts=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&ts=" + tipoServicio;
			}
			
			if(idDireccion.equals("")){
				fullHTTP += "&idir=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&idir=" + URLEncoder.encode(idDireccion, "UTF-8");
			}
			
			if(fechaCompromiso.equals("")){
				fullHTTP += "&fecha=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&fecha=" + URLEncoder.encode(fechaCompromiso, "UTF-8");
			}
			
			if(cantidad.equals("")){
				fullHTTP += "&c=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&c=" + URLEncoder.encode(cantidad, "UTF-8");
			}
			
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);
			
			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);
			
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				
				NodeList list = doc.getElementsByTagName("r");
				
				if (list!=null && list.getLength()>0){
					NamedNodeMap r	= list.item(0).getAttributes();
					
					try{
						genericResultBean.setRes(r.getNamedItem("res").getNodeValue());
					}catch(Exception e){	
					}
					try{
						genericResultBean.setDesc(r.getNamedItem("desc").getNodeValue());
					}catch(Exception e){	
					}
				}
				else{
					genericResultBean.setRes("ERROR");
					genericResultBean.setDesc("Los datos son incorrectos");
				}
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return genericResultBean;
	}

	public static GenericResultBean sendPendienteInsumo(String idAR, String idTecnico,
        String idAlmacen, EntityCatalog[] insumos, String idUrgencia, String notas,
        String tipoServicio, String idDireccion, String fechaCompromiso, String isKitInsumo,
        String idKitInsumo)
    {   // Pendiente Insumo
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/peticionInsumo.asp";

		try
		{	//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
			String				fullHTTP	= URL_ADD_UNIDAD + "?";

			if(idAR.equals("")) {
				fullHTTP += "ia=" + URLEncoder.encode("0", "UTF-8");
			}
			else {
				fullHTTP += "ia=" + URLEncoder.encode(idAR, "UTF-8");
			}

			if(idTecnico.equals("")) {
				fullHTTP += "&iTec=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&iTec=" + URLEncoder.encode(idTecnico, "UTF-8");
			}

			if(idAlmacen.equals("")) {
				fullHTTP += "&iAlm=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&iAlm=" + URLEncoder.encode(idAlmacen, "UTF-8");
			}

			if(insumos == null)
			{
				fullHTTP += "&insumosInfo=" + URLEncoder.encode("null", "UTF-8");
			}
			else
			{
				String insumosInfo = "";
				for(int i = 0; i < insumos.length; i++)
				{
					insumosInfo += insumos[i].toString() + ((i == insumos.length - 1) ? "" : "#");
				}

				fullHTTP += "&insumosInfo=" + URLEncoder.encode(insumosInfo, "UTF-8");
			}

			if(idUrgencia.equals("")) {
				fullHTTP += "&idU=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&idU=" + idUrgencia;
			}

			if(notas.equals("")) {
				fullHTTP += "&notes=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&notes=" + URLEncoder.encode(notas, "UTF-8");
			}

			if(tipoServicio.equals("")) {
				fullHTTP += "&typeSer=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&typeSer=" + tipoServicio;
			}

			if(idDireccion.equals("")) {
				fullHTTP += "&idDir=" + URLEncoder.encode("0", "UTF-8");
			}
			else{
				fullHTTP += "&idDir=" + URLEncoder.encode(idDireccion, "UTF-8");
			}

			if(fechaCompromiso.equals("")) {
				fullHTTP += "&fecha=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&fecha=" + URLEncoder.encode(fechaCompromiso, "UTF-8");
			}

            fullHTTP += "&isKit=" + URLEncoder.encode(isKitInsumo.trim(), "UTF-8");
            fullHTTP += "&idKit=" + URLEncoder.encode(idKitInsumo.trim(), "UTF-8");

			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList list = doc.getElementsByTagName("pend");

				if (list != null)
				{
					if(list.getLength() > 0)
					{
						String posError = "";
						for(int i = 0; i < list.getLength(); i++)
						{
							NamedNodeMap nPend = list.item(i).getAttributes();

							try
							{
								String result = nPend.getNamedItem("res").getNodeValue();
								if(result.equals("ERROR"))
								{
									posError = "Error in item " + String.valueOf("i");
								}
							}
							catch(Exception e) {}
						}

						if(posError.equals(""))
						{
							genericResultBean.setRes("OK");
							genericResultBean.setDesc("Se ha realizado el cambio de status exitosamente!");
						}
						else
						{
							genericResultBean.setRes("ERROR");
							genericResultBean.setDesc(posError);
						}
					}
				}
				else
				{
					genericResultBean.setRes("ERROR");
					genericResultBean.setDesc("Los datos son incorrectos");
				}
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return genericResultBean;
	}

	public static AgregarCoordenadasBean agregarCoordenadas(String idNegocio, double latitud,
        double longitud, double accuracy)
    {   // Agrega coordenadas a	un negocio
		AgregarCoordenadasBean agregarCoordenadasBean = new AgregarCoordenadasBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_SEND_COMMENT	= URL_SERVER + "/agregarCoordenadas.asp";

        String in = idNegocio;
		String lat = latitud + "";
		String lon = longitud + "";
		String acc = accuracy + "";

		try{
			in = URLEncoder.encode(in, "UTF-8");
			lat = URLEncoder.encode(lat, "UTF-8");
			lon = URLEncoder.encode(lon, "UTF-8");
			acc = URLEncoder.encode(acc, "UTF-8");
		}

		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		try{
			String				fullHTTP	= URL_SEND_COMMENT + "?in=" + in +"&lat="+lat+"&lon="+lon+"&acc="+acc;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			agregarCoordenadasBean.setConnStatus(response.getStatusLine().getStatusCode());

			if(agregarCoordenadasBean.getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 			= doc.getElementsByTagName("r");
				NamedNodeMap	nodeMap			= node.item(0).getAttributes();

				agregarCoordenadasBean.setRes(nodeMap.getNamedItem("res").getNodeValue());
				agregarCoordenadasBean.setDesc(nodeMap.getNamedItem("desc").getNodeValue());
				
			}
		}
		catch(SocketTimeoutException ex){
			agregarCoordenadasBean.setRes("ERROR");
			agregarCoordenadasBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
			agregarCoordenadasBean.setRes("ERROR");
			agregarCoordenadasBean.setDesc("Hubo un error en la conexión.");
		}
		return agregarCoordenadasBean;
	}

	public static ArrayList<UnitBean> getInfoUnidadesNegocioActualizacion(String idAR, String idNegocio)
    {   // Adquiere las Unidades del Negocio
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/infoUnidadesNegocioActualizacion.asp";
		ArrayList<UnitBean> unitBeannArray = new ArrayList<UnitBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + idAR + "&in=" + idNegocio;
			final HttpParams httpParams 	= new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			unitBeannArray.add(new UnitBean());
			unitBeannArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());
			
			if(unitBeannArray.get(0).getConnStatus() == 200) {
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());
				
				NodeList nodeRoot			= doc.getElementsByTagName("un");
				NodeList nodeIdUnidad		= doc.getElementsByTagName("ID_UNIDAD");
				NodeList nodeIdModelo		= doc.getElementsByTagName("ID_MODELO");
				NodeList nodeDescModelo		= doc.getElementsByTagName("DESC_MODELO");
				NodeList nodeDescMarca		= doc.getElementsByTagName("DESC_MARCA");
				NodeList nodeNoSerie		= doc.getElementsByTagName("NO_SERIE");
				NodeList nodeNoInventario	= doc.getElementsByTagName("NO_INVENTARIO");
				NodeList nodeImei			= doc.getElementsByTagName("NO_IMEI");
				NodeList nodePosicion		= doc.getElementsByTagName("POSICION_INVENTARIO");
				NodeList nodeIdUsuarioAlta	= doc.getElementsByTagName("ID_USUARIO_ALTA");
				NodeList nodeFecAlta		= doc.getElementsByTagName("FEC_ALTA");
				NodeList nodeDescStatus		= doc.getElementsByTagName("DESC_STATUS_UNIDAD");
				NodeList nodeIdSatus		= doc.getElementsByTagName("ID_STATUS_UNIDAD");
				NodeList nodeDescNegocio	= doc.getElementsByTagName("DESC_NEGOCIO");
                NodeList nodeDescCliente	= doc.getElementsByTagName("DESC_CLIENTE");
                NodeList nodeDescSoftware	= doc.getElementsByTagName("SOFTWARE");
                NodeList nodeDescConnectivity = doc.getElementsByTagName("CONNECTIVITY");

				//NamedNodeMap nodeMap		= nodeRoot.item(0).getAttributes();
				
				for(int index = 0; index < nodeRoot.getLength(); index++) {
					Node nodeAttributes = nodeRoot.item(index);
					if(nodeAttributes.getNodeType() == Node.ELEMENT_NODE) {
						
						if(index > 0) {
							unitBeannArray.add(new UnitBean());
						}
						
						Node idUnidad = nodeIdUnidad.item(index).getChildNodes().item(0);
						Node idModelo = nodeIdModelo.item(index).getChildNodes().item(0);
						Node descModelo = nodeDescModelo.item(index).getChildNodes().item(0);
						Node descMarca = nodeDescMarca.item(index).getChildNodes().item(0);
						Node noSerie = nodeNoSerie.item(index).getChildNodes().item(0);					
						Node noIventario = nodeNoInventario.item(index).getChildNodes().item(0);
						Node imei = nodeImei.item(index).getChildNodes().item(0);
						Node posicionInventario = nodePosicion.item(index).getChildNodes().item(0);
						Node idUsuarioAlta = nodeIdUsuarioAlta.item(index).getChildNodes().item(0);
						Node fecAlta = nodeFecAlta.item(index).getChildNodes().item(0);
						Node descStatusUnidad = nodeDescStatus.item(index).getChildNodes().item(0);
						Node idStatusUnidad = nodeIdSatus.item(index).getChildNodes().item(0);
						Node descNegocio = nodeDescNegocio.item(index).getChildNodes().item(0);
						Node descCliente = nodeDescCliente.item(index).getChildNodes().item(0);
						Node descSoftware = nodeDescSoftware.item(index).getChildNodes().item(0);
						Node descConnectivity = nodeDescConnectivity.item(index).getChildNodes().item(0);

						String idUnidadValue = (idUnidad == null) ? "" : idUnidad.getNodeValue();
						String idModeloValue = (idModelo == null) ? "" : idModelo.getNodeValue();
						String descModeloValue = (descModelo == null) ? "" : descModelo.getNodeValue();
						String descMarcaValue = (descMarca == null) ? "" : descMarca.getNodeValue();
						String noSerieValue = (noSerie == null) ? "" : noSerie.getNodeValue();
						String noIventarioValue = (noIventario == null) ? "" : noIventario.getNodeValue();
						String imeiValue = (imei == null) ? "" : imei.getNodeValue();
						String posicionInventarioValue = (posicionInventario == null) ? "" : posicionInventario.getNodeValue();
						String idUsuarioAltaValue = (idUsuarioAlta == null) ? "" : idUsuarioAlta.getNodeValue();
						String fecAltaValue = (fecAlta == null) ? "" : fecAlta.getNodeValue();
						String descStatusUnidadValue = (descStatusUnidad == null) ? "" : descStatusUnidad.getNodeValue();
						String idStatusUnidadValue = (idStatusUnidad == null) ? "" : idStatusUnidad.getNodeValue();
						String descNegocioValue = (descNegocio == null) ? "" : descNegocio.getNodeValue();
						String descClienteValue = (descCliente == null) ? "" : descCliente.getNodeValue();
                        String valueDescSoftware = (descSoftware== null) ? "" : descSoftware.getNodeValue();
                        String valueDescConn = (descConnectivity == null) ? "" : descConnectivity.getNodeValue();

						unitBeannArray.get(index).setId(idUnidadValue);
						unitBeannArray.get(index).setIdModel(idModeloValue);
						unitBeannArray.get(index).setDescModel(descModeloValue);
						unitBeannArray.get(index).setDescBrand(descMarcaValue);
						unitBeannArray.get(index).setNoSerie(noSerieValue);
						unitBeannArray.get(index).setNoInventory(noIventarioValue);
						unitBeannArray.get(index).setNoIMEI(imeiValue);
						unitBeannArray.get(index).setInventoryPos(posicionInventarioValue);
						unitBeannArray.get(index).setIdNewUser(idUsuarioAltaValue);
						unitBeannArray.get(index).setNewDate(fecAltaValue);
						unitBeannArray.get(index).setDescUnitStatus(descStatusUnidadValue);
						unitBeannArray.get(index).setIdUnitStatus(idStatusUnidadValue);
						unitBeannArray.get(index).setDescBusiness(descNegocioValue);
						unitBeannArray.get(index).setDescClient(descClienteValue);
                        unitBeannArray.get(index).setDescSoftware(valueDescSoftware);
                        unitBeannArray.get(index).setDescConnectivity(valueDescConn);
					}
				}
				/*if(first != null) {
					if(first.hasChildNodes()) {
						for(Node child = first.getFirstChild(); child != null; child = child.getNextSibling()) {
							
						}
					}
				}*/
				
				/*NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				NamedNodeMap	nodeMap		= null;
				int				i			= 0;

				String res		= "";
				String desc		= "";
				
				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							nodeMap = child.getAttributes();
							
							if(i > 1){
								unidadBeannArray.add(new UnidadBean());
							}
							else if (i == 0){
								res = nodeMap.item(0).getNodeValue();
								desc = nodeMap.item(1).getNodeValue();
							}
							
							if( i > 0 && res.equals("OK") && !desc.equals("No hubo unidades en el negocio")){
								unidadBeannArray.get(i-1).setId(nodeMap.getNamedItem("A").getNodeValue());
								unidadBeannArray.get(i-1).setIdModelo(nodeMap.getNamedItem("B").getNodeValue());
								unidadBeannArray.get(i-1).setDescModelo(nodeMap.getNamedItem("C").getNodeValue());
								unidadBeannArray.get(i-1).setDescMarca(nodeMap.getNamedItem("D").getNodeValue());
								unidadBeannArray.get(i-1).setNoSerie(nodeMap.getNamedItem("E").getNodeValue());
								unidadBeannArray.get(i-1).setNoInventario(nodeMap.getNamedItem("F").getNodeValue());
								unidadBeannArray.get(i-1).setNoImei(nodeMap.getNamedItem("G").getNodeValue());
								unidadBeannArray.get(i-1).setPosicionInventario(nodeMap.getNamedItem("H").getNodeValue());
								unidadBeannArray.get(i-1).setIdUsuarioAlta(nodeMap.getNamedItem("I").getNodeValue());
								unidadBeannArray.get(i-1).setFecAlta(nodeMap.getNamedItem("J").getNodeValue());
								unidadBeannArray.get(i-1).setDescStatusUnidad(nodeMap.getNamedItem("K").getNodeValue());
								unidadBeannArray.get(i-1).setIdStatusUnidad(nodeMap.getNamedItem("L").getNodeValue());
								unidadBeannArray.get(i-1).setDescNegocio(nodeMap.getNamedItem("M").getNodeValue());
								unidadBeannArray.get(i-1).setDescCliente(nodeMap.getNamedItem("N").getNodeValue());
							}
							i++;
						}
					}
				}*/
			} 
			/*for(int i = 0; i < unidadBeannArray.size(); i++){
			}*/
			
		}
		catch (Exception e){
			String message = e.getMessage();
			unitBeannArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return unitBeannArray;
	}

	public static GenericResultBean agregarRefaccionUnidadNegocio(String idAR, String noSerie,
        String sku, String idMarca, String daniado, String nuevo, String idTecnico)
    {   // Envía la unidad de SKU (NO ESTA HECHO AUN)
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/insertRefaccionUnidad.asp";
		
		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try
        {
			String				fullHTTP	= URL_ADD_UNIDAD + "?";
			
			if(sku.equals("")){
				fullHTTP += "sk=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "sk=" + URLEncoder.encode(sku, "UTF-8");
			}
			
			if(idAR.equals("")){
				fullHTTP += "&ia=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&ia=" + URLEncoder.encode(idAR, "UTF-8");
			}
			
			if(idAR.equals("")){
				fullHTTP += "&ns=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&ns=" + URLEncoder.encode(noSerie, "UTF-8");
			}
			
			if(idAR.equals("")){
				fullHTTP += "&imar=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&imar=" + URLEncoder.encode(idMarca, "UTF-8");
			}
			
			if(daniado.equals("")){
				fullHTTP += "&d=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&d=" + URLEncoder.encode(daniado, "UTF-8");
			}
			
			if(nuevo.equals("")){
				fullHTTP += "&n=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&n=" + URLEncoder.encode(nuevo, "UTF-8");
			}
			
			//TODO se supone esto no va
			/*if(costo.equals("")){
				fullHTTP += "&c=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&c=" + URLEncoder.encode(costo, "UTF-8");
			}
			
			if(idMoneda.equals("")){
				fullHTTP += "&im=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&im=" + URLEncoder.encode(idMoneda, "UTF-8");
			}*/
			fullHTTP += "&im=" + URLEncoder.encode("1", "UTF-8");
			
			if(idTecnico.equals("")){
				fullHTTP += "&it=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&it=" + URLEncoder.encode(idTecnico, "UTF-8");
			}
			
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);
			
			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);
			
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList node = doc.getElementsByTagName("d");
				NamedNodeMap nodeMap	= node.item(0).getAttributes();
				   
				try{
					genericResultBean.setRes(nodeMap.getNamedItem("res").getNodeValue());
				}catch(Exception e){}
				try{
					genericResultBean.setDesc(nodeMap.getNamedItem("desc").getNodeValue());
				}catch(Exception e){}
				try{
					genericResultBean.setVal(nodeMap.getNamedItem("val").getNodeValue());
				}catch(Exception e){}	
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch(ConnectTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. No se pudo conectar al servidor.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return genericResultBean;
	}

	public static GenericResultBean agregarRefaccionUnidadNegocio(String idAR, String noSerie,
        String sku, String idMarca, String daniado, String nuevo, String costo, String idMoneda,
        String idTecnico)
    {   // Envía la Refacción para Unidad del Negocio
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/insertRefaccionUnidad.asp";

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_ADD_UNIDAD + "?";
			
			if(sku.equals("")){
				fullHTTP += "sk=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "sk=" + URLEncoder.encode(sku, "UTF-8");
			}

			if(idAR.equals("")){
				fullHTTP += "&ia=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&ia=" + URLEncoder.encode(idAR, "UTF-8");
			}
			
			if(idAR.equals("")){
				fullHTTP += "&ns=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&ns=" + URLEncoder.encode(noSerie, "UTF-8");
			}
			
			if(idAR.equals("")){
				fullHTTP += "&imar=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&imar=" + URLEncoder.encode(idMarca, "UTF-8");
			}

			if(daniado.equals("")){
				fullHTTP += "&d=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&d=" + URLEncoder.encode(daniado, "UTF-8");
			}
			
			if(nuevo.equals("")){
				fullHTTP += "&n=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&n=" + URLEncoder.encode(nuevo, "UTF-8");
			}
			
			if(costo.equals("")){
				fullHTTP += "&c=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&c=" + URLEncoder.encode(costo, "UTF-8");
			}
			
			if(idMoneda.equals("")){
				fullHTTP += "&im=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&im=" + URLEncoder.encode(idMoneda, "UTF-8");
			}
			
			if(idTecnico.equals("")){
				fullHTTP += "&it=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&it=" + URLEncoder.encode(idTecnico, "UTF-8");
			}
 
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setParams(httpParams);
			pagePost.setHeader("Accept-Charset", "utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);
			
			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList node = doc.getElementsByTagName("d");
				NamedNodeMap nodeMap	= node.item(0).getAttributes();
					
				try{
					genericResultBean.setRes(nodeMap.getNamedItem("res").getNodeValue());
				}catch(Exception e){}
				try{
					genericResultBean.setDesc(nodeMap.getNamedItem("desc").getNodeValue());
				}catch(Exception e){}
				try{
					genericResultBean.setVal(nodeMap.getNamedItem("val").getNodeValue());
				}catch(Exception e){}	
			}
		}
		catch(SocketTimeoutException ex)
        {
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e)
        {
			e.printStackTrace();
		}

		return genericResultBean;
	}
	
	public static ArrayList<RefaccionesUnidadBean> verRefaccionesUnidadNegocio(String idAR)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/infoRefaccionesUnidadNegocio.asp";
		ArrayList<RefaccionesUnidadBean> refaccionesUnidadBeanArray = new ArrayList<RefaccionesUnidadBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + idAR;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			refaccionesUnidadBeanArray.add(new RefaccionesUnidadBean());
			refaccionesUnidadBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(refaccionesUnidadBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								refaccionesUnidadBeanArray.add(new RefaccionesUnidadBean());	
							
							NodeList childList = child.getChildNodes();
							
							refaccionesUnidadBeanArray.get(i).setIdNegocio(childList.item(0).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescCliente(childList.item(1).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescMarca(childList.item(2).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescModelo(childList.item(3).getTextContent());
							refaccionesUnidadBeanArray.get(i).setNoSerie(childList.item(4).getTextContent());
							refaccionesUnidadBeanArray.get(i).setNoInventario(childList.item(5).getTextContent());
							refaccionesUnidadBeanArray.get(i).setIdStatusUnidad(childList.item(6).getTextContent());

							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			refaccionesUnidadBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return refaccionesUnidadBeanArray;
	}
	
	public static ArrayList<RefaccionesUnidadBean> verInstalacionRefaccion(String idAR, String edit,
        String searchText, String idTipoProducto)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/instalacion.asp";
		ArrayList<RefaccionesUnidadBean> refaccionesUnidadBeanArray = new ArrayList<RefaccionesUnidadBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?ia=" + idAR;
			
			if(edit == null)
				fullHTTP += "&ed=";
			else
				fullHTTP += "&ed=" + edit;
			
			if(searchText == null)
				fullHTTP += "&st=";
			else
				fullHTTP += "&st=" + searchText;
			
			if(idTipoProducto == null)
				fullHTTP += "&ip=";
			else
				fullHTTP += "&ip=" + idTipoProducto;
			
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			refaccionesUnidadBeanArray.add(new RefaccionesUnidadBean());
			refaccionesUnidadBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(refaccionesUnidadBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								refaccionesUnidadBeanArray.add(new RefaccionesUnidadBean());	
							
							NodeList childList = child.getChildNodes();
							
							refaccionesUnidadBeanArray.get(i).setIdUnidad(childList.item(0).getTextContent());
							refaccionesUnidadBeanArray.get(i).setIdNegocio(childList.item(1).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescNegocio(childList.item(2).getTextContent());
							refaccionesUnidadBeanArray.get(i).setIdCliente(childList.item(3).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescCliente(childList.item(4).getTextContent());
							refaccionesUnidadBeanArray.get(i).setIdMarca(childList.item(5).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescMarca(childList.item(6).getTextContent());
							refaccionesUnidadBeanArray.get(i).setIdModelo(childList.item(7).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescModelo(childList.item(8).getTextContent());
							refaccionesUnidadBeanArray.get(i).setNoSerie(childList.item(9).getTextContent());
							refaccionesUnidadBeanArray.get(i).setIdTipoProducto(childList.item(10).getTextContent());
							refaccionesUnidadBeanArray.get(i).setDescStatusUnidad(childList.item(11).getTextContent());

							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			refaccionesUnidadBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return refaccionesUnidadBeanArray;
	}

	public static GenericResultBean instalarRefaccion(String idAR, String idTecnico,
        RefaccionesUnidadBean refaccionUnidadBean, String noEquipo)
    {   // Guarda una instalación de refacción
		GenericResultBean genericResultBean = new GenericResultBean();
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_ADD_UNIDAD	= URL_SERVER + "/insertInstalacion.asp";

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_ADD_UNIDAD + "?";

			if(idAR.equals("")){
				fullHTTP += "ia=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "ia=" + URLEncoder.encode(idAR, "UTF-8");
			}

			fullHTTP += "&ed=1";

			if(refaccionUnidadBean.getIdNegocio().equals("")){
				fullHTTP += "&in=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&in=" + URLEncoder.encode(refaccionUnidadBean.getIdNegocio(), "UTF-8");
			}

			if(idTecnico.equals("")){
				fullHTTP += "&it=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&it=" + URLEncoder.encode(idTecnico, "UTF-8");
			}

			if(refaccionUnidadBean.getIdUnidad().equals("")){
				fullHTTP += "&iu=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&iu=" + URLEncoder.encode(refaccionUnidadBean.getIdUnidad(), "UTF-8");
			}

			if(noEquipo.equals("")){
				fullHTTP += "&ne=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&ne=" + URLEncoder.encode(noEquipo, "UTF-8");
			}

			if(refaccionUnidadBean.getIdTipoProducto().equals("")){
				fullHTTP += "&ip=" + URLEncoder.encode("", "UTF-8");
			}
			else{
				fullHTTP += "&ip=" + URLEncoder.encode(refaccionUnidadBean.getIdTipoProducto(), "UTF-8");
			}

			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList node = doc.getElementsByTagName("d");
				NamedNodeMap nodeMap	= node.item(0).getAttributes();

				try{
					genericResultBean.setRes(nodeMap.getNamedItem("res").getNodeValue());
				}catch(Exception e){}
				try{
					genericResultBean.setDesc(nodeMap.getNamedItem("desc").getNodeValue());
				}catch(Exception e){}
				try{
					genericResultBean.setVal(nodeMap.getNamedItem("val").getNodeValue());
				}catch(Exception e){}	
			}
		}
		catch(SocketTimeoutException ex){
			genericResultBean.setRes("ERROR");
			genericResultBean.setDesc("Error en el servidor. Tiempo de espera agotado.");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return genericResultBean;
	}
	
	public static ArrayList<LogComentariosBean> logComentarios(String idAR)
    {
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getLogComentarios.asp";
		ArrayList<LogComentariosBean> logComentariosBeanArray = new ArrayList<LogComentariosBean>();

		//Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
		try{
			String				fullHTTP	= URL_UPDATE + "?i=" + idAR;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset","utf-8");
			HttpResponse 		response 	= httpClient.execute(pagePost);

			//Respuesta del servidor
			logComentariosBeanArray.add(new LogComentariosBean());
			logComentariosBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

			if(logComentariosBeanArray.get(0).getConnStatus() == 200){
				DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
				DocumentBuilder			builder 	= factory.newDocumentBuilder();
				Document 				doc 		= builder.parse(response.getEntity().getContent());

				NodeList 		node 		= doc.getElementsByTagName("d");
				Node			elem		= node.item(0);
				Node 			child;
				int				i			= 0;

				if( elem != null){
					if (elem.hasChildNodes()){
						for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
							if(i > 0)
								logComentariosBeanArray.add(new LogComentariosBean());	
							
							NodeList childList = child.getChildNodes();
							
							logComentariosBeanArray.get(i).setDescTipoUsuario(childList.item(0).getTextContent());
							logComentariosBeanArray.get(i).setNombre(childList.item(1).getTextContent());
							logComentariosBeanArray.get(i).setFecAlta(childList.item(2).getTextContent());
							logComentariosBeanArray.get(i).setDescComentario(childList.item(3).getTextContent());
							i++;
						}
					}
				}
			} 
		}
		catch (Exception e){
			logComentariosBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}

		return logComentariosBeanArray;
	}
	
	public static int GenerarPDF(String idAR)
    {
        String URL = "";
        HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
        if(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE))
        {
            URL = "http://smc.microformas.com.mx/MIC3/pruebaPDF.asp";
        }
        else
        {
//            URL = "http://smc.microformas.com.mx/MIC3TEST/pruebaPDF.asp";
//            URL = "http://smc.microformas.com.mx/MIC3BIS/pruebaPDF.asp";
            URL = "http://smc.microformas.com.mx/MIC3_LAB/pruebaPDF.asp";
        }

		//(THIS) String URL = URL_SERVER + "/" + (Constants.APP_VERSION.equals("prod") ? "MIC3" : URL_BASE) + "/pruebaPDF.asp";
//        String URL = "";
		//String URL	= URL_SERVER + "/" + URL_BASE + "/pruebaPDF.asp";
		
		try{
			String				fullHTTP	= URL + "?ID_AR=" + idAR;
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
			HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);

			HttpClient 			httpClient 	= new DefaultHttpClient();
			HttpPost 			pagePost 	= new HttpPost(fullHTTP);
			pagePost.setHeader("Accept-Charset", "utf-8");
			pagePost.setParams(httpParams);
			HttpResponse 		response 	= httpClient.execute(pagePost);

			if(response.getStatusLine().getStatusCode() == 200){
				byte[] buffer = new byte[100];
				InputStream stream =response.getEntity().getContent();
				stream.read(buffer);
				String str = new String(buffer, "UTF-8");
				if (!str.contains("se creo el pdf en la carpeta"))
					return 2;		//SERVIDOR NO PUDO CREAR EL ARCHIVO
			}
			else{
				return 1;			//ERROR DE CONEXION AL SERVIDOR
			}
		}
		catch (Exception e){
			return 1;  				//ERROR DE CONEXION AL SERVIDOR
		}

		return 0;
	}
	
	public static boolean UploadPDF(String path, String newFileName, String idAr)
    {   // ("0-0")
		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);
		boolean uploaded = false;
		
		//Objects for postPDF
		HttpClient 			httpClient;
		HttpPost 			pagePost;
		HttpResponse 		response;
		
		//Objects for uploadFile
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		DataInputStream inStream = null;
		String existingFileName = path;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		String reponse_data = "";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;

		String encodedFileName = newFileName;

		try{
			encodedFileName = URLEncoder.encode(encodedFileName, "UTF-8");
		}
		catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}

		String URL_UPLOAD;
        String URL_UPDATE_DB;
        // (THIS)
        HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
		if(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE))
        {
			URL_UPLOAD	= "http://smc.microformas.com.mx/BBNET/uploadPDF.aspx";
            URL_UPDATE_DB = "http://smc.microformas.com.mx/BB/Android/postPDF.asp?ia=" + idAr + "&iu="
                    + MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, 0)
                    .getString(Constants.PREF_USER_ID, "")
                    + "&f=" + newFileName;
		}
		else{
			URL_UPLOAD	= "http://smc.microformas.com.mx/BBNET/uploadPDFTest.aspx";
            URL_UPDATE_DB = "http://smc.microformas.com.mx/BB/Android_VERSION_PRUEBA/postPDF.asp?ia=" + idAr + "&iu="
                    + MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, 0)
                    .getString(Constants.PREF_USER_ID, "")
                    + "&f=" + newFileName;

		}

//		String URL_UPDATE_DB = "http://smc.microformas.com.mx/MIC3TEST/Android/postPDF.asp?ia=" + idAr + "&iu="
//		String URL_UPDATE_DB = "http://smc.microformas.com.mx/MIC3BIS/Android/postPDF.asp?ia=" + idAr + "&iu="
        Log.e("Archivo",URL_UPLOAD);
        Log.e("Post",URL_UPDATE_DB);
		try{
			//------------------ CLIENT REQUEST
			FileInputStream fileInputStream = new FileInputStream(new File(existingFileName) );
			// open a URL connection to the Servlet
			URL url = new URL(URL_UPLOAD);
			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();
			// Allow Inputs
			conn.setDoInput(true);
			// Allow Outputs
			conn.setDoOutput(true);
			// Don't use a cached copy.
			conn.setUseCaches(false);
			// Use a post method.
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			//conn.setRequestProperty("Connection", "close");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			conn.setConnectTimeout(CONN_TIMER);
			conn.setReadTimeout(CONN_TIMER);
			if(true){
				dos = new DataOutputStream( conn.getOutputStream() );
				dos.writeBytes(twoHyphens + boundary + lineEnd);
	
				dos.writeBytes("Content-Disposition: form-data; name=\"File1\";filename=\"" + newFileName + "\"" + lineEnd); // newimageName is the Name of the File to be uploaded
				dos.writeBytes(lineEnd);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				while (bytesRead > 0){
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				fileInputStream.close();
				dos.flush();
				dos.close();
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
            Log.e("Error PDF:",ex.toString());
		
		}
		//------------------ read the SERVER RESPONSE
		try {
			inStream = new DataInputStream ( conn.getInputStream() );
			String str;
			while (( str = inStream.readLine()) != null){
				reponse_data=str;
			}
			inStream.close();
		}
		catch (IOException ioex){
			ioex.printStackTrace();
		}

		if(reponse_data.indexOf("PDF enviado exitosamente") != -1){
			try {
				//Make http request
				httpClient 	= new DefaultHttpClient(httpParams);
				pagePost 	= new HttpPost(URL_UPDATE_DB);
				pagePost.setHeader("Accept-Charset","utf-8");
				response 	= httpClient.execute(pagePost);

				//Chek for server response
				//Respuesta del servidor
				int connStatus = response.getStatusLine().getStatusCode();

				if( connStatus == 200){
					DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
					DocumentBuilder			builder 	= factory.newDocumentBuilder();
					Document 				doc 		= builder.parse(response.getEntity().getContent());

					NodeList 	 n		= doc.getElementsByTagName("d");
					Node 		 node 	= n.item(0).getFirstChild();
					NamedNodeMap r		= node.getAttributes();

					//Revisa si la info es correcta
					if(r.getNamedItem("res").getNodeValue().equals("OK")){
						uploaded = true;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}	
		}	
		
		return uploaded;
	}

    public static boolean UploadPDFClose(String path, String newFileName, String idAr)
    {
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMER);
        boolean uploaded = false;

        //Objects for postPDF
        HttpClient 			httpClient;
        HttpPost 			pagePost;
        HttpResponse 		response;

        //Objects for uploadFile
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String existingFileName = path;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        String reponse_data = "";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        String encodedFileName = newFileName;

        try{
            encodedFileName = URLEncoder.encode(encodedFileName, "UTF-8");
        }
        catch (UnsupportedEncodingException e1){
            e1.printStackTrace();
        }

        String URL_UPLOAD;
        String URL_UPDATE_DB;

        HTTPConnections.VerifyServerMode(MicroformasApp.getAppContext());
        if(Constants.APP_VERSION.equals(Constants.BASE_MODE_LIVE))
        {
            URL_UPLOAD	= "http://smc.microformas.com.mx/BBNET/uploadPDFClose.aspx";
            URL_UPDATE_DB = "http://smc.microformas.com.mx/BB/Android/postPDFCLose.asp?ia=" + idAr + "&iu="
                    + MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, 0)
                    .getString(Constants.PREF_USER_ID, "")
                    + "&f=" + newFileName;
        }
        else{
            URL_UPLOAD	= "http://smc.microformas.com.mx/BBNET/uploadPDFClose.aspx";
            URL_UPDATE_DB = "http://smc.microformas.com.mx/BB/Android_VERSION_PRUEBA/postPDFClose.asp?ia=" + idAr + "&iu="
                    + MicroformasApp.getAppContext().getSharedPreferences(Constants.PREF_CONFIG_USER, 0)
                    .getString(Constants.PREF_USER_ID, "")
                    + "&f=" + newFileName;

        }
        try{
            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(existingFileName) );
            // open a URL connection to the Servlet
            URL url = new URL(URL_UPLOAD);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            //conn.setRequestProperty("Connection", "close");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            conn.setConnectTimeout(CONN_TIMER);
            conn.setReadTimeout(CONN_TIMER);
            if(true){
                dos = new DataOutputStream( conn.getOutputStream() );
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"File1\";filename=\"" + newFileName + "\"" + lineEnd); // newimageName is the Name of the File to be uploaded
                dos.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0){
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                fileInputStream.close();
                dos.flush();
                dos.close();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            Log.e("Error PDF:",ex.toString());

        }
        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream ( conn.getInputStream() );
            String str;
            while (( str = inStream.readLine()) != null){
                reponse_data=str;
            }
            inStream.close();
        }
        catch (IOException ioex){
            ioex.printStackTrace();
        }

        if(reponse_data.indexOf("PDF enviado exitosamente") != -1){
            try {
                //Make http request
                httpClient 	= new DefaultHttpClient(httpParams);
                pagePost 	= new HttpPost(URL_UPDATE_DB);
                pagePost.setHeader("Accept-Charset","utf-8");
                response 	= httpClient.execute(pagePost);

                //Chek for server response
                //Respuesta del servidor
                int connStatus = response.getStatusLine().getStatusCode();

                if( connStatus == 200){
                    DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
                    DocumentBuilder			builder 	= factory.newDocumentBuilder();
                    Document 				doc 		= builder.parse(response.getEntity().getContent());

                    NodeList 	 n		= doc.getElementsByTagName("d");
                    Node 		 node 	= n.item(0).getFirstChild();
                    NamedNodeMap r		= node.getAttributes();

                    //Revisa si la info es correcta
                    if(r.getNamedItem("res").getNodeValue().equals("OK")){
                        uploaded = true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return uploaded;
    }

    public static void updatePhoneImei(String imei, String idusuario, String number ,Activity activity){
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = String.format(URL_SERVER + "/updatePhoneImei.asp?idusuario="+idusuario+"&imei="+imei+"&number="+number);
        StringRequest myReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta**", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ErrorRes**", error.toString());
            }
        });
        queue.add(myReq);
    }

    public static void getNeedPhoneNumber(int idusuario, Context context){

	    final String[] respuesta = new String[1];
        try{
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = new String(URL_SERVER + "/getNeedPhoneNumber.asp?idusuario="+idusuario);
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            respuesta[0] = parseJson(response);
                            if (!respuesta[0].trim().equals("")&&!respuesta[0].trim().equals("0")){
                                MicroformasApp.needPhoneNumber = 1;
                            }else{
                                MicroformasApp.needPhoneNumber = 0;
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Microformas", "Error Respuesta en JSON: " + error.getMessage());
                            MicroformasApp.needPhoneNumber = 0;
                        }
                    }
            );
            queue.add(jsonArrayRequest);
        } catch (Exception e){
            e.printStackTrace();
            MicroformasApp.needPhoneNumber = 0;
        }
    }

    public static String parseJson(JSONObject jsonObject){
	    String celular = "ERROR";
	    JSONArray jsonArray = null;

	    try{
	        jsonArray = jsonObject.getJSONArray("data");
	        for (int i = 0; i < jsonArray.length(); i++){
	            try{
	                JSONObject objeto = jsonArray.getJSONObject(i);
	                celular = objeto.getString("CELULAR");
                }catch (JSONException e){
	                Log.e("Microformas", "Error de parsing" + e.getMessage());
                }
            }
        }catch (JSONException e){
	        e.printStackTrace();
        }
        return celular;
    }

    public static void updatePhoneNumber(String number, String idusuario, Activity activity){
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = String.format(URL_SERVER + "/updatePhoneNumber.asp?idusuario="+idusuario+"&number="+number);
        StringRequest myReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta**", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ErrorRes**", error.toString());
            }
        });
        queue.add(myReq);
    }

	public static String setDateFormatExito(String originalDate)
    {
		String arrangedDate = null;
		Date date = null;
		int type = 0;
		
		if(originalDate == null || originalDate.equals("")){
			arrangedDate = "";
		}
		else{
			SimpleDateFormat dateFormat  = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); 
			SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy"); 
			String month   = "";
			String day     = "";
			String hour    = "";
			String minute  = "";
			String seconds = "";
			
			if(originalDate.length() > 15){
				try{
					date = dateFormat.parse(originalDate);
				}
				catch(Exception e){}
			}
			else{
				try {
					type = 1;
					date = dateFormat2.parse(originalDate);
				}
				catch (ParseException e1){
					return originalDate;
				}
			}
			
			Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
			
			month = String.valueOf(cal.get(Calendar.MONTH) + 1);
			day   = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			
			//Arregla el string de las horas
			if(type == 0){
				hour = String.valueOf(date.getHours());
				minute = String.valueOf(date.getMinutes());
				seconds   = String.valueOf(date.getSeconds());
			}
			
			//Agrega un 0 al día y mes en caso de necesitarlos
			if(String.valueOf(month).trim().length() == 1)
				month   = "0" + month.trim();
			if(String.valueOf(day).trim().length() == 1)
				day     = "0" + day.trim();
			
			arrangedDate = day     + "/" 
					 	 + month   + "/" 
					 	 + String.valueOf(date.getYear() + 1900);
			
			//Le agrega un 0 a las horas en caso de requerirlo
			if(type == 0){
				if(String.valueOf(hour).trim().length() == 1)
					hour    = "0" + hour.trim();
				if(String.valueOf(minute).trim().length() == 1)
					minute  = "0" + minute.trim();
				if(String.valueOf(seconds).trim().length() == 1)
					seconds = "0" + seconds.trim();
				
				if(Integer.valueOf(hour.trim()) > 12){
					int temp = Integer.valueOf(hour.trim()) - 12;
					hour 	 = String.valueOf(temp);
					
					if(hour.trim().length() == 1)
						hour    = "0" + hour.trim();
					
					arrangedDate = arrangedDate + " " 	
							 + hour    + ":" 
							 + minute  + ":" 
							 + seconds + " PM";
				}
				else{
					arrangedDate = arrangedDate + " " 	
							 + hour    + ":" 
							 + minute  + ":" 
							 + seconds + " AM";
				}				
			}
			else{
				arrangedDate = arrangedDate + " 00:00:00 AM";
			}
		}

		return arrangedDate;
	}

	public static ArrayList<DescripcionTrabajoBean> getDescripcionTrabajoCatalogo()
    {   // Adquiere el catálogo de DescripcionTrabajo
        HTTPConnections.VerifyURL(MicroformasApp.getAppContext());
		String URL_UPDATE	= URL_SERVER + "/getDescripcionTrabajoCatalogo.asp";
        ArrayList<DescripcionTrabajoBean> descripcionTrabajoBeanArray = new ArrayList<DescripcionTrabajoBean>();

        //Crea nuevo cliente HTTP, revisa la cadena xml de respuesta.
        try
        {
            String				fullHTTP	= URL_UPDATE;
            final HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONN_TIMER);

            HttpClient 			httpClient 	= new DefaultHttpClient(httpParams);
            HttpPost 			pagePost 	= new HttpPost(fullHTTP);
            pagePost.setHeader("Accept-Charset","utf-8");
            HttpResponse 		response 	= httpClient.execute(pagePost);

            //Respuesta del servidor
            descripcionTrabajoBeanArray.add(new DescripcionTrabajoBean());
            descripcionTrabajoBeanArray.get(0).setConnStatus(response.getStatusLine().getStatusCode());

            if(descripcionTrabajoBeanArray.get(0).getConnStatus() == 200){
                DocumentBuilderFactory 	factory 	= DocumentBuilderFactory.newInstance();
                DocumentBuilder			builder 	= factory.newDocumentBuilder();
                Document 				doc 		= builder.parse(response.getEntity().getContent());

                NodeList 		node 		= doc.getElementsByTagName("d");
                Node			elem		= node.item(0);
                Node 			child;
                NamedNodeMap	nodeMap		= null;
                int				i			= 0;

                if( elem != null){
                    if (elem.hasChildNodes()){
                        for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                            if(i > 0)
                                descripcionTrabajoBeanArray.add(new DescripcionTrabajoBean());

                            nodeMap = child.getAttributes();
                            descripcionTrabajoBeanArray.get(i).setId(nodeMap.getNamedItem("A").getNodeValue());
                            descripcionTrabajoBeanArray.get(i).setIdEspecifTipoFalla(nodeMap.getNamedItem("B").getNodeValue());
                            descripcionTrabajoBeanArray.get(i).setStatus(nodeMap.getNamedItem("C").getNodeValue());
                            descripcionTrabajoBeanArray.get(i).setDesc(child.getTextContent());

                            i++;
                        }
                    }
                }
            }
		}
		catch (Exception e){
			descripcionTrabajoBeanArray.get(0).setConnStatus(100);
			e.printStackTrace();
		}
		return descripcionTrabajoBeanArray;
	}
	
	//UTILITIES
	public static void writeResults(String clase, String fullHTTP, Document doc) throws IOException
    {
		File wallpaperDirectory = new File("/sdcard/");
		wallpaperDirectory.mkdirs();
		File outputFile = new File(wallpaperDirectory, "CierreExitoTest2");
		
		// now attach the OutputStream to the file object, instead of a String representation
		FileOutputStream fos = new FileOutputStream(outputFile, true);
		
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		fos.write((clase + " = " + fullHTTP).getBytes());
					
		Element docEle = doc.getDocumentElement();
	    NodeList nl = docEle.getChildNodes();
	    if (nl != null && nl.getLength() > 0) {
	        for (int i = 0; i < nl.getLength(); i++) {
	            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
	                Element el = (Element) nl.item(i);
	                NamedNodeMap nm = el.getAttributes();
	                for(int j = 0; j < nm.getLength(); j++){
	                	fos.write(("name #"  + j + " = " + nm.item(j).getNodeName()  + " ").getBytes());
	                	fos.write(("value #" + j + " = " + nm.item(j).getNodeValue() + "\n").getBytes());
	                }
	                
	            }
	        }
	    }
	    fos.flush();
	    fos.close();
	}

    public static void VerifyURL(Context context)
    {
        SharedPreferences configServer = context.getSharedPreferences(Constants.PREF_CONFIG_SERVER,
                Context.MODE_PRIVATE);

        if (configServer.getString(Constants.PREF_APP_MODE, "").equals(Constants.BASE_MODE_LIVE))
        {
            URL_SERVER = configServer.getString(Constants.PREF_APP_URL_LIVE, "");
        }
        else
        {
            URL_SERVER = configServer.getString(Constants.PREF_APP_URL_TEST, "");
        }
    }

    public static void VerifyServerMode(Context context)
    {
        SharedPreferences configServer = context.getSharedPreferences(Constants.PREF_CONFIG_SERVER,
                Context.MODE_PRIVATE);

        // Default URL Server
        if (!configServer.contains(Constants.PREF_APP_MODE))
        {
            Constants.APP_VERSION = Constants.BASE_MODE_LIVE;

            SharedPreferences.Editor editor = configServer.edit();
            editor.putString(Constants.PREF_APP_MODE, Constants.APP_VERSION);
            editor.commit();
        }
        else
        {
            Constants.APP_VERSION = configServer.getString(Constants.PREF_APP_MODE, Constants.BASE_MODE_LIVE);
        }
    }

    public static String REQUEST_OK = "S_OK";
    public static String REQUEST_ERROR = "E_FAIL";

    private static String URL_SERVER = null;

    private static int SOCKET_TIMER = 300000;
    private static int CONN_TIMER  	= 300000;
    private static int statusSust;
}
