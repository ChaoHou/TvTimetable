package com.example.c.tvtimetable.channel;

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
 * Created by C on 1/11/2014.
 */
public class TVchannelDataSetPost extends AsyncTask {

    private String url  = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx/getTVchannelDataSet";

    private Context context;

    private DataSet dataSet;
    private ArrayList<TVChannel> items;
    private ArrayAdapter<TVChannel> adapter;
    private String stationID;

    private ProgressDialog dialog;

    public TVchannelDataSetPost(Context cont, ArrayList<TVChannel> list, ArrayAdapter<TVChannel> arrayAdapter, String id,DataSet dataSet){
        context = cont;
        items = list;
//        ids = idList;
        adapter = arrayAdapter;
        stationID = id;
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
            dataSet.deleteAllChannelsByStationID(stationID);

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("theTVstationID",stationID));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = (BasicHttpResponse)client.execute(post);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(response.getEntity().getContent());
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("TvChanne");

            for(int i=0;i<list.getLength();i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    String channelID = element.getElementsByTagName("tvChannelID").item(0).getTextContent();
                    String channel = element.getElementsByTagName("tvChannel").item(0).getTextContent();
                    dataSet.insertChannel(channelID,channel,stationID);
                }
            }
            items.addAll(dataSet.getAllChannelsByStationID(stationID));

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
