package com.example.c.tvtimetable.area;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.c.tvtimetable.db.DataSet;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by C on 31/10/2014.
 */
public class AreaDataSetPost extends AsyncTask {

    private String url  = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx/getAreaDataSet";

    private Context context;

    private DataSet dataSet;

    private ArrayList<Area> items;
    private ArrayAdapter<Area> adapter;

    private ProgressDialog dialog;

    public AreaDataSetPost(Context cont, ArrayList<Area> list, ArrayAdapter<Area> arrayAdapter,DataSet dataSet){
        context = cont;
        items = list;
        adapter = arrayAdapter;
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
            dataSet.deleteAllAreas();

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            HttpResponse response = (BasicHttpResponse)client.execute(post);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(response.getEntity().getContent());
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("AreaList");

            for(int i=0;i<list.getLength();i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    Area area = new Area();
                    area.setAreaID(element.getElementsByTagName("areaID").item(0).getTextContent());
                    area.setArea(element.getElementsByTagName("Area").item(0).getTextContent());
                    Log.d("debug","Before insert");
                    dataSet.insertArea(area);
                    Log.d("debug","after insert");
                }
            }

            items.addAll(dataSet.getAllAreas());

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
