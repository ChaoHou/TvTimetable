package com.example.c.tvtimetable.program;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

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
public class TVprogramDataSetPost extends AsyncTask {

    private String url  = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx/getTVprogramDateSet";

    private Context context;
    private ArrayList<TVProgram> items;
    private ArrayAdapter<TVProgram> adapter;
    private String channelID;
    private String date;

    private ProgressDialog dialog;

    public TVprogramDataSetPost(Context cont, ArrayList<TVProgram> list, ArrayAdapter<TVProgram> arrayAdapter, String id,String date){
        context = cont;
        items = list;
        adapter = arrayAdapter;
        channelID = id;
        this.date = date;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading tv program");
        dialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            items.clear();

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("theTVchannelID",channelID));
            pairs.add(new BasicNameValuePair("theDate",date));
            pairs.add(new BasicNameValuePair("userID",""));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = (BasicHttpResponse)client.execute(post);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(response.getEntity().getContent());
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tvProgramTable");

            for(int i=0;i<list.getLength();i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;
                    TVProgram program = new TVProgram();
                    program.setPlayTime(element.getElementsByTagName("playTime").item(0).getTextContent());
                    program.setTvProgram(element.getElementsByTagName("tvProgram").item(0).getTextContent());
                    items.add(program);
                }
            }

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
        ((TVprogramDataSetActivity)context).invalidateOptionsMenu();
    }
}
