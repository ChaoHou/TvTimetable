package com.example.c.tvtimetable.station;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import com.example.c.tvtimetable.db.DataSet;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by C on 31/10/2014.
 */
public class TVstationDataSetPost extends AsyncTask {

    private String url  = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx/getTVstationDataSet";

    private Context context;

    private DataSet dataSet;

    private ArrayList<TVStation> items;
    private ArrayAdapter<TVStation> adapter;
    private String areaID;

    private ProgressDialog dialog;

    public TVstationDataSetPost(Context cont, ArrayList<TVStation> list, ArrayAdapter<TVStation> arrayAdapter, String id, DataSet dataSet){
        context = cont;
        items = list;
        adapter = arrayAdapter;
        areaID = id;
        this.dataSet = dataSet;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Waiting");
        dialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            items.clear();
            dataSet.deleteAllStationsByAreaID(areaID);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("theAreaID",areaID));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = (BasicHttpResponse)client.execute(post);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(response.getEntity().getContent());
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("TvStation");

            for(int i=0;i<list.getLength();i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    String stationID = element.getElementsByTagName("tvStationID").item(0).getTextContent();
                    String stationName = element.getElementsByTagName("tvStationName").item(0).getTextContent();

                    dataSet.insertStation(stationID,stationName,areaID);
                }
            }
            items.addAll(dataSet.getAllStationsByAreaID(areaID));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }
}
